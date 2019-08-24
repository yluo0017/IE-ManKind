package com.example.mankind;


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

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private int question6;
    private int question7;
    private int question8;
    private int question9;
    private int question10;
    private int question11;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag1 = true;
                switch (checkedId) {
                    case R.id.q10:
                        question1 = 0;
                        break;
                    case R.id.q11:
                        question1 = 1;
                        break;
                    case R.id.q12:
                        question1 = 2;
                        break;
                    case R.id.q13:
                        question1 = 3;
                        break;
                    case R.id.q14:
                        question1 = 4;
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
                        question2 = 0;
                        break;
                    case R.id.q21:
                        question2 = 1;
                        break;
                    case R.id.q22:
                        question2 = 2;
                        break;
                    case R.id.q23:
                        question2 = 3;
                        break;
                    case R.id.q24:
                        question2 = 4;
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
                        question3 = 0;
                        break;
                    case R.id.q31:
                        question3 = 1;
                        break;
                    case R.id.q32:
                        question3 = 2;
                        break;
                    case R.id.q33:
                        question3 = 3;
                        break;
                    case R.id.q34:
                        question3 = 4;
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
                        question4 = 0;
                        break;
                    case R.id.q41:
                        question4 = 1;
                        break;
                    case R.id.q42:
                        question4 = 2;
                        break;
                    case R.id.q43:
                        question4 = 3;
                        break;
                    case R.id.q44:
                        question4 = 4;
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
                        question5 = 0;
                        break;
                    case R.id.q51:
                        question5 = 1;
                        break;
                    case R.id.q52:
                        question5 = 2;
                        break;
                    case R.id.q53:
                        question5 = 3;
                        break;
                    case R.id.q54:
                        question5 = 4;
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
                        question6 = 0;
                        break;
                    case R.id.q61:
                        question6 = 1;
                        break;
                    case R.id.q62:
                        question6 = 2;
                        break;
                    case R.id.q63:
                        question6 = 3;
                        break;
                    case R.id.q64:
                        question6 = 4;
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
                        question7 = 0;
                        break;
                    case R.id.q71:
                        question7 = 1;
                        break;
                    case R.id.q72:
                        question7 = 2;
                        break;
                    case R.id.q73:
                        question7 = 3;
                        break;
                    case R.id.q74:
                        question7 = 4;
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
                        question8 = 0;
                        break;
                    case R.id.q81:
                        question8 = 1;
                        break;
                    case R.id.q82:
                        question8 = 2;
                        break;
                    case R.id.q83:
                        question8 = 3;
                        break;
                    case R.id.q84:
                        question8 = 4;
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
                        question9 = 0;
                        break;
                    case R.id.q91:
                        question9 = 1;
                        break;
                    case R.id.q92:
                        question9 = 2;
                        break;
                    case R.id.q93:
                        question9 = 3;
                        break;
                    case R.id.q94:
                        question9 = 4;
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
                        question10 = 0;
                        break;
                    case R.id.q101:
                        question10 = 1;
                        break;
                    case R.id.q102:
                        question10 = 2;
                        break;
                    case R.id.q103:
                        question10 = 3;
                        break;
                    case R.id.q104:
                        question10 = 4;
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
                        question11 = 0;
                        break;
                    case R.id.q1111:
                        question11 = 1;
                        break;
                    case R.id.q1112:
                        question11 = 2;
                        break;
                    case R.id.q1113:
                        question11 = 3;
                        break;
                    case R.id.q1114:
                        question11 = 4;
                        break;
                }
            }
        });
        findViewById(R.id.q1submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == false || flag2 == false || flag3 == false || flag4 == false || flag5 == false || flag6==false || flag7 == false || flag8 == false || flag9 == false || flag10 == false || flag11 ==false){
                    Toast.makeText(MainActivity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                String display = "";
                int result = (int)((question1 + question2 + question3 + question4 + question5 + question6 + question7 + question8 + question9 + question10 + question11)*100/44);
                if(result<10)
                    display = "If you do not feel loved or happy, it is your opinion. Do not be afraid to tell your partner and fix it at the earliest.";
                else if(result < 30)
                    display = "There are signs that your partner might be abusing you. Stay strong and follow the rest of the content to end this at the earliest";
                else if(result < 70)
                    display = "There are strong signals that your partner is abusing you. You don’t need to be scared. You are not alone. Follow the rest of the content to find your voice to fight against this injustice.";
                else
                    display = "It does not look like your safe right now, you are at a serious threat. Call 000 if you are in immediate danger or call the helplines in the sources section to talk to someone. Follow the content of the app and get the will to put an end to this abuse immediately";
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.show();
                alertDialog.setContentView(R.layout.dialog);
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                TextView textView = (TextView) window.findViewById(R.id.display);
                textView.setText(display);
                window.findViewById(R.id.contin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, Question2Activity.class);
                        MainActivity.this.startActivity(intent);
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
