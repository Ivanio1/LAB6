package commands;

import data.*;
import server.CollectionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

/**
 * Класс {@code AddIfMax} переопределяет метод {@code execute ()}, чтобы добавить {@link data.LabWork} в коллекцию,
 * если он больше {@code Collections.max(Collection <? extends LabWork> col)}.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class AddIfMaxCommand extends AbstractCommand {

    public AddIfMaxCommand(CollectionManager manager) {
        super(manager);
        setDescription("Добавьте новый элемент в коллекцию, если его значение меньше, чем наименьший элемент в этой коллекции.");
    }

    @Override
    public synchronized String execute(String line) throws ParseException {
        Vector<LabWork> works = getManager().getWorks();
        String[] args = line.split(",");
        String s = "";
        if (isNumeric(args[1]) && isNumeric(args[2]) && isDouble(args[3])) {
            LabWork W = null;
            try {
                int id = create_id();
                String name = args[0];
                String pname = null;
                Color color = null;
                Country country = null;
                Date birth = null;
                Coordinates coordinates = new Coordinates(Long.parseLong(args[1]), Long.parseLong(args[2]));
                java.util.Date creationDate = java.util.Date.from(Instant.now());
                Double minimalPoint = Double.parseDouble(args[3]);
                // System.out.println(args[0]+args[1]+args[2]+args[3]);
                if (Objects.equals(args[4], "EASY") || Objects.equals(args[4], "HARD") || Objects.equals(args[4], "VERY_HARD") || Objects.equals(args[4], "HOPELESS")) {
                    Difficulty diff = Difficulty.valueOf(args[4]);
                    pname = args[5];
                    color = Color.valueOf(args[6]);
                    if (args.length > 7) {
                        country = Country.valueOf(args[7]);
                        if (args.length > 8) {
                            birth = new SimpleDateFormat("dd.MM.yyyy").parse(args[8]);
                            Person p = new Person(pname, birth, color, country);
                            W = new LabWork(id, name, coordinates, creationDate, minimalPoint, diff, p);

                        } else {
                            Person p = new Person(pname, color, country);
                            W = new LabWork(id, name, coordinates, creationDate, minimalPoint, diff, p);
                        }
                    } else {
                        Person p = new Person(pname, color);
                        W = new LabWork(id, name, coordinates, creationDate, minimalPoint, diff, p);
                    }
                } else {
                    pname = args[4];
                    color = Color.valueOf(args[5]);
                    if (args.length > 6) {
                        country = Country.valueOf(args[6]);
                        if (args.length > 7) {
                            birth = new SimpleDateFormat("dd.MM.yyyy").parse(args[7]);
                            Person p = new Person(pname, birth, color, country);
                            W = new LabWork(id, name, coordinates, creationDate, minimalPoint, p);
                        } else {
                            Person p = new Person(pname, color, country);
                            W = new LabWork(id, name, coordinates, creationDate, minimalPoint, p);
                        }
                    } else {
                        Person p = new Person(pname, color);
                        W = new LabWork(id, name, coordinates, creationDate, minimalPoint, p);
                    }
                }

            } catch (IllegalArgumentException e) {
                s = "ERROR! Значение поля неверно";
            } catch (NullPointerException e) {
                s = "ERROR! Значение полей неверно";
            }
            if (W != null) {
                if (works.size() != 0) {
                    LabWork competitor = Collections.max(works);
                    if (competitor.getName().length() < W.getName().length()) {
                        getManager().getWorks().add(W);
                        getManager().save();
                        s = "Объект успешно добавлен.";
                    } else s = "Не удалось добавить элемент. Он меньше максимального.";
                } else s = "Элемент не с чем сравнивать. Коллекция пуста.";
            }else {s="ERROR! Значение полей неверно.";}

        }else {s="ERROR! Значение полей неверно.";}
        return s;

    }

    /**
     * Проверяет является ли строка int
     *
     * @param string
     * @return
     */
    public static boolean isNumeric(String string) {
        int intValue;


        if (string == null || string.equals("")) {

            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    /**
     * Проверяет является ли строка double
     *
     * @param string
     * @return
     */
    public static boolean isDouble(String string) {
        double intValue;


        if (string == null || string.equals("")) {
            ;
            return false;
        }

        try {
            intValue = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }

    /**
     * @return unique number.
     */
    public static int create_id() {
        return (int) Math.round(Math.random() * 32767 * 10);
    }

}

