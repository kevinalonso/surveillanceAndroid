package iia.com.surveillanceproject.com.Utils;

import javax.crypto.spec.IvParameterSpec;

import iia.com.surveillanceproject.com.Asymetric.Iv;
import iia.com.surveillanceproject.com.Asymetric.SecretKey;
import iia.com.surveillanceproject.com.Asymetric.Descrypt;
import iia.com.surveillanceproject.com.Asymetric.Encrypt;


/**
 * Created by Thom' on 01/05/2016.
 */
public class TestEncyptDecryptData {


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


        /**
         * Encrypt json
         */
        String jsonEncrypted = Encrypt.encryptMessage(json, kc, ivParameterSpec);

        /**
         * Encrypt Kc
         */
        String kcEncrypted = SecretKey.encryptKc(kc);

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


        /**
         * Extract json from message
         */


        String jsonEncryptedV2 = Data.ExtractJson(messageV2, ivParameterSpec, KcEncryptedFromMessageV2);


        /**
         * Decrypt Kc
         */

        String KcDecrypted2 = SecretKey.decryptKc(KcEncryptedFromMessageV2);

        /**
         * Decrypt message
         */


        String messageDecryptedV2 = Descrypt.decryptMessage(jsonEncryptedV2, KcDecrypted2, ivParameterSpecExtract);

        return messageDecryptedV2;
    }

}
