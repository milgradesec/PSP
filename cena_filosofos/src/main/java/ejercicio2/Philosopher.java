package ejercicio2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Philisopher implementa una solución al problema de "La cena de los
 * filósofos".
 */
public class Philosopher extends Thread {
    /**
     * Identificador del filósofo.
     */
    private final int id;

    /**
     * Array de semáforos que simulan los palillos de los filósofos.
     */
    private Semaphore[] chopsticks;

    /**
     * Generador de números pseudoaleatorios para las esperas.
     */
    private final Random rnd;

    /**
     * Crea un objeto Philosopher.
     * 
     * @param id         identificador numérico para el filósofo
     * @param chopsticks array de semáforos (inicializados)
     */
    public Philosopher(int id, Semaphore[] chopsticks) {
        this.id = id;
        this.rnd = new Random();
        this.chopsticks = chopsticks;
    }

    /**
     * Adquiere los palillos adyacentes al filósofo en función de su id, bloquea
     * hasta que ambos estan disponibles. Para evitar un deadlock adquiere el
     * primero e intenta adquirir el segundo durante una cantidad de tiempo, si no
     * lo consigue liberará el primero y volverá a empezar.
     */
    private void acquireChopstics() {
        boolean acquired = false;

        do {
            try {
                chopsticks[id - 1].acquire();
                acquired = chopsticks[id % 5].tryAcquire(500 + rnd.nextInt(300), TimeUnit.MILLISECONDS);
                if (!acquired) {
                    chopsticks[id - 1].release();
                    System.out.println("Filósofo" + id + " tiene hambre");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (acquired == false);
    }

    /**
     * Libera los palillos utilizados para que puedan ser adquiridos por otro hilo.
     */
    private void releaseChopstics() {
        chopsticks[id - 1].release();
        chopsticks[id % 5].release();
    }

    /**
     * Simula pensar durante un tiempo aleatorio entre 2s y 3s.
     */
    public void think() {
        System.out.println("Filósofo" + id + " pensando");

        try {
            sleep(2000 + rnd.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simula comer durante un tiempo aleatorio entre 1s y 2s.
     */
    public void eat() {
        System.out.println("Filósofo" + id + " comiendo con los palillos " + (id - 1) + " y " + (id % 5));

        try {
            sleep(1000 + rnd.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Filósofo" + id + " termina de comer y deja libres los palillos " + (id - 1) + " y " + (id % 5));
    }

    @Override
    public void run() {
        while (true) {
            think();
            acquireChopstics();
            eat();
            releaseChopstics();
        }
    }
}
