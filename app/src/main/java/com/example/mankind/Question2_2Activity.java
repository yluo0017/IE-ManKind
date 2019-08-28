package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question2_2Activity extends AppCompatActivity {

    private boolean flag5;
    private boolean flag6;
    private boolean flag7;
    private boolean flag8;
    private boolean flag9;
    private int physical;
    private int financial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question2_2);
        flag5 = false;
        flag6 = false;
        flag7 = false;
        flag8 = false;
        flag9 = false;
        financial = 0;
        physical = getIntent().getIntExtra("physical", 0);
        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);
        final RadioGroup q6 = (RadioGroup) findViewById(R.id.q6);
        final RadioGroup q7 = (RadioGroup) findViewById(R.id.q7);
        final RadioGroup q8 = (RadioGroup) findViewById(R.id.q8);
        final RadioGroup q9 = (RadioGroup) findViewById(R.id.q9);
        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag5 = true;
                switch (checkedId) {
                    case R.id.q50:
                        physical += 1;
                        break;
                    case R.id.q51:
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
                        physical += 1;
                        break;
                    case R.id.q61:
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
                       financial += 1;
                        break;
                    case R.id.q71:
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
                        financial += 1;
                        break;
                    case R.id.q81:
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
                        financial += 1;
                        break;
                    case R.id.q91:
                        break;
                }
            }
        });
        Button button = (Button)findViewById(R.id.next3);
        button.getBackground().setAlpha(100);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag5 == false || flag6==false || flag7 == false || flag8 == false || flag9==false){
                    Toast.makeText(Question2_2Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Question2_2Activity.this, Question2_3Activity.class);
                intent.putExtra("age", getIntent().getStringExtra("age"));
                intent.putExtra("partnerGender", getIntent().getStringExtra("partnerGender"));
                intent.putExtra("lastTime", getIntent().getStringExtra("lastTime"));
                intent.putExtra("physical", physical);
                intent.putExtra("financial", financial);
                startActivity(intent);
            }
        });
    }
}
