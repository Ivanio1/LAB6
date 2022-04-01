package commands;

import server.CollectionManager;

/**
 * Класс {@code ClearCommand} переопределяет метод {@code execute ()}, чтобы удалить все элементы из
 * {@code Collection <? extends LabWork> col}.
 *
 * @author Соболев Иван
 */
public class ClearCommand extends AbstractCommand {

    public ClearCommand(CollectionManager manager) {
        super(manager);
        setDescription("Очистить коллекцию.");
    }

    @Override
    public synchronized String execute() {
        getManager().getWorks().clear();
        getManager().save();
        return "Коллекция очищена.";
    }
}