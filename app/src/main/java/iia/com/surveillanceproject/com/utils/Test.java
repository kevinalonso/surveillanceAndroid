package iia.com.surveillanceproject.com.utils;

import javax.crypto.spec.IvParameterSpec;

import iia.com.surveillanceproject.com.asymetric.Iv;
import iia.com.surveillanceproject.com.asymetric.SecretKey;
import iia.com.surveillanceproject.com.asymetric.Descrypt;
import iia.com.surveillanceproject.com.asymetric.Encrypt;


/**
 * Created by Thom' on 01/05/2016.
 */
public class Test {


    public static String sendData(String login, String password) throws Exception {


        String json = Data.createJson(login, password);


        /**
         * Create Kc
         */

        byte kc[] = SecretKey.GenerateKc();

        /**
         * Create iv
         */


        IvParameterSpec ivParameterSpec = Iv.GenerateIv();
       // byte[] ivbyte = ivParameterSpec.getIV();


        /**
         * Encrypt json
         */
        String jsonEncrypted = Encrypt.encryptMessage(json, kc, ivParameterSpec);

        /**
         * Encrypt Kc
         */
        String kcEncrypted = SecretKey.encryptKc(kc);

       // byte[] kcEncryptedByte = Base64.decode(kcEncrypted, Base64.DEFAULT);
        /**
         * Concat JsonEncrypted + KcEncrypted
         */


        //String message = Concat.ConcatEncryptedStrings(jsonEncrypted, kcEncrypted);

        /**
         * Concat iv + kcEncrypted + jsonEncrypted
         */

        String messageV2 = Concat.ConcatEncryptedStrings(jsonEncrypted, kcEncrypted, ivParameterSpec);

        /**
         * Extract iv from message
         */


        IvParameterSpec ivParameterSpecExtract = Iv.extractIv(messageV2);
        byte[] ivbyte2 = ivParameterSpecExtract.getIV();

        /**
         * Extract SecretKey from message V2
         */

        String KcEncryptedFromMessageV2 = SecretKey.ExtractKcv2(messageV2, ivParameterSpecExtract);
       // byte[] KcEncryptedFromMessageV2Byte = Base64.decode(KcEncryptedFromMessageV2, Base64.DEFAULT);

        /**
         * Extract SecretKey From message
         */

       // String KcEncryptedFromMessage = SecretKey.ExtractKc(message);

        /**
         * Extract json from message
         */


        String jsonEncryptedV2 = Data.ExtractJson(messageV2, ivParameterSpec, KcEncryptedFromMessageV2);


        /**
         * Decrypt Kc
         */

       // String KcDecrypted = SecretKey.decryptKc(KcEncryptedFromMessage);
       // byte[] KcDecryptedByte = Base64.decode(KcDecrypted, Base64.DEFAULT);
        String KcDecrypted2 = SecretKey.decryptKc(KcEncryptedFromMessageV2);
        //byte[] KcDecryptedByte2 = Base64.decode(KcDecrypted2, Base64.DEFAULT);

        /**
         * Decrypt message
         */


       // String messageDecrypted = Descrypt.decryptMessage(jsonEncrypted, KcDecrypted, ivParameterSpec);

        String messageDecryptedV2 = Descrypt.decryptMessage(jsonEncryptedV2, KcDecrypted2, ivParameterSpecExtract);


        //return message;
        return messageDecryptedV2;
    }

}
