package iia.com.surveillanceproject.com.Utils;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 03/05/2016.
 */
public class Data {

    public static String createJson(String login,String password) throws JSONException, UnsupportedEncodingException {

        String json = "";
        JSONObject jsonObject = new JSONObject();
        // build json
        jsonObject.accumulate("login", login);
        jsonObject.accumulate("password", password);
        json = jsonObject.toString();
        byte[] jsonbyte = json.getBytes("UTF-8");

        return json;
    }

    public static String createJson(String login,String password,String token) throws JSONException, UnsupportedEncodingException {

        String json = "";
        JSONObject jsonObject = new JSONObject();
        // build json
        jsonObject.accumulate("login", login);
        jsonObject.accumulate("password", password);
        jsonObject.accumulate("token", token);
        json = jsonObject.toString();
        byte[] jsonbyte = json.getBytes("UTF-8");

        return json;
    }


    public static String ExtractJson(String message,IvParameterSpec ivParameterSpec ,String secretKeyEncrypted) {

        final byte[] messageBytes = Base64.decode(message, Base64.DEFAULT);

        byte[] json = new byte[Base64.decode(message,Base64.DEFAULT).length -
                ivParameterSpec.getIV().length - Base64.decode(secretKeyEncrypted,Base64.DEFAULT).length];

        int j = 0;
        for(int i = ivParameterSpec.getIV().length + Base64.decode(secretKeyEncrypted,Base64.DEFAULT).length;
            i < Base64.decode(message,Base64.DEFAULT).length;i++) {
            json[j] = messageBytes[i];

            j++;
        }

        return Base64.encodeToString(json,Base64.DEFAULT);

    }
}
