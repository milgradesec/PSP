package servidor;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ClientHandler atiende la conexión de un cliente, procesando los mensajes y
 * respondiendo, esta pensada para ejecutar una nueva instancia de ClientHandler
 * en un hilo nuevo para cada cliente que se conecta.
 */
public class ClientHandler extends Thread {

    /**
     * Socket de la conexión con el cliente.
     */
    private final Socket conn;

    /**
     * Crea un nuevo objeto ClientHandler.
     * 
     * @param conn socket de la conexión con un cliente
     */
    public ClientHandler(Socket conn) {
        this.conn = conn;
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

    /**
     * listDirectory muestra el contenido del directorio actual al cliente.
     * 
     * @param out flujo de salida de la conexión con el cliente
     * @throws IOException si ocurre un error de E/S
     */
    private void listDirectory(DataOutputStream out) throws IOException {
        File cwd = new File(System.getProperty("user.dir"));

        String response = null;
        for (String file : cwd.list()) {
            response += file;
            response += "\n";
        }
        out.writeUTF(response);
    }

    /**
     * authenticateClient autentica al cliente con usuario y contraseña.
     * 
     * @param loginMsg mensaje del cliente en formato usuario:contraseña
     * @return true si el par usuario/contraseña son válidos, false si no lo son
     */
    private boolean authenticateClient(String loginMsg) {
        String[] loginData = loginMsg.split(":");

        if (loginData.length != 2) {
            return false;
        }

        String username = loginData[0];
        String password = loginData[1];

        if (username.equals("usuario") && password.equals("secreto")) {
            return true;
        }
        return false;
    }

    /**
     * run atiende la conexión del cliente respondiendo sus peticiones.
     */
    @Override
    public void run() {
        try {
            // Crear flujos de entrada y salida
            DataInputStream in = new DataInputStream(conn.getInputStream());
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());

            // Autenticar al cliente
            while (true) {
                String loginMsg = in.readUTF();

                if (authenticateClient(loginMsg)) {
                    out.writeUTF("Autenticación correcta.\n");
                    break;
                }
                out.writeUTF("Error: usuario/contraseña no válidos.");
            }

            // Implementación del diagrama de estados
            int state = 1;
            String command = null;
            do {
                switch (state) {
                    case 1:
                        out.writeUTF("Introduce comando (ls/get/exit): ");

                        do {
                            command = in.readUTF();
                        } while (command.equals(""));

                        if (command.equals("ls")) {
                            state = 2;
                            break;
                        } else if (command.equals("get")) {
                            state = 3;
                            break;
                        } else if (command.equals("exit")) {
                            state = -1;
                            break;
                        } else {
                            out.writeUTF("Error: comando no reconocido.\n");
                            state = 1;
                            break;
                        }
                    case 2:
                        listDirectory(out);
                        state = 1;
                        break;
                    case 3:
                        out.writeUTF("Introduce el nombre del archivo: ");
                        String fileName = in.readUTF();
                        if (checkFileExists(fileName)) {
                            sendFile(fileName, out);
                        } else {
                            out.writeUTF("Error: el fichero no existe\n");
                        }
                        state = 1;
                        break;
                    default:
                        break;
                }
            } while (state != -1);

            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
