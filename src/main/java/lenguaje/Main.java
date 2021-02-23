package lenguaje;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class Main {

    /**
     * Genera palabras en un idioma inventado.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }

        int nPalabras = Integer.parseInt(args[0]);
        RandomCharGenerator rcg = new RandomCharGenerator();

        try (RandomAccessFile raf = new RandomAccessFile(new File(args[1]), "rwd")) {
            // adquirir exclusividad del archivo
            FileLock lock = raf.getChannel().lock();

            // desplazarse hasta el final del archivo
            raf.seek(raf.length());

            for (int i = 0; i < nPalabras; i++) {
                for (int j = 0; j < 15; j++) {
                    raf.writeChar(rcg.generate());
                }
                raf.writeChar('\n');
            }

            // liberar el archivo
            lock.release();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
