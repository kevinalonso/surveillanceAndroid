package iia.com.surveillanceproject.com.Asymetric;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Created by Thom' on 26/01/2016.
 */
public class Hash {

    /**
     * Hash data SHA256
     *
     * @param content data to hash
     * @return data hashed SHA256
     */
    public static String hashContent(String content) {

        final String hashed = Hashing.sha256()
                .hashString(content, Charsets.UTF_8)
                .toString();

        return hashed;
    }
}
