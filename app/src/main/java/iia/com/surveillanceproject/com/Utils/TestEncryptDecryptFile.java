package iia.com.surveillanceproject.com.Utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import iia.com.surveillanceproject.com.Asymetric.Descrypt;
import iia.com.surveillanceproject.com.Asymetric.Encrypt;
import iia.com.surveillanceproject.com.Entity.Fichier;

/**
 * Created by Thom' on 10/05/2016.
 */
public class TestEncryptDecryptFile {

    public static void decrypt() throws IOException {
        String path = Environment.getExternalStorageDirectory().toString() + "/Download/";

        File f = new File(path);
        File file[] = f.listFiles();

        for (int i = 0; i < file.length; i++) {
            String fileName = file[i].getName();
            String fileLastModifier = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(
                    new Date(file[i].lastModified()));

            String pathfile = file[i].getPath();
            Fichier fichier = new Fichier();

            fichier.setName(fileName);
            fichier.setLastDateModifier(fileLastModifier);
            fichier.setPath(pathfile);


            //encrypt file

            /**
             * Read file byte
             */

            File fichierALire = new File(fichier.getPath());
            /**
             * Encrypt file
             */
            String encryptedFile = Encrypt.encryptFile(fichierALire);

            /**
             * Read fileEncrypted byte
             */
            File fichierEncrypt = new File(encryptedFile);

            /**
             * Decrypt file
             */

            Descrypt.decryptFile(fichierEncrypt);


        }
    }

}
