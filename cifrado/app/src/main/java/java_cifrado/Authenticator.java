package java_cifrado;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Authenticator provee métodos para validar que el nombre de usuario y
 * contraseña tienen un formato específico.
 */
public class Authenticator {

    /**
     * login valida y autentica un usuario.
     * 
     * @param username Nombre del usuario
     * @param password Contraseña del usuario
     * @return True si ambos son válidos o False si alguno no lo es
     */
    public static boolean validateLogin(String username, String password) {
        if (!validateUsername(username)) {
            return false;
        }
        if (!validatePassword(password)) {
            return false;
        }
        return true;
    }

    /**
     * validateUsername comprueba que el nombre introducido cumple los requisitos
     * especificados.
     * 
     * @param username Nombre de usuario
     * @return True si es válido o False si no
     */
    public static boolean validateUsername(String username) {
        Pattern p = Pattern.compile("[a-z]{8}");
        Matcher m = p.matcher(username);
        return m.find();
    }

    /**
     * validatePassword comprueba que la contraseña introducida cumple los
     * requisitos especificados.
     * 
     * @param password Contraseña
     * @return True si es válido o False si no
     */
    public static boolean validatePassword(String password) {
        Pattern p = Pattern.compile("[a-zA-Z0-9]{10,30}");
        Matcher m = p.matcher(password);
        return m.find();
    }
}
