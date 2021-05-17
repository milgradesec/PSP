package ejercicio1;

public class Main {

    /**
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(6);
        new Producer(buffer).start();
        new Consumer(buffer).start();
    }
}
