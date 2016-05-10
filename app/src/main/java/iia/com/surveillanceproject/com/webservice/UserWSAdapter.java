package iia.com.surveillanceproject.com.webservice;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;
import iia.com.surveillanceproject.com.entity.User;

/**
 * Created by Thom' on 01/05/2016.
 */
public class UserWSAdapter {

    private static final String URL = "http://ip/adresse";
    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void post(Context context,String json ,AsyncHttpResponseHandler responseHandler)
            throws JSONException, UnsupportedEncodingException {

        StringEntity entity = new StringEntity(json);
        client.post(context,URL,entity,"application/json",responseHandler);
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
