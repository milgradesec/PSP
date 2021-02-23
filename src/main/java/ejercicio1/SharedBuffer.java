package ejercicio1;

import java.util.ArrayList;

/**
 * SharedBuffer implementa un buffer compartido para ser utilizado por varios
 * hilos a la vez.
 */
public class SharedBuffer {

    private final ArrayList<String> buffer;
    private final int MAX_SIZE;

    /**
     * Crea un objeto SharedBuffer.
     *
     * @param maxSize tamaño máximo del buffer
     */
    public SharedBuffer(int maxSize) {
        this.MAX_SIZE = maxSize;
        this.buffer = new ArrayList<>(MAX_SIZE);
    }

    /**
     * Deposita un dato en el buffer, bloquea si esta lleno.
     *
     * @param data dato a insertar
     */
    public synchronized void insert(String data) {
        try {
            while (buffer.size() >= MAX_SIZE) {
                wait();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        buffer.add(data);
        notify();

        System.out.println("Depositado el caracter " + data + " en el buffer.");
    }

    /**
     * Recoge el último dato depositado en el buffer, bloquea si esta vacío.
     *
     * @return el último dato depositado
     */
    public synchronized String retrieve() {
        try {
            while (buffer.isEmpty()) {
                wait();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        String data = buffer.get(buffer.size() - 1);
        buffer.remove(buffer.size() - 1);
        notify();

        System.out.println("Recogido el caracter " + data + " del buffer.");
        return data;
    }
}
