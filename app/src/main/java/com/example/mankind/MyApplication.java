package com.example.mankind;

import android.app.Application;

public class MyApplication extends Application {
    private String type;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
