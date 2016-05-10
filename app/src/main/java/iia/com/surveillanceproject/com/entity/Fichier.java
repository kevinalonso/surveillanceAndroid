package iia.com.surveillanceproject.com.entity;

/**
 * Created by Thom' on 09/05/2016.
 */
public class Fichier {

    private String name;
    private String lastDateModifier;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastDateModifier() {
        return lastDateModifier;
    }

    public void setLastDateModifier(String lastDateModifier) {
        this.lastDateModifier = lastDateModifier;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
