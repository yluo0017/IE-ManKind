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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Question2Activity extends AppCompatActivity {

    private String age;
    private String partnerGender;
    private String lastTime;
    private boolean question4;
    private boolean question5;
    private boolean question6;
    private boolean question7;
    private boolean question8;
    private boolean question9;
    private boolean question10;
    private boolean question11;
    private boolean question12;
    private boolean hasKid;
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
    private boolean flag11;
    private boolean flag12;
    private boolean flag13;
    private List<String> type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        flag5 = false;
        flag6 = false;
        flag7 = false;
        flag8 = false;
        flag9 = false;
        flag10 = false;
        flag11 = false;
        flag12 = false;
        flag13 = false;
        final RadioGroup q1 = (RadioGroup) findViewById(R.id.q1);
        final RadioGroup q2 = (RadioGroup) findViewById(R.id.q2);
        final RadioGroup q3 = (RadioGroup) findViewById(R.id.q3);
        final RadioGroup q4 = (RadioGroup) findViewById(R.id.q4);
        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);
        final RadioGroup q6 = (RadioGroup) findViewById(R.id.q6);
        final RadioGroup q7 = (RadioGroup) findViewById(R.id.q7);
        final RadioGroup q8 = (RadioGroup) findViewById(R.id.q8);
        final RadioGroup q9 = (RadioGroup) findViewById(R.id.q9);
        final RadioGroup q10 = (RadioGroup) findViewById(R.id.q110);
        final RadioGroup q11 = (RadioGroup) findViewById(R.id.q111);
        final RadioGroup q12 = (RadioGroup) findViewById(R.id.q112);
        final RadioGroup q13 = (RadioGroup) findViewById(R.id.q113);
        type = new ArrayList<>();
        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag1 = true;
                switch (checkedId) {
                    case R.id.q10:
                        age = "18-23";
                        break;
                    case R.id.q11:
                        age = "24-30";
                    case R.id.q12:
                        age = "31-45";
                    case R.id.q13:
                        age = "46+";
                        break;
                }
            }
        });
        q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag2 = true;
                switch (checkedId) {
                    case R.id.q20:
                        partnerGender = "Male";
                        break;
                    case R.id.q21:
                        partnerGender = "Female";
                    case R.id.q22:
                        partnerGender = "Others";
                        break;
                }
            }
        });
        q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag3 = true;
                switch (checkedId) {
                    case R.id.q30:
                        lastTime = "Last week";
                        break;
                    case R.id.q31:
                        lastTime = "Last month";
                    case R.id.q32:
                        lastTime = "Over a few months";
                    case R.id.q33:
                        lastTime = "Over a year";
                        break;
                }
            }
        });
        q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag4 = true;
                switch (checkedId) {
                    case R.id.q40:
                        question4 = true;
                        break;
                    case R.id.q41:
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
                    case R.id.q50:
                        question5 = true;
                        break;
                    case R.id.q51:
                        question5 = false;
                        break;
                }
            }
        });
        q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag6 = true;
                switch (checkedId) {
                    case R.id.q60:
                        question6 = true;
                        break;
                    case R.id.q61:
                        question6 = false;
                        break;
                }
            }
        });
        q7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag7 = true;
                switch (checkedId) {
                    case R.id.q70:
                        question7 = true;
                        break;
                    case R.id.q71:
                        question7 = false;
                        break;
                }
            }
        });
        q8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag8 = true;
                switch (checkedId) {
                    case R.id.q80:
                        question8 = true;
                        break;
                    case R.id.q81:
                        question8 = false;
                        break;
                }
            }
        });
        q9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag9 = true;
                switch (checkedId) {
                    case R.id.q90:
                        question9 = true;
                        break;
                    case R.id.q91:
                        question9 = false;
                        break;
                }
            }
        });
        q10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag10 = true;
                switch (checkedId) {
                    case R.id.q100:
                        question10 = true;
                        break;
                    case R.id.q101:
                        question10 = false;
                        break;
                }
            }
        });
        q11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag11 = true;
                switch (checkedId) {
                    case R.id.q1110:
                        question11 = true;
                        break;
                    case R.id.q1111:
                        question11 = false;
                        break;
                }
            }
        });
        q12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag12 = true;
                switch (checkedId) {
                    case R.id.q1120:
                        question12 = true;
                        break;
                    case R.id.q1121:
                        question12 = false;
                        break;
                }
            }
        });
        q13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag13 = true;
                switch (checkedId) {
                    case R.id.q130:
                        hasKid = true;
                        break;
                    case R.id.q131:
                        hasKid = false;
                        break;
                }
            }
        });

        findViewById(R.id.q2submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == false || flag2 == false || flag3 == false || flag4 == false || flag5 == false || flag6==false || flag7 == false || flag8 == false || flag9 == false || flag10 == false || flag11 ==false || flag12 == false || flag13 == false){
                    Toast.makeText(Question2Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                double physical = 0;
                if(question4)
                    physical += 1 ;
                if(question5)
                    physical += 1;
                if(question6)
                    physical += 1;
                physical /= 3;
                double financial = 0;
                if(question7)
                   financial += 1 ;
                if(question8)
                    financial += 1;
                if(question9)
                   financial += 1;
                financial /= 3;
                double emotional = 0;
                if(question10)
                    emotional += 1 ;
                if(question11)
                    emotional += 1;
                if(question12)
                    emotional += 1;
                emotional /= 3;
                if(physical > 0.5)
                    type.add("Physical");
                if(financial > 0.5)
                    type.add("financial");
                if(emotional > 0.5)
                    type.add("emotional");
                if(type.size() == 0){
                    if(physical > 0)
                        type.add("physical");
                    if(financial > 0)
                        type.add("financial");
                    if(emotional > 0)
                        type.add("emotional");
                }
                StringBuilder result = new StringBuilder("You are suffering from ");
                for (int i=0; i<type.size(); i++){
                    if(i!=0)
                        result.append(" and ");
                    result.append(type.get(i) + " violence");
                }
                result.append("!");
                result.append("\r\n Please sign up with your username and password so that we can display customized content for you");
                if (physical==0 && financial == 0 && emotional==0)
                    result = new StringBuilder("You may not suffer from any domestic violence, click exit if you want to leave");
                final AlertDialog alertDialog = new AlertDialog.Builder(Question2Activity.this).create();
                alertDialog.show();
                alertDialog.setContentView(R.layout.dialog);
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                TextView textView = (TextView) window.findViewById(R.id.display);
                textView.setText(result.toString());
                window.findViewById(R.id.contin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Question2Activity.this, RegisterActivity.class);
                        Question2Activity.this.startActivity(intent);
                    }
                });
                window.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Question2Activity.super.onDestroy();
                         System.exit(0);
                    }
                });
            }
        });
    }
}