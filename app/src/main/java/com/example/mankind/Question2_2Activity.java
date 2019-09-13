
package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Question2_2Activity extends AppCompatActivity {
    private int question5, question6, question7, question8, question9;
    private int physical;
    private int financial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question2_2);
        financial = 0;
        question5 = 1;
        question6 = 1;
        question7 = 1;
        question8 = 1;
        question9 = 1;
        physical = getIntent().getIntExtra("physical", 0);
        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);
        final RadioGroup q6 = (RadioGroup) findViewById(R.id.q6);
        final RadioGroup q7 = (RadioGroup) findViewById(R.id.q7);
        final RadioGroup q8 = (RadioGroup) findViewById(R.id.q8);
        final RadioGroup q9 = (RadioGroup) findViewById(R.id.q9);
        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.q50:
                        question5 = 1;
                        break;
                    case R.id.q51:
                        question5 = 0;
                        break;
                }
            }
        });
        q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.q60:
                        question6 = 1;
                        break;
                    case R.id.q61:
                        question6 = 0;
                        break;
                }
            }
        });
        q7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.q70:
                        question7 = 1;
                        break;
                    case R.id.q71:
                        question7 = 0;
                        break;
                }
            }
        });
        q8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.q80:
                        question8 = 1;
                        break;
                    case R.id.q81:
                        question8 = 0;
                        break;
                }
            }
        });
        q9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.q90:
                        question9 = 1;
                        break;
                    case R.id.q91:
                        question9 = 0;
                        break;
                }
            }
        });
        Button button = (Button)findViewById(R.id.next3);
        button.getBackground().setAlpha(180);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                physical +=  question5 + question6;
                financial += question7 + question8 + question9;
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