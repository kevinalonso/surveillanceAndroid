package iia.com.surveillanceproject.com.Asymetric;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Thom' on 03/05/2016.
 */
public class Iv {

    /**
     * Generate an init vector
     *
     * @return iv generate
     */
    public static IvParameterSpec GenerateIv() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    /**
     * Extract iv from message
     *
     * @param message message
     * @return iv extract
     */
    public static IvParameterSpec extractIv(String message) {

        final byte[] messageBytes = Base64.decode(message, Base64.NO_PADDING);

        // Get 16 first byte[] (= KcEncrypted)
        final byte[] iv = new byte[16];
        for (int i = 0; i < iv.length; i++) {
            iv[i] = messageBytes[i];
        }

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;

    }

}
