import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {

    /**
     * Отправная точка клиента. Создает объект {@code ClientConnection}.
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Запуск клиентского модуля.\nПодключение к серверу ...");

        DataReader reader=new DataReader();
        Client connection = new Client(reader);
        connection.work();
    }
}