package iia.com.surveillanceproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edLogin = (EditText) this.findViewById(R.id.login);
        EditText edPassword = (EditText) this.findViewById(R.id.password);
        Button btCnx = (Button) this.findViewById(R.id.Cnx);

        btCnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public static String sendData(String url, EditText login, EditText password) throws IOException {
        InputStream inputStream = null;
        String result = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        String json ="";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.accumulate("login",login.getText());
            jsonObject.accumulate("password",password.getText());
            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            HttpResponse response = client.execute(httpPost);
            inputStream = response.getEntity().getContent();

            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "InputStream not convert to string";
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

          //  return sendData(urls[0],"toto","mypassword");


        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
