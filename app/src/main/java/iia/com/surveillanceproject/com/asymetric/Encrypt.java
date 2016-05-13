package iia.com.surveillanceproject.com.Asymetric;

import android.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 20/01/2016.
 */
public class Encrypt {


    /**
     * Encrypt message
     *
     * @param text message to encrypt
     * @param kc   secret key
     * @param iv   init vector
     * @return message encrypted
     */
    public static String encryptMessage(String text, byte kc[], IvParameterSpec iv) {

        try {

            final byte[] inputText = text.getBytes("UTF-8");
            final byte[] cipherText = CipherData.cipherEncryptAES(inputText, kc, iv);
            String message = Base64.encodeToString(cipherText, Base64.NO_PADDING);

            return message;

        } catch (Exception e) {

            System.out.println(e);
        }
        return null;
    }

    /**
     * Encrypt file
     *
     * @param fichier file to encrypt
     * @return encrypted file path
     */
    public static String encryptFile(File fichier) {
        IvParameterSpec iv = Iv.GenerateIv();
        byte[] secretKey = SecretKey.GenerateKc();

        final String secretKeyEncrypted = SecretKey.encryptKc(secretKey);
        try {
            byte[] bytes = FileUtils.readFileToByteArray(fichier);

            final byte[] encryptedFile = CipherData.cipherEncryptAES(bytes, secretKey, iv);

            FileOutputStream fEncrypted;
            try {
                String fileNameWithOutExt = FilenameUtils.removeExtension(fichier.getPath());
                String fileExt = FilenameUtils.getExtension(fichier.getPath());
                String pathEncrypted = fileNameWithOutExt + "_encrypted." + fileExt;
                fEncrypted = new FileOutputStream(pathEncrypted);
                fEncrypted.write(iv.getIV());
                fEncrypted.write(Base64.decode(secretKeyEncrypted, Base64.DEFAULT));
                fEncrypted.write(encryptedFile);
                fEncrypted.close();

                return pathEncrypted;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
