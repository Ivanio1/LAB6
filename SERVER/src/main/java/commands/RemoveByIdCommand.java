package commands;


import data.LabWork;
import server.CollectionManager;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Класс {@code RemoveLastCommand} переопределяет метод {@code execute ()} для удаления
 * элемента из {@code Collection <? extends LabWork> col}.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class RemoveByIdCommand extends AbstractCommand {
    public RemoveByIdCommand(CollectionManager manager) {
        super(manager);
        setDescription("Удалить элемент из коллекции по его id.");
    }

    @Override
    public synchronized String execute(String id) {
        Vector<LabWork> works = getManager().getWorks();
        if (works.size() != 0) {
            try {
                works.removeIf(p -> ((p.getId()) == Integer.parseInt(id)));
                getManager().save();
                return "Элемент коллекции удалён.";
            } catch (NoSuchElementException ex) {
                return "Ошибка! Id не найдено.";
            }
        } else return "Элемент не с чем сравнивать. Коллекция пуста.";

    }
}
