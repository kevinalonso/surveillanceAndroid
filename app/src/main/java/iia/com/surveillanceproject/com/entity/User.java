package iia.com.surveillanceproject.com.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 10/05/2016.
 */
public class User implements Serializable {

    /**
     * User login
     */
    private String login;
    /**
     * User password
     */
    private String password;
    /**
     * User token
     */
    private String token;
    /**
     * User SERIAL
     */
    public static final String SERIAL = "User";

    /**
     * Get user login
     *
     * @return user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set user login
     *
     * @param login user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get user password
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user token
     *
     * @return user token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set user token
     *
     * @param token user token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
