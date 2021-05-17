package lenguaje;

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
     * Cada invocación genera una letra.
     * 
     * @return un caracter entre 'a' y 'z'
     */
    public char generate() {
        return (char) ('a' + rnd.nextInt(26));
    }
}
