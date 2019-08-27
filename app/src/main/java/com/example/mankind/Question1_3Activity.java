package com.example.mankind;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question1_3Activity extends AppCompatActivity {
    private boolean flag9;
    private boolean flag10;
    private boolean flag11;
    private int result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question1_3);
        flag9 = false;
        flag10 = false;
        flag11 = false;
        result = getIntent().getIntExtra("result", 0);
        final RadioGroup q9 = (RadioGroup) findViewById(R.id.q9);
        final RadioGroup q10 = (RadioGroup) findViewById(R.id.q110);
        final RadioGroup q11 = (RadioGroup) findViewById(R.id.q111);
        q9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag9 = true;
                switch (checkedId) {
                    case R.id.q90:
                        break;
                    case R.id.q91:
                        result += 1;
                        break;
                    case R.id.q92:
                        result += 2;
                        break;
                    case R.id.q93:
                        result += 3;
                        break;
                    case R.id.q94:
                        result += 4;
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
                        break;
                    case R.id.q101:
                        result += 1;
                        break;
                    case R.id.q102:
                        result += 2;
                        break;
                    case R.id.q103:
                        result += 3;
                        break;
                    case R.id.q104:
                        result += 4;
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
                        break;
                    case R.id.q1111:
                        result += 1;
                        break;
                    case R.id.q1112:
                        result += 2;
                        break;
                    case R.id.q1113:
                        result += 3;
                        break;
                    case R.id.q1114:
                        result += 4;
                        break;
                }
            }
        });
        Button submit = findViewById(R.id.q1submit);
        submit.getBackground().setAlpha(100);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag9 == false || flag10 == false || flag11 ==false){
                    Toast.makeText(Question1_3Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Question1_3Activity.this, Result1Activity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }

}
