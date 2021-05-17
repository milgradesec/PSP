package servidor;

import java.net.Socket;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Server implementa un servidor TCP que envía un fichero solicitado por el
 * cliente.
 */
public class Server {

    public Server() {
        try (ServerSocket ss = new ServerSocket(1500)) {
            while (true) {
                Socket client = ss.accept();

                connHandler(client);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * connHandler atiende la conexión del cliente respondiendo sus peticiones.
     * 
     * @param conn socket de la conexión con el cliente
     */
    private void connHandler(Socket conn) {
        try {
            // Crear flujos de entrada y salida
            DataInputStream in = new DataInputStream(conn.getInputStream());
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            while (true) {
                String fileName = in.readUTF();

                if (checkFileExists(fileName)) {
                    sendFile(fileName, out);
                    break;
                }

                out.writeUTF("Error: el fichero no existe");
            }

            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * checkFileExists comprueba si existe el fichero en la ruta indicada.
     * 
     * @param fileName ruta completa del fichero
     * @return true si existe o false si no
     */
    private boolean checkFileExists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * sendFile envía un fichero al cliente. Lee todo el contenido del fichero en
     * memoria y lo escribe por el flujo de salida aportado.
     * 
     * @param fileName ruta completa del fichero
     * @param out      flujo de salida de la conexión con el cliente
     * @throws IOException si ocurre un error de E/S
     */
    private void sendFile(String fileName, DataOutputStream out) throws IOException {
        Path path = Path.of(fileName);
        String content = Files.readString(path);
        out.writeUTF(content);
    }
}
