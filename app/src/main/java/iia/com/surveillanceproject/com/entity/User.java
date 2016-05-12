package iia.com.surveillanceproject.com.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 10/05/2016.
 */
public class User implements Serializable {

    private String login;
    private String password;
    private String token;
    public static final String SERIAL = "User";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
