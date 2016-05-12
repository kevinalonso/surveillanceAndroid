package iia.com.surveillanceproject.com.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import javax.crypto.spec.IvParameterSpec;

import cz.msebera.android.httpclient.Header;
import iia.com.surveillanceproject.R;
import iia.com.surveillanceproject.com.Asymetric.Descrypt;
import iia.com.surveillanceproject.com.Asymetric.Encrypt;
import iia.com.surveillanceproject.com.Asymetric.Iv;
import iia.com.surveillanceproject.com.Asymetric.SecretKey;
import iia.com.surveillanceproject.com.Entity.User;
import iia.com.surveillanceproject.com.Utils.Concat;
import iia.com.surveillanceproject.com.Utils.Data;
import iia.com.surveillanceproject.com.Utils.TestEncyptDecryptData;
import iia.com.surveillanceproject.com.Asymetric.Hash;
import iia.com.surveillanceproject.com.Webservice.UserWSAdapter;

public class MainActivity extends AppCompatActivity {

    EditText edLogin;
    EditText edPassword;
    Button btCnx;
    Button btSurveillance;
    String login = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edLogin = (EditText) this.findViewById(R.id.login);
        edPassword = (EditText) this.findViewById(R.id.password);
        btCnx = (Button) this.findViewById(R.id.Cnx);
        btSurveillance = (Button) this.findViewById(R.id.btSurveillance);


        btCnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login = edLogin.getText().toString();
                password = edPassword.getText().toString();
                password = Hash.hashContent(password);

                /**
                 * Encrypt identifiants
                 */
                String jsondecrypted = null;
                String messageEncrypted = null;

                try {
                    /**
                     * Encrypt Decrypt data TEST
                     */
                    jsondecrypted = TestEncyptDecryptData.sendData(login, password);
                    Toast.makeText(MainActivity.this, jsondecrypted, Toast.LENGTH_SHORT).show();

                    /**
                     * Encrypt Json
                     */
                    String json = Data.createJson(login, password);
                    byte[] secretKey = SecretKey.GenerateKc();
                    IvParameterSpec iv = Iv.GenerateIv();
                    String jsonencrypted = Encrypt.encryptMessage(json, secretKey, iv);
                    String secretKeyEncrypted = SecretKey.encryptKc(secretKey);

                    messageEncrypted = Concat.ConcatEncryptedStrings(jsonencrypted, secretKeyEncrypted, iv);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                /**
                 * Send identifiants to server
                 */

                try {
                    UserWSAdapter.post(MainActivity.this, messageEncrypted, new TextHttpResponseHandler() {

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {

                            /**
                             * Decrypt response
                             */
                            /**
                             * Extract iv from response
                             */

                            IvParameterSpec ivParameterSpecExtract= null;

                            /**
                             * Extract SecretKey from response
                             */

                            String secretKeyEncrypted = SecretKey.ExtractKcv2(responseString, ivParameterSpecExtract);

                            /**
                             * Extract json from message
                             */


                            String jsonEncrypted = Data.ExtractJson(responseString, ivParameterSpecExtract, secretKeyEncrypted);

                            /**
                             * Decrypt Kc
                             */

                            String secretKeyDecrypted = SecretKey.decryptKc(secretKeyEncrypted);

                            /**
                             * Decrypt message
                             */

                            String json = Descrypt.decryptMessage(jsonEncrypted, secretKeyDecrypted, ivParameterSpecExtract);

                            /**
                             * Create User
                             */
                            User user = new User();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                user = UserWSAdapter.jsonToUser(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            /**
                             * If server response a token = User exist
                             */
                            if (user.getToken() != null) {

                                Intent i = new Intent(MainActivity.this, Surveillance.class);
                                i.putExtra(User.SERIAL, user);

                                startActivity(i);


                            } else {
                                Toast.makeText(MainActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        /**
         * Click on surveillance
         */
        btSurveillance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = null;
                Intent i = new Intent(MainActivity.this, Surveillance.class);
                i.putExtra(User.SERIAL, user);

                startActivity(i);
            }
        });



    }

}

