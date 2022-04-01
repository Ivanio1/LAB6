package commands;


import data.LabWork;
import server.CollectionManager;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

/**
 * Класс {@code AbstractCommand} является суперклассом для всех классов команд.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */

public abstract class AbstractCommand {
    private CollectionManager manager; //Позволяет изменить коллекцию.
    private String description; //Содержит краткое руководство к команде.

    public AbstractCommand(CollectionManager manager) {
        this.manager = manager;
    }
    /**
     * Метод служит для выполнения кода команды без агрументов.
     * @return строка, которая содержит результат операции.
     */
    public synchronized String execute() {
        return "Отсутствует аргумент.";
    }
    /**
     * Метод служит для выполнения кода команды с агрументами.
     *
     * @param arg аргумент команды.
     * @return строка, которая содержит результат операции.
     */
    public synchronized String execute(String arg) throws ParseException {
        return execute();
    }
    public synchronized String execute(LabWork W) throws ParseException {
        return execute();
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public CollectionManager getManager() {
        return manager;
    }

    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }


    @Override
    public String toString() {
        return "AbstractCommand{" +
                "manager=" + manager +
                ", description='" + description + '\'' +
                '}';
    }
}
