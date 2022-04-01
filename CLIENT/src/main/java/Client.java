import commands.AddCommand;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Класс {@code Client} представляет объект клиента, подключаемый к серверу для манипулирования коллекцией.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class Client {

    private static Scanner fromKeyboard;
    private static ObjectOutputStream toServer;
    private static ObjectInputStream fromServer;
    private DataReader dataReader = new DataReader();

    public Client(DataReader d) {
        this.dataReader = d;

    }

    /**
     * Устанавливает активное соединение с сервером.
     */
    public void work() {
        try (Scanner scanner = new Scanner(System.in)) {
            fromKeyboard = scanner;
            while (true) {
                try (Socket outcoming = new Socket(InetAddress.getLocalHost(), 8800)) {
                    outcoming.setSoTimeout(5000);
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(outcoming.getOutputStream());
                         ObjectInputStream inputStream = new ObjectInputStream(outcoming.getInputStream())) {
                        toServer = outputStream;
                        fromServer = inputStream;
                        interactiveMode();
                        System.out.println("Завершение программы.");
                    }
                } catch (IOException e) {
                    System.err.println("Нет связи с сервером. Подключиться ещё раз (введите {да} или {нет})?");
                    String answer;
                    while (!(answer = fromKeyboard.nextLine()).equals("да")) {
                        switch (answer) {
                            case "":
                                break;
                            case "нет":
                                exit();
                                break;
                            default:
                                System.out.println("Введите корректный ответ.");
                        }
                    }
                    System.out.print("Подключение ...");
                }
            }
        }
    }

    /**
     * Парсит пользовательские команды и осуществляет обмен данными с сервером.
     *
     * @throws IOException при отправке и получении данных с сервера.
     */
    private void interactiveMode() throws IOException {
        try {
            System.out.println((String) fromServer.readObject());
            String command;
            while (!(command = fromKeyboard.nextLine()).equals("exit")) {
                String[] parsedCommand = command.trim().split(" ", 2);
                switch (parsedCommand[0]) {
                    case "":
                        break;
                    case "add":
                    case "add_if_max":
                        String arg = dataReader.readWork();
                        Command object = new Command(parsedCommand[0], arg);
                        sendCommand(object, toServer);
                        getAnswer(fromServer);
                        break;
                    case "update_id":
                        String arg1 = dataReader.readId();
                        Command object2 = new Command(parsedCommand[0], arg1);
                        sendCommand(object2, toServer);
                        getAnswer(fromServer);
                        break;
                    case "count_by_difficulty":
                    case "filter_greater_than_minimal_point":
                    case "remove_at":
                    case "remove_by_id":
                    case "man":
                        try{Command object3 = new Command(parsedCommand[0], parsedCommand[1]);
                        sendCommand(object3, toServer);
                        getAnswer(fromServer);}
                        catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Отсутствует аргумент!");
                        }
                        break;
                    case "show":
                    case "info":
                    case "clear":
                    case "remove_first":
                    case "max_by_author":
                    case "help":
                        Command object1 = new Command(parsedCommand[0]);
                        sendCommand(object1, toServer);
                        getAnswer(fromServer);
                        break;
                    case "execute_script":
                        //execute_script C:\Users\Asus\IdeaProjects\LAB6\CLIENT\src\main\java\script.txt
                        try{Command object4 = new Command(parsedCommand[0],ScriptReader(parsedCommand[1]));
                        sendCommand(object4, toServer);
                        getAnswer(fromServer);} catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("Отсутствует аргумент!");
                        }

                        break;
                    default:
                        System.out.println("Неопознанная команда. Введите help для справки.");
                }
            }
            exit();
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + e.getMessage());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод отправляет команду на сервер
     *
     * @throws IOException
     */
    public void sendCommand(Command answer, ObjectOutputStream toServer) throws IOException {
        toServer.writeObject(answer.toString());
    }

    /**
     * Метод получает результат от сервера
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void getAnswer(ObjectInputStream fromServer) throws IOException, ClassNotFoundException {
        try{String answer;
        answer = (String) fromServer.readObject();
        if (answer.equals("exit")) {
            System.exit(0);
        } else {
            System.out.println(answer);
        }}catch (NullPointerException e){
            System.out.println("Неверные данные в скрипте.");
        }
    }

public String ScriptReader(String argument){
    StringBuilder builder = new StringBuilder();
    try {
        if (argument.isEmpty()) throw new exceptions.WrongAmountOfElementsException();
        System.out.println("Выполняю скрипт '" + argument + "'...");
        String[] userCommand = {"", ""};
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                if (userCommand[0].equals("update_id")) {
                    String[] comands = new String[]{"execute_script", "save", "remove_first", "add", "show", "clear", "update_id", "info", "help", "man", "remove_at", "remove_by_id", "add_if_max", "exit", "max_by_author", "count_by_difficulty", "filter_greater_than_minimal_point"};
                    String line = (scriptScanner.nextLine().trim());
                    if (!(Arrays.asList(comands)).contains(line)) {
                        userCommand[1] = line;
                    } else {
                        userCommand[1] = "0";
                        System.out.println("Отсутствует аргумент.");
                    }

                }
                if (userCommand[0].equals("add")) {
                    StringBuilder command = new StringBuilder();
                    String[] comands = new String[]{"execute_script", "save", "remove_first", "add", "remove_greater", "show", "clear", "update_id", "info", "help", "man", "remove_at_index", "remove_by_id", "add_if_max", "exit", "max_by_author", "count_by_difficulty", "filter_greater_than_minimal_point"};
                    String line = (scriptScanner.nextLine().trim() + " ").split(" ")[0];
                    while (!(Arrays.asList(comands)).contains(line)) {
                        command.append(line + ",");
                        line = scriptScanner.nextLine();
                    }
                    userCommand[1] = command.toString()+";"+line+line.charAt(line.length()-1);


                }
                if (userCommand[0].equals("execute_script")) {
                    //System.out.println("Рекурсия скрипта. В скрипте не может быть команды execute_script");
                    throw new exceptions.ScriptRecursionException();
                }
                String out = userCommand[0] + " " + userCommand[1];
                builder.append(out+';');
                builder.delete(builder.length()-2,builder.length()-1);
            } while (scriptScanner.hasNextLine());
            //builder.setLength(builder.length()-1);
        } catch (FileNotFoundException exception) {
            System.out.println("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            System.out.println("Файл со скриптом пуст!");
        } catch (exceptions.ScriptRecursionException exception) {
            System.out.println("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            System.out.println("Непредвиденная ошибка!");
            System.exit(0);
        }

    } catch (exceptions.WrongAmountOfElementsException exception) {
        System.out.println("Некорректные команды в скрипте!");
    }
    // System.out.println(commands);
    return String.valueOf(builder).trim();
}

    /**
     * Отвечает за завершение работу клиентского приложения.
     */
    private void exit() {
        System.out.println("\nЗавершение программы.");
        System.exit(0);
    }
}