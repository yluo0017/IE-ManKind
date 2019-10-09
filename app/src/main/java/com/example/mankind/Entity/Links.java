package com.example.mankind.Entity;

/**
 * The type Links.
 */
public class Links {
    private String name;
    private String link;
    private String type;
    private String des;

    /**
     * Instantiates a new Links.
     *
     * @param name the name
     * @param link the link
     * @param type the type
     */
    public Links(String name, String link, String type, String des) {
        this.name = name;
        this.link = link;
        this.type = type;
        this.des = des;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
