package aleatorios;

import java.util.Random;

public class Main {

    /**
     * Genera números aleatorios.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Random rnd = new Random();

        for (int i = 0; i < 40; i++) {
            System.out.println(rnd.nextInt(101));
        }

        // añadir una linea vacía para indicar el final.
        System.out.println("");
    }
}
