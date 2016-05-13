package iia.com.surveillanceproject.com.Entity;

/**
 * Created by Thom' on 09/05/2016.
 */
public class Fichier {

    /**
     * file name
     */
    private String name;
    /**
     * file last modifier date
     */
    private String lastDateModifier;
    /**
     * file path
     */
    private String path;

    /**
     * get file name
     *
     * @return file name
     */
    public String getName() {
        return name;
    }

    /**
     * set file name
     *
     * @param name file name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get file last modifier date
     *
     * @return last modifier date
     */
    public String getLastDateModifier() {
        return lastDateModifier;
    }

    /**
     * Set file last modified
     *
     * @param lastDateModifier last modifier date
     */
    public void setLastDateModifier(String lastDateModifier) {
        this.lastDateModifier = lastDateModifier;
    }

    /**
     * Get file path
     *
     * @return file path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set file path
     *
     * @param path file path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
