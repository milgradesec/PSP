package ejercicio1;

import java.util.Random;

/**
 * Producer implementa un hilo productor que genera y deposita caracteres
 * aleatorios en un buffer.
 */
public class Producer extends Thread {

    private final SharedBuffer buffer;
    private final Random rnd;

    /**
     * Crea un objeto Producer.
     *
     * @param buffer buffer para insertar datos
     */
    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
        this.rnd = new Random();
    }

    /**
     * Produce un caracter aleatorio en cada invocación.
     *
     * @return un caracter aleatorio
     */
    private char produce() {
        return (char) ('a' + rnd.nextInt(26));
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            String data = String.valueOf(produce());
            buffer.insert(data);
        }
    }
}
