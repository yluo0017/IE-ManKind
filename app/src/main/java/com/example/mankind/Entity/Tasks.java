package com.example.mankind.Entity;

import java.io.Serializable;

/**
 * The type Tasks.
 */
public class Tasks implements Serializable {
    private int id;
    private String type;
    private String des;

    /**
     * Instantiates a new Tasks.
     *
     * @param des  the des
     * @param type the type
     */
    public Tasks(int id,String des, String type) {
        this.id = id;
        this.des = des;
        this.type = type;
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

    /**
     * Instantiates a new Tasks.
     */
    public Tasks() {
    }

    /**
     * Gets des.
     *
     * @return the des
     */
    public String getDes() {
        return des;
    }

    /**
     * Sets des.
     *
     * @param des the des
     */
    public void setDes(String des) {
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
