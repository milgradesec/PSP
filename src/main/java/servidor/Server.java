package servidor;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Server implementa un servidor TCP en el puerto 2000 que genera un número
 * aleatorio y los clientes que tienen que adivinarlo. Puede recibir un número
 * indefinido de conexiones pero no de forma concurrente.
 */
public class Server {

    /**
     * Generador de números aleatorios
     */
    private static Random rnd = new Random();

    public Server() {
        try (ServerSocket ss = new ServerSocket(2000)) {
            while (true) {
                Socket client = ss.accept();

                connHandler(client);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * connHandler recibe la conexión de un cliente, genera el secreto (distinto
     * para cada cliente) y responde a los mensajes que le envía el cliente.
     * 
     * @param conn socket de la conexión a un cliente
     */
    private void connHandler(Socket conn) {
        try {
            int secret = generateSecret();

            DataInputStream in = new DataInputStream(conn.getInputStream());
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            while (true) {
                String msg = in.readUTF();
                int guess = Integer.parseInt(msg);

                if (guess == secret) {
                    out.writeUTF("Correcto");
                    break;
                }

                if (guess > secret) {
                    out.writeUTF(guess + " es mayor");
                } else {
                    out.writeUTF(guess + " es menor");
                }
            }

            conn.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * generateSecret genera el número aleatorio que el cliente tiene que adivinar.
     * 
     * @return un número aleatorio entre 0 y 100
     */
    private int generateSecret() {
        return rnd.nextInt(101);
    }
}
