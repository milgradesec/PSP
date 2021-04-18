package java_cifrado;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * EncryptedFileWriter es un flujo de salida de ficheros que cifra todos los
 * datos que se escriben en el.
 */
public class EncryptedFileWriter extends FileOutputStream {

    private Cipher cipher;

    /**
     * Crea un nuevo flujo EncryptedFileWriter.
     * 
     * @param name nombre del fichero
     * @param key  clave de cifrado
     * @throws FileNotFoundException si no puede acceder al fichero
     */
    public EncryptedFileWriter(String name, SecretKey key) throws FileNotFoundException {
        super(name);
        initCrypto(key);
    }

    /**
     * initCrypto inicializa la instancia de cifrado.
     * 
     * @param key clave de cifrado
     */
    private void initCrypto(SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            this.cipher = cipher;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        byte[] buffer;
        try {
            buffer = cipher.doFinal(b);
            super.write(buffer);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }
}
