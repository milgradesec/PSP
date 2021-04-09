package login_fichero;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    /**
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("login_fichero");
        try {
            // Configurar el logger.
            FileHandler fh = new FileHandler("log.txt", true);
            SimpleFormatter fmt = new SimpleFormatter();
            fh.setFormatter(fmt);
            logger.setUseParentHandlers(false);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Solicitar un nombre de usuario.
            System.out.print("Introduce usuario: ");
            String usuario = reader.readLine();
            if (!validarNombreUsuario(usuario)) {
                System.out.println("Nombre de usuario no válido.");
                logger.log(Level.WARNING, "Introducido nombre de usuario no válido: '" + usuario + "'");
                return;
            }
            logger.log(Level.INFO, "Nueva sesión para usuario '" + usuario + "'");

            // Solicitar el nombre de un fichero al usuario.
            System.out.print("Introduce el nombre del archivo: ");
            String fichero = reader.readLine();
            if (!validarNombreFichero(fichero)) {
                System.out.println("El nombre del fichero no cumple los requisitos.");
                logger.log(Level.WARNING,
                        "Introducido nombre de fichero que no cumple los requisitos: '" + fichero + "'");
                return;
            }

            // Comprobar si el fichero existe.
            File f = new File(fichero);
            if (!f.exists()) {
                System.out.println("El fichero solicitado no existe.");
                logger.log(Level.WARNING, "Solicitado fichero que no existe: '" + fichero + "'");
                return;
            }

            // Mostrar el contenido del fichero.
            Path path = Path.of(fichero);
            String content = Files.readString(path);
            System.out.println(content);
            logger.log(Level.INFO, "Accedido el fichero '" + fichero + "'");

        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
            logger.log(Level.SEVERE, "Error de E/S: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Error: no tienes permiso para acceder al fichero: " + e.getMessage());
            logger.log(Level.SEVERE, "Error: no tienes permiso para acceder al fichero: " + e.getMessage());
        } catch (InvalidPathException e) {
            System.out.println("Error: la ruta del fichero no es válida " + e.getMessage());
            logger.log(Level.SEVERE, "Error: la ruta del fichero no es válida " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: se ha producido una excepción no esperada: " + e.getMessage());
            logger.log(Level.SEVERE, "Error: se ha producido una excepción no esperada: " + e.getMessage());
        }
    }

    /**
     * validarNombreUsuario comprueba que el nombre introducido cumple los
     * requisitos especificados.
     * 
     * @param usuario Nombre de usuario
     * @return True si es válido o False si no
     */
    public static boolean validarNombreUsuario(String usuario) {
        Pattern p = Pattern.compile("[a-z]{8}");
        Matcher m = p.matcher(usuario);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * validarNombreFichero comprueba si el nombre del fichero introducido cumple
     * los requisitos especificados.
     * 
     * @param fichero Nombre del fichero
     * @return True si es válido o False si no
     */
    public static boolean validarNombreFichero(String fichero) {
        Pattern p = Pattern.compile("[a-z]{1,8}[.][a-z]{3}");
        Matcher m = p.matcher(fichero);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
