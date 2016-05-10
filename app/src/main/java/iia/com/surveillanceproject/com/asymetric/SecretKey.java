package iia.com.surveillanceproject.com.asymetric;

import android.util.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import iia.com.surveillanceproject.com.asymetric.PrivateKeyReader;
import iia.com.surveillanceproject.com.asymetric.PublicKeyReader;

/**
 * Created by Thom' on 01/05/2016.
 */
public class SecretKey {


    public static String encryptKc(byte[] kc) {

        final String PATH_PUBLIC_KEY = "./data/data/iia.com.surveillanceproject/public_test.der";

        PublicKey pubKey = null;
        try {
            pubKey = PublicKeyReader.get(PATH_PUBLIC_KEY);
        } catch (Exception e) {
            System.out.println(e);
        }

        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            cipherText = cipher.doFinal(kc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(cipherText, Base64.NO_WRAP);
    }

    public static String decryptKc(String kc) {

        final String PATH_PRIVATE_KEY = "./data/data/iia.com.surveillanceproject/private_test.der";
        PrivateKey privKey = null;
        try {
            privKey = PrivateKeyReader.get(PATH_PRIVATE_KEY);
        } catch (Exception e) {
            System.out.println(e);
        }
        byte[] dectyptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            dectyptedText = cipher.doFinal(Base64.decode(kc, Base64.DEFAULT));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Base64.encodeToString(dectyptedText, Base64.DEFAULT);
    }

    public static String ExtractKc(String message) {

        final byte[] messageBytes = Base64.decode(message, Base64.DEFAULT);
        final byte[] kc = new byte[16];
        for (int i = 0; i < kc.length; i++) {
            kc[i] = messageBytes[i];
        }

        return Base64.encodeToString(kc, Base64.DEFAULT);

    }

    public static String ExtractKcv2(String message,IvParameterSpec ivParameterSpec) {

        // Message Base64 to byte[]
        final byte[] messageBytes = Base64.decode(message,Base64.DEFAULT);
        // Get 16 first byte[] (= KcEncrypted)
        int j = 0;
        final byte[] kc = new byte[256];// modif ici 256 a la place de 16
        for (int i = ivParameterSpec.getIV().length; i < kc.length + ivParameterSpec.getIV().length; i++) {
            kc[j] = messageBytes[i];
            j++;
        }

        // return KcEncrypted base64
        return Base64.encodeToString(kc, Base64.DEFAULT);

    }

    public static byte[] GenerateKc() {
        SecureRandom random = new SecureRandom();
        byte kc[] = new byte[16];
        random.nextBytes(kc);

        return kc;
    }
}
