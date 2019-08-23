package com.example.mankind;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Question2Activity extends AppCompatActivity {

    private boolean question1;
    private boolean question2;
    private boolean question3;
    private boolean question4;
    private boolean question5;
    private boolean question6;
    private boolean question7;
    private boolean question8;
    private boolean question9;
    private boolean question10;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private boolean flag5;
    private boolean flag6;
    private boolean flag7;
    private boolean flag8;
    private boolean flag9;
    private boolean flag10;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        flag5 = false;
        final RadioGroup q1 = (RadioGroup) findViewById(R.id.q1);
        final RadioGroup q2 = (RadioGroup) findViewById(R.id.q2);
        final RadioGroup q3 = (RadioGroup) findViewById(R.id.q3);
        final RadioGroup q4 = (RadioGroup) findViewById(R.id.q4);
        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);
        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag1 = true;
                switch (checkedId) {
                    case R.id.q1t:
                        question1 = true;
                        break;
                    case R.id.q1f:
                        question1 = false;
                        break;
                }
            }
        });
        q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag2 = true;
                switch (checkedId) {
                    case R.id.q2t:
                        question2 = true;
                        break;
                    case R.id.q2f:
                        question2 = false;
                        break;
                }
            }
        });
        q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag3 = true;
                switch (checkedId) {
                    case R.id.q3t:
                        question3 = true;
                        break;
                    case R.id.q3f:
                        question3 = false;
                        break;
                }
            }
        });
        q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag4 = true;
                switch (checkedId) {
                    case R.id.q4t:
                        question4 = true;
                        break;
                    case R.id.q4f:
                        question4 = false;
                        break;
                }
            }
        });
        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag5 = true;
                switch (checkedId) {
                    case R.id.q5t:
                        question5 = true;
                        break;
                    case R.id.q5f:
                        question5 = false;
                        break;
                }
            }
        });
        findViewById(R.id.q1submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == false || flag2 == false || flag3 == false || flag4 == false || flag5 == false){
                    Toast.makeText(Question2Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                final AlertDialog alertDialog = new AlertDialog.Builder(Question2Activity.this).create();
                alertDialog.show();
                alertDialog.setContentView(R.layout.dialog);
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                window.findViewById(R.id.contin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Question2Activity.this, RegisterActivity.class);
                        Question2Activity.this.startActivity(intent);
                    }
                });
//                window.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                         MainActivity.super.onDestroy();
//                         System.exit(0);
//                    }
//                });
            }
        });
    }
}