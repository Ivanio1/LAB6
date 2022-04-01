package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.*;

/**
 * Выводит работу с максимальной длиной имени автора.
 * @author Соболев Иван
 */
public class MaxByAuthorCommand extends AbstractCommand{

    public MaxByAuthorCommand(CollectionManager manager) {
        super(manager);
        setDescription("Вывести любой объект из коллекции, значение поля author которого является максимальным.\"");
    }

    @Override
    public synchronized String execute() {
        String s=null;
        Vector<LabWork> works = getManager().getWorks();
        if (works.size() != 0) {
            try {
                ArrayList names = new ArrayList<>();
                for (LabWork work : works) {
                    String a = work.getAuthor().getName();
                    names.add(a);
                }

                Comparable name = Collections.max(names);
                String Max_name = name.toString();
                for (LabWork work : works) {
                    if (work.getAuthor().getName() == Max_name) {
                        s=work.toString();
                    }
                }
            } catch (NoSuchElementException e) {
                s="Элемент не с чем сравнивать. Коллекция пуста.";
            }
        } else s="Элемент не с чем сравнивать. Коллекция пуста.";
        return s;
    }
}
