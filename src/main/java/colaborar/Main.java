package colaborar;

import java.io.IOException;

public class Main {

    /**
     * Ejecuta varias instancias de "lenguaje" para que colaboren en crear un fichero.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            return;
        }

        int nInstances = Integer.parseInt(args[0]);
        String appPath = args[1];
        int nLines = Integer.parseInt(args[2]);
        String outFile = args[3];

        try {
            for (int i = 0; i < nInstances; i++) {
                Runtime.getRuntime().exec("java -jar " + appPath + " " + nLines + " " + outFile);
                nLines += 10;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
