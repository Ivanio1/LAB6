package server;


import commands.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

/**
 * Класс {@code Server} представляет объект сервера, который манипулирует {@link CollectionManager}.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */

public class Server implements Runnable {
    private CollectionManager serverCollection;
    private Socket incoming;
    private HashMap<String, AbstractCommand> availableCommands;
    private static final Logger logger = Logger.getLogger("Logger");

    /**
     * @param serverCollection обеспечивает доступ к коллекции.
     * @param incoming         активное соединение с клиентской программой.
     *                         Команды, доступные клиенту, являются объектами {@link AbstractCommand}, хранящимися в
     *                         {@code HashMap <String, AbstractCommand> availableCommands}.
     */
    Server(CollectionManager serverCollection, Socket incoming) {
        this.serverCollection = serverCollection;
        this.incoming = incoming;
        availableCommands = new HashMap<>();
        availableCommands.put("show", new ShowCommand(serverCollection));
        availableCommands.put("remove_first", new RemoveFirstCommand(serverCollection));
        availableCommands.put("info", new InfoCommand(serverCollection));
        availableCommands.put("clear", new ClearCommand(serverCollection));
        availableCommands.put("count_by_difficulty", new CountByDifficultyCommand(serverCollection));
        availableCommands.put("execute_script", new ExecuteScriptCommand(serverCollection));
        availableCommands.put("filter_greater_than_minimal_point", new FilterGreaterThanCommand(serverCollection));
        availableCommands.put("max_by_author", new MaxByAuthorCommand(serverCollection));
        availableCommands.put("add", new AddCommand(serverCollection));
        availableCommands.put("remove_at", new RemoveAtCommand(serverCollection));
        availableCommands.put("remove_by_id", new RemoveByIdCommand(serverCollection));
        availableCommands.put("add_if_max", new AddIfMaxCommand(serverCollection));
        availableCommands.put("update_id", new UpdateIdCommand(serverCollection));
        availableCommands.put("help", new HelpCommand(serverCollection, availableCommands));
        availableCommands.put("man", new ManCommand(serverCollection, availableCommands));
    }

    /**
     * Запускает активное соединение с клиентом.
     */
    @Override
    public void run() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        try (ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream());
             ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            sendToClient.writeObject("Соединение установлено.\nВы можете вводить команды.");
            flag=false;
            AbstractCommand errorCommand = new AbstractCommand(null) {
                @Override
                public String execute() {
                    return "Неизвестная команда. Введите 'help' для получения списка команд.";
                }
            };
            while (true) {
                try {
                    String requestFromClient = getFromClient.readObject().toString();
                    String[] parsedCommand = requestFromClient.trim().split(" ", 2);
                    logger.log(Level.INFO,"Получено [" + requestFromClient + "] от " + incoming + ". ");
                    if (parsedCommand[0].equals("execute_script")) {
                        if(parsedCommand.length!=1){
                            scriptmode(parsedCommand[1], sendToClient, getFromClient);}else{
                            sendToClient.writeObject("ОТВЕТ СЕРВЕРА: Пустой файл.");
                        }

                    } else if (parsedCommand.length == 1)
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute());
                    else if (parsedCommand.length == 2)
                        sendToClient.writeObject(availableCommands.getOrDefault(parsedCommand[0], errorCommand).execute(parsedCommand[1]));
                    logger.log(Level.INFO,"Ответ успешно отправлен.");
                } catch (SocketException e) {
                    logger.log(Level.SEVERE,incoming + " отключился от сервера."); //Windows
                    flag=true;
                    while(flag){
                        String world=scanner.nextLine();
                        if(world.equals("save")){
                            serverCollection.save();
                            logger.log(Level.INFO,"Коллекция сохранена.");
                            flag=true;
                        }
                        if(world.equals("stop")){
                            serverCollection.save();
                            logger.log(Level.INFO,"Завершение работы сервера.");
                            flag=false;
                            System.exit(0);
                        }
                    }
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE,incoming + " отключился от сервера."); //Unix
            flag=true;
            while(flag){
                String world=scanner.nextLine();
                if(world.equals("save")){
                    serverCollection.save();
                    logger.log(Level.INFO,"Коллекция сохранена.");
                    flag=true;
                }
                if(world.equals("stop")){
                    logger.log(Level.INFO,"Завершение работы сервера.");
                    flag=false;
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Mode for catching commands from user input.
     */
    public void scriptmode(String arr, ObjectOutputStream sendToClient, ObjectInputStream getFromClient) throws IOException {
        AddCommand add = new AddCommand(serverCollection);
        AddIfMaxCommand addIfMaxCommand = new AddIfMaxCommand(serverCollection);
        ClearCommand clearCommand = new ClearCommand(serverCollection);
        CountByDifficultyCommand countByDifficultyCommand = new CountByDifficultyCommand(serverCollection);
        FilterGreaterThanCommand filterGreaterThanCommand = new FilterGreaterThanCommand(serverCollection);
        HelpCommand helpCommand = new HelpCommand(serverCollection, availableCommands);
        InfoCommand infoCommand = new InfoCommand(serverCollection);
        ManCommand manCommand = new ManCommand(serverCollection, availableCommands);
        MaxByAuthorCommand maxByAuthorCommand = new MaxByAuthorCommand(serverCollection);
        RemoveAtCommand removeAtCommand = new RemoveAtCommand(serverCollection);
        RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand(serverCollection);
        RemoveFirstCommand removeFirstCommand = new RemoveFirstCommand(serverCollection);
        ShowCommand showCommand = new ShowCommand(serverCollection);
        UpdateIdCommand updateIdCommand = new UpdateIdCommand(serverCollection);

        String s = "";
        String[] Arr = arr.split(";");
        //System.out.println(arr);
        for (String command : Arr) {
            String[] finalUserCommand = command.split(" ", 2);

            // System.out.println(finalUserCommand);
            try {
                switch (finalUserCommand[0]) {
                    case "":
                        break;
                    case "remove_first":
                        s+=(removeFirstCommand.execute())+"\n";
                        break;
                    case "add":
                        if (finalUserCommand[1] != null) {
                            s += add.execute(finalUserCommand[1]) + "\n";
                        } else {
                            s+="Неверный ввод данных в скрипте. ";
                        }
                        break;
                    case "remove_by_id":
                        s+=removeByIdCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "remove_at":
                        s+=removeAtCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "show":
                        s += showCommand.execute().toString();
                        break;
                    case "clear":
                        s+=clearCommand.execute()+"\n";
                        break;
                    case "info":
                        s+=infoCommand.execute()+"\n";
                        break;
                    case "update_id":
                        s+=updateIdCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "add_if_max":
                        s+=addIfMaxCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "help":
                        s += helpCommand.execute() + "\n";
                        break;
                    case "exit":
                        s+="\nПроцесс завершён."+"\n";
                        System.exit(0);
                        break;
                    case "max_by_author":
                        s+=maxByAuthorCommand.execute()+"\n";
                        break;
                    case "execute_script":
                        s+="Рекурсия скрипта."+"\n";
                        break;
                    case "man":
                        s+=manCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "count_by_difficulty":
                        s+=countByDifficultyCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    case "filter_greater_than_minimal_point":
                        s+=filterGreaterThanCommand.execute(finalUserCommand[1])+"\n";
                        break;
                    default:
                        s+="Неопознанная команда. Наберите 'help' для справки."+"\n";
                }

            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Отсутствует аргумент.");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        sendToClient.writeObject(s);

    }

}