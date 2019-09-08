package com.example.mankind;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question1Activity extends AppCompatActivity {

    private int question1,question2,question3,question4;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question1_1);
        result = 0;
        question1 = 4;
        question2 = 4;
        question3 = 4;
        question4 = 4;
        final RadioGroup q1 = (RadioGroup) findViewById(R.id.q1);
        final RadioGroup q2 = (RadioGroup) findViewById(R.id.q2);
        final RadioGroup q3 = (RadioGroup) findViewById(R.id.q3);
        final RadioGroup q4 = (RadioGroup) findViewById(R.id.q4);
        q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
        Button btnNext = findViewById(R.id.next1);
        btnNext.getBackground().setAlpha(180);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = question1 + question2 + question3 + question4;
                Intent intent = new Intent();
                intent.setClass(Question1Activity.this, Question1_2Activity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }
}