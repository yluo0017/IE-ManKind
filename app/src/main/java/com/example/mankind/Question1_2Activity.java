package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class Question1_2Activity extends AppCompatActivity {
    private boolean flag5;
    private boolean flag6;
    private boolean flag7;
    private boolean flag8;
    private int result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragemnt_question1_2);
        flag5 = false;
        flag6 = false;
        flag7 = false;
        flag8 = false;
        result = getIntent().getIntExtra("result", 0);
        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);
        final RadioGroup q6 = (RadioGroup) findViewById(R.id.q6);
        final RadioGroup q7 = (RadioGroup) findViewById(R.id.q7);
        final RadioGroup q8 = (RadioGroup) findViewById(R.id.q8);
        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag5 = true;
                switch (checkedId) {
                    case R.id.q50:
                        break;
                    case R.id.q51:
                        result += 1;
                        break;
                    case R.id.q52:
                        result += 2;
                        break;
                    case R.id.q53:
                        result += 3;
                        break;
                    case R.id.q54:
                        result += 4;
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
                        break;
                    case R.id.q61:
                        result += 1;
                        break;
                    case R.id.q62:
                        result += 2;
                        break;
                    case R.id.q63:
                        result += 3;
                        break;
                    case R.id.q64:
                        result += 4;
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
                        break;
                    case R.id.q71:
                        result += 1;
                        break;
                    case R.id.q72:
                        result += 2;
                        break;
                    case R.id.q73:
                        result += 3;
                        break;
                    case R.id.q74:
                        result += 4;
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
                        break;
                    case R.id.q81:
                        result += 1;
                        break;
                    case R.id.q82:
                        result += 2;
                        break;
                    case R.id.q83:
                        result += 3;
                        break;
                    case R.id.q84:
                        result += 4;
                        break;
                }
            }
        });
        Button btnNext = findViewById(R.id.next2);
        btnNext.getBackground().setAlpha(100);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag5 == false || flag6==false || flag7 == false || flag8 == false){
                    Toast.makeText(Question1_2Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Question1_2Activity.this, Question1_3Activity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }
}
