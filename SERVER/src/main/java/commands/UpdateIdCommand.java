package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.NoSuchElementException;
import java.util.Vector;

import static commands.AddCommand.create_id;

/**
 * Класс {@code UpdateId} переопределяет метод {@code execute ()}, чтобы обновить указанный id.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */

public class UpdateIdCommand extends AbstractCommand {
    public UpdateIdCommand(CollectionManager manager) {
        super((manager));
        setDescription("Обновить значение элемента коллекции, id которого равен заданному.");
    }

    @Override
    public synchronized String execute(String t) {
        boolean flag = false;
        String s=null;
        Vector<LabWork> works = getManager().getWorks();
        if (works.size() != 0) {
            try {
                if (t != null) {
                    int new_id = Integer.parseInt(t);
                    for (LabWork p : works) {
                        if (p != null && p.getId() == new_id) {
                            int id = create_id();
                            p.setId(id);
                            getManager().save();
                            s= "Id обновлено.";
                            flag = true;
                        }
                    }
                    if (!flag) {
                        s= "Нет такого id.";
                    }
                } else {
                    s="Ошибка! Id не найдено.";
                }
            } catch (NoSuchElementException ex) {
                s="Ошибка! Id не найдено.";
            }
        } else s="Элемент не с чем сравнивать. Коллекция пуста.";

        return s;
    }
}
