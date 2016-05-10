package iia.com.surveillanceproject.com.asymetric;

import android.util.Base64;

import java.security.PrivateKey;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 26/01/2016.
 */
public class Descrypt {


    public static String decryptMessage(String input, String secretKey,IvParameterSpec iv) {

        try {

            byte[] kc = Base64.decode(secretKey,Base64.DEFAULT);
            final byte[] message = Base64.decode(input, Base64.DEFAULT);
            final byte[] cipherText = CipherData.cipherDecryptAES(message, kc,iv);

            return new String(cipherText,"UTF-8");

        } catch (Exception e) {

            System.out.println(e);
        }
        return null;
    }

}
