package commands;

import data.LabWork;
import server.CollectionManager;

import java.util.Vector;

/**
 * Выводит работы, если MinimalPoint равно данному.
 * @author Соболев Иван
 */
public class FilterGreaterThanCommand extends AbstractCommand{
    public FilterGreaterThanCommand(CollectionManager manager) {
        super(manager);
        setDescription("Вывести элементы, значение поля minimalPoint которых больше заданного.\"");
    }

    @Override
    public synchronized String execute(String point) {
        String s=null;
        Vector<LabWork> works = getManager().getWorks();
        try{if (works.size() != 0) {
            for (LabWork work : works) {
                if (work.getMinimalPoint() == Double.parseDouble(point)) {
                    return work.toString();
                } else {
                    s="Значения не равны.";
                }
            }
        } else s="Элемент не с чем сравнивать. Коллекция пуста.";}
        catch (NumberFormatException e){
            s="Неверный формат введенных данных.";
        }
        return s;
    }


}
