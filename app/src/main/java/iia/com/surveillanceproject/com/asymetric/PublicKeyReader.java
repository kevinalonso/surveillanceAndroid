package iia.com.surveillanceproject.com.Asymetric;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Thom' on 20/01/2016.
 */
public class PublicKeyReader {

    /**
     * Read public key
     * @param filename public key to read
     * @return public key
     * @throws Exception
     */
    public static PublicKey get(String filename) throws Exception {
        File public_key = new File(filename);
        FileInputStream fis = new FileInputStream(public_key);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) public_key.length()];
        dis.readFully(keyBytes);
        dis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);

    }
}
