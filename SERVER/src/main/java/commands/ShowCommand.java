package commands;


import data.LabWork;
import server.CollectionManager;

import java.util.Vector;

/**
 * Класс {@code ShowCommand} переопределяет метод {@code execute ()} для отображения
 * всех элементов из {@code Collection <? extends LabWork> col}.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class ShowCommand extends AbstractCommand {
    public ShowCommand(CollectionManager manager) {
        super(manager);
        setDescription("Выводит все элементы коллекции.");
    }

    @Override
    public synchronized String execute() {
        try {
            Vector<LabWork> collection = getManager().getWorks();
            StringBuilder result = new StringBuilder();
            if (collection.size() != 0) {
                for (LabWork s : collection) {
                    result.append(s.toString());
                }
                return result.toString();
            } else return "Коллекция пуста.";
        } catch (NullPointerException e) {
            return "Неверный ввод данных в скрипте.";
        }
    }
}
