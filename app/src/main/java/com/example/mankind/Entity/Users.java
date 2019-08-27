package com.example.mankind.Entity;

import java.io.Serializable;
import java.util.Collection;

public class Users implements Serializable {
    private String username;
    private int userid;
    private String password;
    private Collection<String> types;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getTypes() {
        return types;
    }

    public void setTypes(Collection<String> types) {
        this.types = types;
    }
}
