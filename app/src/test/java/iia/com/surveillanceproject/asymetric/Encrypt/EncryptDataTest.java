//package iia.com.surveillanceproject.asymetric.Encrypt;
//
//import org.json.JSONException;
//import org.junit.TestEncyptDecryptData;
//
//import java.io.UnsupportedEncodingException;
//
//import javax.crypto.spec.IvParameterSpec;
//
//import iia.com.surveillanceproject.com.Asymetric.Descrypt;
//import iia.com.surveillanceproject.com.Asymetric.Encrypt;
//import iia.com.surveillanceproject.com.Asymetric.Iv;
//import iia.com.surveillanceproject.com.Asymetric.SecretKey;
//import iia.com.surveillanceproject.com.Utils.Concat;
//import iia.com.surveillanceproject.com.Utils.Data;
//
//import static org.junit.Assert.*;
//
//
//public class EncryptDataTest {
//
//    private static final String LOGIN = "login";
//    private static final String PASSWORD ="password";
//
//
//    @TestEncyptDecryptData
//    public void EncryptDecryptDatas() throws UnsupportedEncodingException, JSONException {
//
//        String json = Data.createJson(LOGIN, PASSWORD);
//
//        /**
//         * Create Kc
//         */
//
//        byte kc[] = SecretKey.GenerateKc();
//
//        /**
//         * Create iv
//         */
//
//        IvParameterSpec ivParameterSpec = Iv.GenerateIv();
//
//        /**
//         * Encrypt json
//         */
//        String jsonEncrypted = Encrypt.encryptMessage(json, kc, ivParameterSpec);
//
//        /**
//         * Encrypt Kc
//         */
//        String kcEncrypted = SecretKey.encryptKc(kc);
//
//        /**
//         * Concat iv + kcEncrypted + jsonEncrypted
//         */
//
//        String messageV2 = Concat.ConcatEncryptedStrings(jsonEncrypted, kcEncrypted, ivParameterSpec);
//
//        /**
//         * Extract iv from message
//         */
//
//
//        IvParameterSpec ivParameterSpecExtract = Iv.extractIv(messageV2);
//
//        /**
//         * Extract SecretKey from message V2
//         */
//
//        String KcEncryptedFromMessageV2 = SecretKey.ExtractKcv2(messageV2, ivParameterSpecExtract);
//
//        /**
//         * Extract json from message
//         */
//
//
//        String jsonEncryptedV2 = Data.ExtractJson(messageV2, ivParameterSpec, KcEncryptedFromMessageV2);
//
//
//        /**
//         * Decrypt Kc
//         */
//
//
//        String KcDecrypted2 = SecretKey.decryptKc(KcEncryptedFromMessageV2);
//
//
//        /**
//         * Decrypt message
//         */
//
//
//        String messageDecryptedV2 = Descrypt.decryptMessage(jsonEncryptedV2, KcDecrypted2, ivParameterSpecExtract);
//
//        assertEquals(json,messageDecryptedV2);
//
//    }
//
//
//    @org.junit.TestEncyptDecryptData
//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//    }
//}