package iia.com.surveillanceproject.com.asymetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Thom' on 20/01/2016.
 */
public class CipherData {
    public static final byte[] cipherEncrypt(final byte[] input,final PublicKey publickey) {
        byte[] cipherText = null;
        try {
            final SecureRandom random = new SecureRandom();
            //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
           Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publickey,random);

            //Encrypt
            cipherText = cipher.doFinal(input);
            return cipherText;

        }catch(Exception e) {

        }
        return cipherText;
    }

    public static final byte[] cipherEncryptAES(final byte[] input,byte[] kc,IvParameterSpec iv) {
        byte[] cipherText = null;
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey aesKey = new SecretKeySpec(kc, "AES");
            cipher.init(Cipher.ENCRYPT_MODE,aesKey,iv);
            //Encrypt
            cipherText = cipher.doFinal(input);
            return cipherText;

        }catch(Exception e) {

            e.printStackTrace();
        }
        return cipherText;
    }

    public static  final byte[] cipherDescrypt(final byte[] input,final PrivateKey privateKey) {
        byte[] cipherText = null;
        try
        {
            final SecureRandom random = new SecureRandom();

            //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey, random);

            // Decrypt !
             cipherText = cipher.doFinal(input);

            return cipherText;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static final byte[] cipherDecryptAES(final byte[] input,byte[] kc,IvParameterSpec iv ) {
        byte[] cipherText = null;
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey aesKey = new SecretKeySpec(kc, "AES");
            cipher.init(Cipher.DECRYPT_MODE,aesKey,iv);
            //Encrypt
            cipherText = cipher.doFinal(input);
            return cipherText;

        }catch(Exception e) {

            e.printStackTrace();
        }
        return cipherText;
    }
}
