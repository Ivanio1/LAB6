package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Класс {@code RemoveLastCommand} переопределяет метод {@code execute ()} для удаления
 * элемента из {@code Collection <? extends LabWork> col}.
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class RemoveAtCommand extends AbstractCommand {

    public RemoveAtCommand(CollectionManager manager) {
        super(manager);
        setDescription("Удалить элемент в коллекции.");
    }

    @Override
    public synchronized String execute(String t) {
        Vector<LabWork> collection = getManager().getWorks();
        try {
            collection.remove(Integer.parseInt(t)-1);
            getManager().save();
            return "Элемент в коллекции удалён.";
        }
        catch (NoSuchElementException ex) {
            return "Вы не можете удалить элемент, так как коллекция пуста.";
        }
        catch (ArrayIndexOutOfBoundsException e){
            return "Элемента по данному индексу не существует.";
        }
    }
}
