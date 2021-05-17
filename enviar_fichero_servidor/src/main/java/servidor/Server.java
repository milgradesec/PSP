package servidor;

import java.net.Socket;

import java.io.*;
import java.net.*;

/**
 * Server implementa un servidor TCP que envía un fichero solicitado por el
 * cliente.
 */
public class Server {

    public Server() {
        try (ServerSocket ss = new ServerSocket(1500)) {
            while (true) {
                Socket client = ss.accept();

                new ClientHandler(client).start();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
