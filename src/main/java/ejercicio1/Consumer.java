package ejercicio1;

/**
 * Consumer implementa un hilo consumidor que recoge elementos de un buffer.
 */
public class Consumer extends Thread {

    private final SharedBuffer buffer;

    /**
     * Crea un objeto Consumer
     *
     * @param buffer buffer para recoger datos
     */
    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            buffer.retrieve();
        }
    }
}
