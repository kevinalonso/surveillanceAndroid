package iia.com.surveillanceproject.com.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.spec.IvParameterSpec;

import cz.msebera.android.httpclient.Header;
import iia.com.surveillanceproject.R;
import iia.com.surveillanceproject.com.asymetric.Descrypt;
import iia.com.surveillanceproject.com.asymetric.Encrypt;
import iia.com.surveillanceproject.com.asymetric.Iv;
import iia.com.surveillanceproject.com.asymetric.SecretKey;
import iia.com.surveillanceproject.com.entity.Fichier;
import iia.com.surveillanceproject.com.entity.User;
import iia.com.surveillanceproject.com.utils.Concat;
import iia.com.surveillanceproject.com.utils.Data;
import iia.com.surveillanceproject.com.utils.EncryptFile;
import iia.com.surveillanceproject.com.webservice.FileWSAdapter;

public class Surveillance extends Activity {

    boolean filefound = false;
    String json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_surveillance);

        /**
         * Get User intent
         */

        User user = (User) getIntent().getSerializableExtra(User.SERIAL);

        /**
         * Encrypt token
         */

        byte[] secretKey = SecretKey.GenerateKc();
        IvParameterSpec iv = Iv.GenerateIv();

        String tokenEncrypted = Encrypt.encryptMessage(user.getToken(), secretKey, iv);

        String secretKeyEncrypted = SecretKey.encryptKc(secretKey);
        String messageEncrypted = Concat.ConcatEncryptedStrings(tokenEncrypted, secretKeyEncrypted, iv);


        /**
         * Get server directory files (Json encrypted)
         */

        try {
            FileWSAdapter.postToken(Surveillance.this, messageEncrypted, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (responseString != "") {
                        json = responseString;
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (responseString != "") {
                        json = responseString;
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * If we get files list
         */
        if (json != null) {
            /**
             * Decrypt json encrypted
             */
            IvParameterSpec ivExtract = Iv.extractIv(json);
            String secretkeyEncrypted = SecretKey.ExtractKcv2(json, ivExtract);
            String jsonEncrypted = Data.ExtractJson(json, ivExtract, secretkeyEncrypted);
            String secretKeyDecrypted = SecretKey.decryptKc(secretkeyEncrypted);
            String jsonDecrypted = Descrypt.decryptMessage(jsonEncrypted, secretKeyDecrypted, ivExtract);

            /**
             * Parse Json decrypted
             */

            ArrayList<Fichier> fichiers = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonDecrypted);
                fichiers = FileWSAdapter.jsonToFichiers(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            /**
             * List files in phone directory
             */
            String path = Environment.getExternalStorageDirectory().toString() + "/Pictures/";

            File f = new File(path);
            File file[] = f.listFiles();

            for (int i = 0; i < file.length; i++) {
                filefound = false;
                String fileName = file[i].getName();
                String fileLastModifier = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(
                        new Date(file[i].lastModified()));

                String pathfile = file[i].getPath();
                Fichier fichier = new Fichier();

                fichier.setName(fileName);
                fichier.setLastDateModifier(fileLastModifier);
                fichier.setPath(pathfile);

                for (Fichier fich : fichiers) {
                    if (fich.getName().equals(fichier.getName()) && fich.getLastDateModifier() != fichier.getLastDateModifier()) {
                        //encrypt file

                        /**
                         * Read file byte
                         */

                        File fichierALire = new File(fichier.getPath());
                        /**
                         * Encrypt file
                         */
                        String encryptedFile = EncryptFile.encryptFile(fichierALire);

                        /**
                         * Send Encryptedfile to server
                         */

                        try {
                            FileWSAdapter.post(encryptedFile, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        filefound = true;
                        break;
                    }


                    if (filefound = false) {
                        //encrypt file
                        /**
                         * Read file byte
                         */

                        File fichierALire = new File(fichier.getPath());
                        /**
                         * Encrypt file
                         */
                        String encryptedFile = EncryptFile.encryptFile(fichierALire);
                        /**
                         * Send Encryptedfile to server
                         */
                        try {
                            FileWSAdapter.post(encryptedFile, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
}