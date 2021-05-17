package cliente;

import java.io.*;
import java.net.*;

/**
 * Client implementa el cliente TCP
 */
public class Client {

    public Client(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Conexión establecida con el servidor");

            // Crear flujos de entrada y salida.
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // Leer la entrada del usuario y enviarla al servidor.
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            String response = null;
            do {
                System.out.print("Introduce la ruta de un fichero: ");

                line = br.readLine();
                out.writeUTF(line);

                response = in.readUTF();
                System.out.println(response);

                if (!response.contains("Error")) {
                    break;
                }
            } while (line != null && response != null);

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

}
