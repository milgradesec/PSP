package java_cifrado;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * EncryptedFileReader es un flujo de entrada de ficheros que descifra el
 * contenido al leer.
 */
public class EncryptedFileReader extends FileInputStream {

    private Cipher cipher;

    /**
     * Crea un nuevo flujo EncryptedFileReader.
     * 
     * @param name nombre del fichero
     * @param key  clave de descifrado
     * @throws FileNotFoundException si no puede acceder al fichero
     */
    public EncryptedFileReader(String name, SecretKey key) throws FileNotFoundException {
        super(name);
        initCrypto(key);
    }

    /**
     * initCrypto inicializa la instancia de descifrado.
     * 
     * @param key clave de descifrado
     */
    public void initCrypto(SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
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
    public byte[] readAllBytes() throws IOException {
        byte[] buffer;
        try {
            buffer = cipher.doFinal(super.readAllBytes());
            return buffer;
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
