package com.example.mankind;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Question1Activity extends AppCompatActivity {

    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question1_1);
        result = 0;
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
                        break;
                    case R.id.q11:
                        result += 1;
                        break;
                    case R.id.q12:
                        result += 2;
                        break;
                    case R.id.q13:
                        result += 3;
                        break;
                    case R.id.q14:
                        result += 4;
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
                        break;
                    case R.id.q21:
                        result += 1;
                        break;
                    case R.id.q22:
                        result += 2;
                        break;
                    case R.id.q23:
                        result += 3;
                        break;
                    case R.id.q24:
                        result += 4;
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
                        break;
                    case R.id.q31:
                        result += 1;
                        break;
                    case R.id.q32:
                        result += 2;
                        break;
                    case R.id.q33:
                        result += 3;
                        break;
                    case R.id.q34:
                        result += 4;
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
                        break;
                    case R.id.q41:
                        result += 1;
                        break;
                    case R.id.q42:
                        result += 2;
                        break;
                    case R.id.q43:
                        result += 3;
                        break;
                    case R.id.q44:
                        result += 4;
                        break;
                }
            }
        });
        Button btnNext = findViewById(R.id.next1);
        btnNext.getBackground().setAlpha(100);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 == false || flag2 == false || flag3 == false || flag4 == false){
                    Toast.makeText(Question1Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Question1Activity.this, Question1_2Activity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        });
    }
}