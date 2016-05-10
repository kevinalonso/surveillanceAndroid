package iia.com.surveillanceproject.com.utils;

import android.util.Base64;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 01/05/2016.
 */
public class Concat {

    public static String ConcatEncryptedStrings(String json, String kc) {

        byte[] concat = new byte[Base64.decode(kc, Base64.NO_WRAP).length + Base64.decode(json, Base64.NO_WRAP).length];

        for (int i = 0; i < Base64.decode(kc, Base64.NO_WRAP).length; i++) {
            concat[i] = Base64.decode(kc, Base64.NO_WRAP)[i];
        }
        int j = 0;
        try {
            for (int i = Base64.decode(kc, Base64.NO_WRAP).length; i < concat.length; i++) {
                concat[i] = Base64.decode(json, Base64.NO_WRAP)[j];
                j++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        String message = Base64.encodeToString(concat, Base64.DEFAULT);

        return message;
    }

    public static String ConcatEncryptedStrings(String jsonEncrypted,String kcEncrypted,IvParameterSpec iv) {
        /**
         * concat lenght = iv.lenght + kc.lenght + message.lenght
         */
        byte[] concat = new byte[iv.getIV().length + Base64.decode(kcEncrypted, Base64.NO_WRAP).length + Base64.decode(jsonEncrypted, Base64.NO_WRAP).length];

        /**
         * concat = iv
         */
        for(int i=0; i < iv.getIV().length;i++) {
            concat[i] = iv.getIV()[i];
        }

        int j = 0;
        /**
         * concat = iv + kcEncrypted
         */
        for(int i = iv.getIV().length; i < iv.getIV().length + Base64.decode(kcEncrypted, Base64.NO_WRAP).length; i++ ) {
            concat[i] = Base64.decode(kcEncrypted, Base64.NO_WRAP)[j];
            j++;
        }


        j = 0;
        /**
         * concat = iv + kcEncrypted + jsonEncrypted
         */
        for(int i = iv.getIV().length + Base64.decode(kcEncrypted, Base64.NO_WRAP).length;
            i < iv.getIV().length + Base64.decode(kcEncrypted, Base64.NO_WRAP).length +
                    Base64.decode(jsonEncrypted, Base64.NO_WRAP).length;i++ ) {

            concat[i] =  Base64.decode(jsonEncrypted, Base64.NO_WRAP)[j];
            j++;

        }

        String message = Base64.encodeToString(concat, Base64.DEFAULT);

        return message;
    }
}
