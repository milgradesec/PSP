package java_cifrado;

import java.util.Random;

/**
 * Genera caracteres aleatorios.
 */
public class RandomCharGenerator {

    private final Random rnd;

    public RandomCharGenerator() {
        this.rnd = new Random();
    }

    /**
     * generate en invocación genera un caracter aleatorio.
     * 
     * @return un caracter entre 'a' y 'z'
     */
    public char generate() {
        return (char) ('a' + rnd.nextInt(26));
    }

    /**
     * generateString crea una cadena de caracteres aleatorios con el tamaño
     * especificado.
     * 
     * @param size tamaño de la cadena
     * @return cadena generada
     */
    public String generateString(int size) {
        if (size > 0) {
            char[] array = new char[size];
            for (int i = 0; i < size; i++) {
                array[i] = generate();
            }
            return new String(array);
        }
        return "";
    }
}