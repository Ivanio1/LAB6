package commands;

import server.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand(CollectionManager manager) {
        super(manager);
    }

    public synchronized String execute(String argument) throws ParseException {
        StringBuilder builder = new StringBuilder();
        try {
            if (argument == null) {
                builder = null;
                throw new exceptions.WrongAmountOfElementsException();
            } else {
                System.out.println("Выполняю скрипт '" + argument + "'...");
                String[] userCommand = {"", ""};
                try (Scanner scriptScanner = new Scanner(new File(argument))) {
                    if (!scriptScanner.hasNext()) throw new NoSuchElementException();
                    do {
                        userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);

                        //System.out.println(userCommand[0]);

                        if (userCommand[0].equals("update_id")) {
                            String[] comands = new String[]{"execute_script", "save", "remove_first", "add", "remove_greater", "show", "clear", "update_id", "info", "help", "man", "remove_at_index", "remove_by_id", "add_if_max", "exit", "max_by_author", "count_by_difficulty", "filter_greater_than_minimal_point"};
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
                                command.append(line + " ");
                                line = scriptScanner.nextLine();
                            }
                            //System.out.println(command);

                            userCommand[1] = command.toString();

                        }
                        if (userCommand[0].equals("execute_script")) {
                            //System.out.println("Рекурсия скрипта. В скрипте не может быть команды execute_script");
                            throw new exceptions.ScriptRecursionException();
                        }
                        String out = userCommand[0] + " " + userCommand[1];
                        builder.append(out + ';');
                    } while (scriptScanner.hasNextLine());
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
            }
        } catch (exceptions.WrongAmountOfElementsException exception) {
            System.out.println("Некорректные команды в скрипте!");
        }
        // System.out.println(commands);
        return String.valueOf(builder).trim();
    }
}

