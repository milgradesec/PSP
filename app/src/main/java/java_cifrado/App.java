package java_cifrado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class App {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Introduce usuario: ");
            String username = reader.readLine();

            System.out.print("Introduce contraseña: ");
            String password = reader.readLine();

            boolean ok = Authenticator.validateLogin(username, password);
            if (!ok) {
                System.out.println("Nombre de usuario o contraseña no son válidos");
                System.exit(0);
            }

            String seed = username.concat(password);
            SecureRandom sRandom = new SecureRandom();
            sRandom.setSeed(seed.getBytes());

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, sRandom);
            SecretKey key = keyGen.generateKey();

            RandomCharGenerator rcg = new RandomCharGenerator();
            String msg = rcg.generateString(50);

            System.out.printf("Cifrando mensaje '%s' en el fichero 'fichero.cifrado'\n", msg);
            EncryptedFileWriter efw = new EncryptedFileWriter("fichero.cifrado", key);
            efw.write(msg.getBytes());
            efw.close();
            System.out.println("Hecho");

            System.out.println("Descifrando el mensaje...");
            EncryptedFileReader efr = new EncryptedFileReader("fichero.cifrado", key);
            byte[] content = efr.readAllBytes();
            efr.close();
            System.out.printf("Contenido: %s\n", new String(content));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
