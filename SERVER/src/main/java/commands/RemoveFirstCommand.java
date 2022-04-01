package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Класс {@code RemoveLastCommand} переопределяет метод {@code execute ()} для удаления
 * первого элемента из {@code Collection <? extends LabWork> col}.
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class RemoveFirstCommand extends AbstractCommand {

    public RemoveFirstCommand(CollectionManager manager) {
        super(manager);
        setDescription("Удалить первый элемент в коллекции.");
    }

    @Override
    public synchronized String execute() {
        Vector<LabWork> collection = getManager().getWorks();
        try {
            collection.remove(0);
            getManager().save();
            return "Первый элемент в коллекции удалён.";
        }
        catch (NoSuchElementException ex) {
            return "Вы не можете удалить элемент, так как коллекция пуста.";
        }
    }
}
