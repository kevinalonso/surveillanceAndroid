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

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class MainActivity extends AppCompatActivity {

    EditText edLogin;
    EditText edPassword;
    String login = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edLogin = (EditText) this.findViewById(R.id.login);
        edPassword = (EditText) this.findViewById(R.id.password);
        Button btCnx = (Button) this.findViewById(R.id.Cnx);

        btCnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = edLogin.getText().toString();
                password = edPassword.getText().toString();
                new HttpAsyncTask().execute("http://192.168.100.25/index.php");

            }
        });


    }



    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            try {
                return Login.sendData(urls[0], login, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }


}
