package server;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import commands.ClearCommand;
import data.LabWork;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import static commands.AddCommand.create_id;

/**
 * Класс {@code CollectionManager} обеспечивает доступ к коллекции.
 *
 * @author Соболев Иван
 * @since 25.03.2022
 */
public class CollectionManager<TypeToken> {
    private File outPut;
    private Vector<LabWork> works = new Vector<>();
    private File jsonCollection;
    private Type collectionType;
    private Date initDate;
    private Gson serializer;
    protected static HashMap<String, String> manual;

    {
        serializer = new Gson();
        works = new Vector<LabWork>();
        collectionType = works.getClass();
       // System.out.println(collectionType);
    }

    /**
     * Предоставляет доступ к коллекции и связанному с ней файлу.
     *
     * @param collectionPath путь к файлу коллекции в файловой системе.
     */
    CollectionManager(String collectionPath,String output) {
        this.outPut=new File(output);
        try {
            if (collectionPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.err.println("Путь к файлу должен быть задан через командную строку.");
            System.exit(1);
        }
        jsonCollection = new File(collectionPath);
        try {
            String extension = jsonCollection.getAbsolutePath().substring(jsonCollection.getAbsolutePath().lastIndexOf(".") + 1);
            if (!jsonCollection.exists() | !extension.equals("json")) throw new FileNotFoundException();
            if (jsonCollection.length() == 0) throw new Exception("Файл пуст");
            if (!jsonCollection.canRead() || !jsonCollection.canWrite()) throw new SecurityException();
            try (BufferedReader collectionReader = new BufferedReader(new FileReader(jsonCollection))) {
                System.out.println("Загрузка коллекции " + jsonCollection.getAbsolutePath());
                String nextLine;
                StringBuilder result = new StringBuilder();
                while ((nextLine = collectionReader.readLine()) != null) {
                    result.append(nextLine);

                }


                //System.out.println(result);

                Type collectionType = new com.google.gson.reflect.TypeToken<Vector<LabWork>>() {
                }.getType();
                try {
                    works = serializer.fromJson(result.toString(), collectionType);
                    System.out.println("Коллекция успешно загружена. Добавлено " + (works.size()) + " элементов.");
                
                    // System.out.println(works);
                    for (LabWork element : works) {
                        int id = create_id();
                        element.setId(id);
                        if (element.getDate() != null) {
                            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
                            Date cdate = formatter.parse(element.getDate());
                            element.setCreationDate(cdate);

                        }

                    }

                } catch (JsonSyntaxException ex) {
                    System.out.println("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                    System.exit(1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл по указанному пути не найден или он пуст.");
            System.exit(1);
        } catch (SecurityException e) {
            System.err.println("Файл защищён от чтения.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Что-то не так с файлом.");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Файл пуст");
            System.exit(1);
        }
    }

    public CollectionManager() {
        System.out.println("Не был передан путь к файлу при запуске программы.");
        System.exit(1);
    }

    /**
         * Записывает элементы коллекции в файл. Так как необходим нескольким командам, реализован в этом классе.
         */
        public void save() {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((outPut))))) {
                writer.write(serializer.toJson(works));
              //  System.out.println("Коллекция успешно сохранена.");
            } catch (Exception ex) {
                System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть записана в файл.");
            }
        }

        public Vector<LabWork> getWorks () {
            return works;
        }



    public File getJsonCollection () {
            return jsonCollection;
        }

        public Gson getSerializer () {
            return serializer;
        }

        public Date getInitDate () {
            return initDate;
        }

        public Type getCollectionType () {
            return collectionType;
        }
        /**
         * @return current date.
         */
        public static Date create_date () {

            Date date = new Date();
            return date;
        }
        @Override
        public String toString () {
            return "Тип коллекции: " + works.getClass() +
                    "\nТип элементов: " + LabWork.class +
                    "\nДата инициализации: " + initDate +
                    "\nКоличество элементов: " + works.size();
        }
    }
