package ordenarNumeros;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;

public class Main {

    /**
     * Recibe una cantidad indefinida de números por la entrada estándar, los ordena
     * y escribe en la salida estándar.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            List<Integer> list = new ArrayList<Integer>();

            // leer la entrada línea por línea
            String line;
            while ((line = br.readLine()) != null) {

                // al recibir una línea vacía se da por terminado el conjunto de números
                if (line.isEmpty()) {
                    break;
                }
                list.add(Integer.parseInt(line));
            }

            // ordenar la lista y mostrarla
            list.sort(null);
            System.out.println(list);

        } catch (IOException ex) {
            System.err.println("Se ha producido un error de E/S.");
            System.err.println(ex.toString());
        }
    }
}
