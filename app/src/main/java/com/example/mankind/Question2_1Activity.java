package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question2_1Activity extends AppCompatActivity {

    private String age;
    private String partnerGender;
    private String lastTime;
    private boolean question4;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question2_1);
        flag1 = false;
        flag2 = false;
        flag3 = false;
        flag4 = false;
        final RadioGroup q1 = (RadioGroup) findViewById(R.id.q1);
        final RadioGroup q2 = (RadioGroup) findViewById(R.id.q2);
        final RadioGroup q3 = (RadioGroup) findViewById(R.id.q3);
        final RadioGroup q4 = (RadioGroup) findViewById(R.id.q4);
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
        Button btnNext = (Button) findViewById(R.id.next1);
        btnNext.getBackground().setAlpha(100);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == false || flag2 == false || flag3 == false || flag4 == false){
                    Toast.makeText(Question2_1Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("age", age);
                intent.putExtra("partnerGender", partnerGender);
                intent.putExtra("lastTime", lastTime);
                if(question4)
                    intent.putExtra("physical", 1);
                else
                    intent.putExtra("physical", 0);
                intent.setClass(Question2_1Activity.this, Question2_2Activity.class);
                startActivity(intent);

            }
        });
    }
}
