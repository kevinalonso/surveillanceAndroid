package iia.com.surveillanceproject.com.utils;

import android.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.spec.IvParameterSpec;

import iia.com.surveillanceproject.com.asymetric.CipherData;
import iia.com.surveillanceproject.com.asymetric.Iv;
import iia.com.surveillanceproject.com.asymetric.SecretKey;
import iia.com.surveillanceproject.com.entity.Fichier;

/**
 * Created by Thom' on 09/05/2016.
 */
public class EncryptFile {



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
