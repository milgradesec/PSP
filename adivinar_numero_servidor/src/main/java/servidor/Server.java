package servidor;

import java.io.*;
import java.net.*;

/**
 * Server implementa un servidor TCP en el puerto 2000 que genera un número
 * aleatorio y los clientes que tienen que adivinarlo. Acepta un número
 * indefinido de cliente y los atiende de forma concurrente.
 */
public class Server {

    public Server() {
        try (ServerSocket ss = new ServerSocket(2000)) {
            while (true) {
                Socket client = ss.accept();

                new ClientHandler(client).start();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

}
