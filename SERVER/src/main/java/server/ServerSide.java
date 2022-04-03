package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

public class ServerSide {
    private static final Logger logger = Logger.getLogger("Logger");
    /**
     * Точка входа в программу. Управляет подключением к клиентам и созданием потоков для каждого из них.
     *
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String inPath = "C:\\Users\\Asus\\IdeaProjects\\LAB6\\SERVER\\src\\main\\java\\server\\in.json";
        String outPath = "C:\\Users\\Asus\\IdeaProjects\\LAB6\\SERVER\\src\\main\\java\\server\\out.json";

            try (ServerSocket server = new ServerSocket(8000)) {
                CollectionManager serverCollection = new CollectionManager(args[0],args[1]);
                logger.log(Level.INFO,"Сервер начал слушать клиентов. " + "\nПорт " + server.getLocalPort() +
                        " / Адрес " + InetAddress.getLocalHost() + ".\nОжидаем подключения клиентов ... ");
                while (true) {
                    Socket incoming = server.accept();
                    logger.log(Level.INFO,incoming + " подключился к серверу.");
                    Server server1 = new Server(serverCollection, incoming);
                   server1.run();
                }

            } catch (IOException ex) {
                logger.log(Level.SEVERE,"Ошибка получения локального хоста.");
                System.exit(1);
            }catch (ArrayIndexOutOfBoundsException e){
                logger.log(Level.SEVERE,"Не был передан путь к файлу при запуске программы.");

        }

    }
}