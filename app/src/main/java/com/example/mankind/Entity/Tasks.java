package com.example.mankind.Entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Tasks.
 */
public class Tasks implements Serializable {
    private int id;
    private String type;
    private String des;
    private int stage;
    private boolean isChecked;

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
        isChecked = false;
    }

    public int getStage() {
        return stage;
    }

    //set stage for each task
    public void setStage(){
        if(id < 3)
            stage = 1;
        if(id >= 3 && id < 6)
            stage = 2;
        if(id >= 6 && id < 9)
            stage = 3;
        if(id >= 9)
            stage = 4;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", des='" + des + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tasks tasks = (Tasks) o;
        return id == tasks.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
