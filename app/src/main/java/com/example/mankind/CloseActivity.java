package com.example.mankind;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CloseActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
        System.exit(0);
    }
}