package commands;

import commands.AbstractCommand;
import data.LabWork;
import server.CollectionManager;

import java.util.Vector;

/**
 * Класс {@code InfoCommand} переопределяет метод {@code execute ()} для отображения информации о {@link CollectionManager}.
 * @author Соболев Иван
 * @since 26.03.22
 */
public class InfoCommand extends AbstractCommand {

    public InfoCommand(CollectionManager manager) {
        super(manager);
        setDescription("Выводит информацию о коллекции.");
    }

    @Override
    public String execute(String arg) {
        return execute();
    }

    @Override
    public synchronized String execute() {
        Vector<LabWork> works = getManager().getWorks();
        return "Тип коллекции: " + works.getClass() + "\nКоличество элементов: " + works.size();
    }
}