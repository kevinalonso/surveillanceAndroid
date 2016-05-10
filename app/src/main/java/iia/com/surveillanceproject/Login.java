//package iia.com.surveillanceproject;
//
//import android.util.Base64;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.SecureRandom;
//
//import cz.msebera.android.httpclient.HttpResponse;
//import cz.msebera.android.httpclient.client.HttpClient;
//import cz.msebera.android.httpclient.client.methods.HttpPost;
//import cz.msebera.android.httpclient.entity.StringEntity;
//import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
//import iia.com.surveillanceproject.com.asymetric.Descrypt;
//import iia.com.surveillanceproject.com.asymetric.Encrypt;
//import iia.com.surveillanceproject.com.symetric.Encryptor;
//
///**
// * Created by Thom' on 20/01/2016.
// */
//public class Login {
//
//    public static String sendData(String url, String login, String password) throws IOException, JSONException {
//
//        InputStream inputStream = null;
//        String result = "";
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost httpPost = new HttpPost(url);
//        String json = "";
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            // build json
//            jsonObject.accumulate("login", login);
//            jsonObject.accumulate("password", password);
//            json = jsonObject.toString();
//
//            //create kc
//            SecureRandom random = new SecureRandom();
//            byte kc[] = new byte[16];
//            random.nextBytes(kc);
//            //encrypt json contain
//            String newjson = Encrypt.encryptMessage(json, kc,null);
//            Base64.decode(newjson, Base64.NO_WRAP);
//            // encrypt kc
//            String kcEncrypted = Encrypt.encryptKc(kc);
//           // byte[] kcc = Base64.decode(kcEncrypted, Base64.DEFAULT);
//            byte [] kcdecrypted = Descrypt.descrypt(kcEncrypted);
//            //concat kc et newjson
//            String kcJson = kcEncrypted.concat(newjson);
//            String test = kcEncrypted + newjson;
//            Base64.decode(kcEncrypted, Base64.NO_WRAP);
//            Base64.decode(newjson, Base64.NO_WRAP);
//            //Base64.decode(kcEncrypted + newjson,Base64.DEFAULT);
//            int kcencryptedlenght = Base64.decode(kcEncrypted, Base64.NO_WRAP).length;
//            int newjsonmenght =  Base64.decode(newjson, Base64.NO_WRAP).length;
//            byte[] test2 = new byte[Base64.decode(kcEncrypted, Base64.NO_WRAP).length + Base64.decode(newjson, Base64.NO_WRAP).length];
//
//            for (int i = 0; i < Base64.decode(kcEncrypted, Base64.NO_WRAP).length; i++) {
//                test2[i] = Base64.decode(kcEncrypted, Base64.NO_WRAP)[i];
//            }
//            int j = 0;
//            try {
//                for (int i = Base64.decode(kcEncrypted, Base64.NO_WRAP).length; i < test2.length; i++) {
//                    test2[i] = Base64.decode(newjson, Base64.NO_WRAP)[j];
//                    j++;
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//
//
//            String encore = Base64.encodeToString(test2, Base64.DEFAULT);
//            byte[] test2decode = Base64.decode(Base64.encodeToString(test2, Base64.DEFAULT), Base64.DEFAULT);
//            // to do ajouter l'iv + envoyer iv + kc + message et ne pas oublier de mettre base64 avant d'envoyer
//            //String resultDescrypted = Descrypt.descrypt(newjson);
//            byte[] KcDecrypted = Descrypt.descrypt(encore);
//            String resultDecrypted = Descrypt.decryptMessage(encore, KcDecrypted,null);
//            // send json to server
//            // StringEntity se = new StringEntity(newjson,kcEncrypted);
//            //httpPost.setEntity(se);
//
//            //get response form server
//            HttpResponse response = client.execute(httpPost);
//            inputStream = response.getEntity().getContent();
//
//            if (inputStream != null) {
//                // get string reponse form server
//                result = convertInputStreamToString(inputStream);
//                // descrypt result
//                // String resultDescrypted = Descrypt.descrypt(newjson);
//                //  System.out.println(resultDescrypted);
//
//            } else {
//                result = "InputStream not convert to string";
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//
//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while ((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }
//}
