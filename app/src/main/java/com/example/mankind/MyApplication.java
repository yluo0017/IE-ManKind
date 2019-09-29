package com.example.mankind;

import android.app.Application;

/**
 * The type My application.
 */
public class MyApplication extends Application {
    private String type;
    @Override
    public void onCreate() {
        super.onCreate();
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
}
