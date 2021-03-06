package iia.com.surveillanceproject.com.Webservice;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;
import iia.com.surveillanceproject.com.Entity.Fichier;

/**
 * Created by Thom' on 09/05/2016.
 */
public class FileWSAdapter {

    private static final String URL = "https://192.168.100.152/surveillancev3/checkfile.php";
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);


    /**
     * Post file to server
     *
     * @param filePath        file
     * @param responseHandler response
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public static void post(String filePath, AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException, FileNotFoundException {

        File file = new File(filePath);
        InputStream myInputStream = new FileInputStream(file);
        RequestParams params = new RequestParams();
        params.put("file", myInputStream, file.getName());
        client.post(URL, params, responseHandler);
    }


    /**
     * Post token to server
     *
     * @param context         context
     * @param json            json
     * @param responseHandler response
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    public static void postToken(Context context, String json, AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException {

        StringEntity entity = new StringEntity(json);
        client.post(context, URL, entity, "application/json", responseHandler);
    }

    public static ArrayList<Fichier> jsonToFichiers(JSONObject json) throws JSONException {

        ArrayList<Fichier> fichiers = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            Fichier fichier = new Fichier();

            fichier.setName(json.getString("name"));
            fichier.setLastDateModifier(json.getString("lastModifier"));

            fichiers.add(fichier);

        }


        return fichiers;

    }
}
