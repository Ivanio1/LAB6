package commands;

import com.google.gson.JsonSyntaxException;
import data.*;
import server.CollectionManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 * Класс {@code AddCommand} переопределяет метод {@code execute ()} для добавления {@link data.LabWork} в коллекцию.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */

public class AddCommand extends AbstractCommand {
    public AddCommand(CollectionManager manager) {
        super(manager);
        setDescription("Добавить новый элемент в коллекцию.");
    }
    @Override
    public synchronized String execute(String line) throws ParseException {
        String answer = null;
        String[] args = line.split(",");
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
                answer="ERROR! Значение поля неверно.";
            } catch (NullPointerException e) {
                answer="ERROR! Значение полей неверно.";
            }
            if(W!=null){
            getManager().getWorks().add(W);
           getManager().save();
           answer="Объект успешно добавлен.";}
            else answer="ERROR! Значение полей неверно.";
        }else {answer="ERROR! Значение полей неверно.";}
        return answer;

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
