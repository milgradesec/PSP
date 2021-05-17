package cliente;

import java.io.*;
import java.net.*;

/**
 * Client implementa el cliente TCP.
 */
public class Client {

    public Client(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Conexión establecida con el servidor");

            // Crear flujos de entrada y salida.
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // Pedir al usuario que introduzca nombre de usuario y contraseña y enviarlo al
            // servidor, repetir hasta tener éxito.
            while (true) {
                System.out.print("Introduce usuario y contraseña: ");

                String login = br.readLine();
                output.writeUTF(login);

                String response = input.readUTF();
                System.out.println(response);

                if (!response.contains("Error")) {
                    break;
                }
            }

            // Bucle de recibir mensaje del servidor y responder con la orden del usuario
            String serverMsg = null;
            String command = null;
            do {
                serverMsg = input.readUTF();
                System.out.print(serverMsg);

                command = br.readLine();
                output.writeUTF(command);
            } while (command != null && serverMsg != null);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
