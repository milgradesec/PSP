package ejercicio2;

import java.util.concurrent.Semaphore;

public class Main {

    /**
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {

        Semaphore[] chopsticks = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            chopsticks[i] = new Semaphore(1, true);
        }

        for (int i = 1; i <= 5; i++) {
            new Philosopher(i, chopsticks).start();
        }
    }

}
