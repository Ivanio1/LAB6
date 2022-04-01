package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

/**
 * Считает количество элементов с данной сложностью.
 * @author Соболев Иван
 */
public class CountByDifficultyCommand extends AbstractCommand{
    public CountByDifficultyCommand(CollectionManager manager) {
        super(manager);
        setDescription("Вывести количество элементов, значение поля difficulty которых равно заданному.\"");
    }

    @Override
    public synchronized String execute(String addCommand) {
        String s=null;
        Vector<LabWork> works = getManager().getWorks();
        if (works.size() != 0) {
            int c = 0;
            int n = 0;
            for (LabWork work : works) {
                if (work.getDifficulty() != null) {

                    if (Objects.equals(work.getDifficulty().toString(), addCommand) && work.getDifficulty() != null) {

                        c += 1;
                    }
                } else {
                    n += 1;
                    s="У " + n + " элементов коллекции сложности нет.";
                }


            }
            s="Количество элементов со сложностью " + addCommand + "=" + c;
        } else s="Элемент не с чем сравнивать. Коллекция пуста.";
        return s;
    }

}
