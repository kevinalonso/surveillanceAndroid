package iia.com.surveillanceproject.com.Asymetric;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 * Created by Thom' on 26/01/2016.
 */
public class Hash {

    public static String hashContent(String content) {

    final String hashed = Hashing.sha256()
            .hashString(content, Charsets.UTF_8)
            .toString();

        return hashed;
    }
}
