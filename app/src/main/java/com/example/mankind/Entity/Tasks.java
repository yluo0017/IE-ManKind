package com.example.mankind.Entity;

import java.io.Serializable;

public class Tasks implements Serializable {
    private String type;
    private String des;

    public Tasks(String des, String type) {
        this.des = des;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tasks() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "type='" + type + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
