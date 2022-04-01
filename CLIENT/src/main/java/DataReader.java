import data.*;

import javax.xml.ws.BindingType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

import static commands.AddCommand.create_id;

public class DataReader {
    public DataReader() {
    }

    Scanner scanner = new Scanner(System.in);

    /**
     * Метод для чтения объекта LabWork из командной строки
     *
     * @return возвращает объект класса LabWork
     */
    public String readWork() throws ParseException {
        String W = null;

        String name = readWorkName();
        String coordinates = readCoordinates();

        Double minimalPoint = readMinimalPoint();
        Difficulty difficulty = readDifficulty();
        String personname = readPersonName();
        Color color = readColor();
        Country nationality = readNationality();
        Date birth = readBirth();
        //System.out.println(coordinates);
        if (difficulty != null && birth != null && nationality != null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + difficulty.toString() + "," + personname + "," + birth.toString() + "," + color .toString()+ "," + nationality.toString();
        }
        if (difficulty == null && birth != null && nationality != null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + personname + "," + birth.toString() + "," + color.toString() + "," + nationality.toString();
        }
        if (difficulty != null && birth == null && nationality != null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + difficulty.toString() + "," + personname + "," + color.toString() + "," + nationality.toString();
        }
        if (difficulty != null && birth == null && nationality == null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + difficulty.toString() + "," + personname + "," + color.toString();
        }
        if (difficulty != null && birth != null && nationality == null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + difficulty.toString() + "," + personname + "," + birth.toString() + "," + color.toString();
        }
        if (difficulty == null && birth == null && nationality != null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + personname + "," + color.toString() + "," + nationality.toString();
        }
        if (difficulty == null && birth != null && nationality == null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + personname + "," + birth.toString() + "," + color.toString();
        }
        if (difficulty == null && birth == null && nationality == null) {
            W = name + "," + coordinates + "," + minimalPoint.toString() + "," + personname + ","  + color.toString();
        }
        //System.out.println(W);
        return W;
    }


    /**
     * Вспомогательный метод для чтения объекта Coordinates из командной строки
     *
     * @return возвращает объект класса Coordinates
     */
    public String readCoordinates() {
        Scanner scanner = new Scanner(System.in);
        Long x = null;
        Long y = null;
        do {
            System.out.println("Введите x, не может быть null.");
            String s = scanner.nextLine();
            if (s.equals("")) {
                x = null;

            } else {
                try {
                    x = Long.parseLong(s);
                } catch (IllegalArgumentException e) {
                    System.out.println("x - обязано быть числом без каких-либо разделителей.");
                }
            }
        } while (x == null);

        do {
            System.out.println("Введите y, не может быть null.");
            String s = scanner.nextLine();
            if (s.equals("")) {
                y = null;

            } else {
                try {
                    y = Long.parseLong(s);
                } catch (IllegalArgumentException e) {
                    System.out.println("y - обязано быть числом без каких-либо разделителей.");
                }
            }
        } while (y == null);

        return x.toString() + "," + y.toString();
    }
    /**
     * Вспомогательный метод для чтения Id из командной строки
     *
     * @return возвращает объект класса String
     */
public String readId(){
    Scanner scanner = new Scanner(System.in);

    Integer y = null;
    do {
        System.out.println("Введите id, не может быть null.");
        String s = scanner.nextLine();
        if (s.equals("")) {
            y = null;

        } else {
            try {
                y = Integer.parseInt(s);
            } catch (IllegalArgumentException e) {
                System.out.println("id - обязано быть числом без каких-либо разделителей.");
            }
        }
    } while (y == null);
    //shoSystem.out.println(y);
    return y.toString();
}
    /**
     * Вспомогательный метод для чтения строки из командной строки
     *
     * @return возвращает объект класса String
     */
    public String readWorkName() {
        String name = "";
        while (name.equals("")) {
            System.out.println("Введите название лабораторной работы, поле не может быть пустой строкой");
            name = scanner.nextLine();
        }
        //System.out.println(name);
        return name;
    }

    /**
     * Вспомогательный метод для чтения строки из командной строки
     *
     * @return возвращает объект класса String
     */
    public String readPersonName() {
        String name = "";
        while (name.equals("")) {
            System.out.println("Введите имя автора, поле не может быть пустой строкой");
            name = scanner.nextLine();
        }
        // System.out.println(name);
        return name;
    }

    /**
     * Вспомогательный метод для чтения цены из командной строки
     *
     * @return возвращает цену Double
     */
    public Double readMinimalPoint() {
        Double point = null;
        do {
            System.out.println("Введите MinimalPoint, поле не может быть null, Значение поля должно быть больше 0");
            String s = scanner.nextLine();
            try {
                point = Double.parseDouble(s);
                if (point <= 0) {
                    System.out.println("MinimalPoint - обязано быть вещественным числом > 0. Формат ввода: 100.0 или 100");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("MinimalPoint - обязано быть вещественным числом > 0. Формат ввода: 100.0 или 100");
            }


        } while ((point == null) || (point <= 0));
        return point;
    }

    /**
     * Вспомогательный метод для чтения строки из командной строки
     *
     * @return возвращает объект класса String
     */
    public Date readBirth() throws ParseException, exceptions.WrongDate {
        String name = "";
        String[] arr = new String[0];
        boolean flag = Boolean.TRUE;
        Date date = null;
        while (flag) {
            try {
                System.out.println("Введите день рождения автора(Формат ввода: DD.MM.YYYY,HH.MM.SS). Можно не заполнять - Enter." + "\nВнимательно вводите данные, чтобы избежать ошибки(Например: часы не могут быть больше 23).");
                String s = scanner.nextLine();
                if (s.equals("")) {
                    name = null;
                    date = null;
                    flag = Boolean.FALSE;
                } else {
                    name = s;
                    String[] date_test = name.split(",");
                    String[] date_1 = date_test[0].split("\\.");
                    String[] date_2 = date_test[1].split("\\.");
                    if (Integer.parseInt(date_1[0]) <= 0 || Integer.parseInt(date_1[0]) > 31 || Integer.parseInt(date_1[1]) <= 0 || Integer.parseInt(date_1[1]) > 12 || Integer.parseInt(date_2[0]) < 0 || Integer.parseInt(date_2[0]) > 23 || Integer.parseInt(date_2[1]) < 0 || Integer.parseInt(date_2[1]) > 59 || Integer.parseInt(date_2[2]) < 0 || Integer.parseInt(date_2[2]) > 59) {
                        System.out.println("Неверный формат даты! Введите заново.");
                        flag = Boolean.TRUE;
                    } else {
                        date = new SimpleDateFormat("dd.MM.yyyy,hh.mm.ss").parse(name);
                        flag = Boolean.FALSE;
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERROR! Неправильный формат даты");
                flag = Boolean.TRUE;
            }
        }


        return date;
    }

    /**
     * Вспомогательный метод для чтения объекта UnitOfMeasure из командной строки
     *
     * @return возвращает объект класса UnitOfMeasure
     */
    public Difficulty readDifficulty() {
        Difficulty diff = null;
        boolean flag = Boolean.TRUE;
        while (flag) {
            try {
                System.out.println("Введите Difficulty, значение поля может быть равно: EASY, HARD, VERY_HARD, HOPELESS. Можно не заполнять - ENTER.");
                String s = scanner.nextLine();
                if (s.equals("")) {
                    diff = null;
                    flag = Boolean.FALSE;
                } else {
                    diff = Difficulty.valueOf(s);
                    flag = Boolean.FALSE;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERROR! Значение поля может быть равно: EASY, HARD, VERY_HARD, HOPELESS");
                flag = Boolean.TRUE;
            }
        }

        return diff;
    }

    /**
     * Вспомогательный метод для чтения объекта Person из командной строки
     *
     * @return возвращает объект класса Person
     */
    public String readPerson() throws ParseException {
        String name = readPersonName();
        Color color = readColor();
        Country nationality = readNationality();
        Date birth = readBirth();
        String p = null;
        if (nationality != null && birth != null) {
            p = name + " " + birth.toString() + " " + color.toString() + " " + nationality.toString();
        }
        if (nationality == null && birth != null) {
            p = name + " " + birth.toString() + " " + color.toString();
        }
        if (nationality == null && birth == null) {
            p = name + " " + color.toString();
        }
        //System.out.println(p);
        return p;

    }

    /**
     * Вспомогательный метод для чтения Color из командной строки
     *
     * @return возвращает объект Color
     */
    public Color readColor() {
        Color color = null;
        while (color == null) {
            try {
                System.out.println("Введите цвет глаз автора, значение поля может быть равно: RED, GREEN, ORANGE,BLACK, BROWN");
                color = Color.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("значение поля может быть равно: RED, GREEN, ORANGE,BLACK, BROWN");
            }
        }
        return color;
    }

    /**
     * Вспомогательный метод для чтения объекта Location из командной строки
     *
     * @return возвращает объект класса Location
     */
    public Country readNationality() {
        Country c = null;
        boolean flag = Boolean.TRUE;
        while (flag) {
            try {
                System.out.println("Введите национальность автора, значение поля может быть равно: USA, GERMANY, INDIA,VATICAN, SOUTH_KOREA. Поле может быть пустым - ENTER");
                String s = scanner.nextLine();
                if (s.equals("")) {
                    c = null;
                    flag = Boolean.FALSE;
                } else {
                    c = Country.valueOf(s);
                    flag = Boolean.FALSE;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("ERROR! Значение поля может быть равно: USA, GERMANY, INDIA,VATICAN, SOUTH_KOREA");
                flag = Boolean.TRUE;
            }
        }


        return c;
    }

}
