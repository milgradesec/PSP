package servidor;

import java.net.Socket;
import java.util.Random;
import java.io.*;

/**
 * ClientHandler atiende la conexión de un cliente, procesando los mensajes y
 * respondiendo, esta pensada para ejecutar una nueva instancia de ClientHandler
 * en un hilo nuevo para cada cliente que se conecta.
 */
public class ClientHandler extends Thread {

    /**
     * Generador de números aleatorios.
     */
    private static Random rnd = new Random();

    /**
     * Socket de la conexión con el cliente.
     */
    private final Socket conn;

    /**
     * Crea un nuevo objeto ClientHandler.
     * 
     * @param conn socket de la conexión con el cliente
     */
    public ClientHandler(Socket conn) {
        this.conn = conn;
    }

    /**
     * run atiende la conexión del cliente en un hilo nuevo, genera el secreto y
     * responde a los mensajes que le envía el cliente.
     */
    @Override
    public void run() {
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
