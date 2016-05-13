package iia.com.surveillanceproject.com.Asymetric;

import android.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 26/01/2016.
 */
public class Descrypt {


    /**
     * Decrypt message
     *
     * @param input     message to decrypt
     * @param secretKey secret key
     * @param iv        init vector
     * @return message decrypted
     */
    public static String decryptMessage(String input, String secretKey, IvParameterSpec iv) {

        try {

            byte[] kc = Base64.decode(secretKey, Base64.DEFAULT);
            final byte[] message = Base64.decode(input, Base64.DEFAULT);
            final byte[] cipherText = CipherData.cipherDecryptAES(message, kc, iv);

            return new String(cipherText, "UTF-8");

        } catch (Exception e) {

            System.out.println(e);
        }
        return null;
    }

    /**
     * Decrypt file
     *
     * @param fichier file to decrypt
     * @return file decrypted path
     * @throws IOException
     */
    public static String decryptFile(File fichier) throws IOException {

        byte[] bytes = FileUtils.readFileToByteArray(fichier);

        byte[] iv = new byte[16];
        for (int i = 0; i < 16; i++) {
            iv[i] = bytes[i];
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);


        byte[] secretKey = new byte[256];
        int j = 0;
        for (int i = iv.length; i < iv.length + secretKey.length; i++) {
            secretKey[j] = bytes[i];
            j++;
        }

        byte[] file = new byte[bytes.length - (iv.length + secretKey.length)];
        j = 0;
        for (int i = iv.length + secretKey.length; i < bytes.length; i++) {
            file[j] = bytes[i];
            j++;
        }

        String skDecrypted = SecretKey.decryptKc(Base64.encodeToString(secretKey, Base64.NO_PADDING));
        byte[] skdecryptedbyte = Base64.decode(skDecrypted, Base64.NO_PADDING);
        byte[] filedecrypted = CipherData.cipherDecryptAES(file, Base64.decode(skDecrypted, Base64.NO_PADDING), ivParameterSpec);


        FileOutputStream fEncrypted;
        try {
            String fileNameWithOutExt = FilenameUtils.removeExtension(fichier.getPath());
            String fileExt = FilenameUtils.getExtension(fichier.getPath());
            String pathEncrypted = fileNameWithOutExt + "_decrypted." + fileExt;
            fEncrypted = new FileOutputStream(pathEncrypted);
            fEncrypted.write(filedecrypted);
            fEncrypted.close();

            return pathEncrypted;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
