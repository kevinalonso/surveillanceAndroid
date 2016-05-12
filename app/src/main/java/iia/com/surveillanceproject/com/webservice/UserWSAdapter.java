package iia.com.surveillanceproject.com.Webservice;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.entity.StringEntity;
import iia.com.surveillanceproject.com.Entity.User;

/**
 * Created by Thom' on 01/05/2016.
 */
public class UserWSAdapter {

    private static final String URL = "http://192.168.100.152/surveillance/login.php";
    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void post(Context context,String json ,AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException {

        String data = URLEncoder.encode("message", "UTF-8")+"=" + URLEncoder.encode(json, "UTF-8");
        StringEntity entity = new StringEntity(data);
        client.post(context,URL,entity,"application/x-www-form-urlencoded",responseHandler);
    }

        public static User jsonToUser(JSONObject json) throws JSONException {

        User user = new User();
        for (int i = 0; i < json.length(); i++) {

            user.setLogin(json.getString("login"));
            user.setToken(json.getString("token"));
            user.setPassword(json.getString("password"));
        }

        return user;
    }
}
