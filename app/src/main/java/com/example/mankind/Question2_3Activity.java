package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Question2_3Activity extends AppCompatActivity {
    private boolean flag10;
    private boolean flag11;
    private boolean flag12;
    private boolean flag13;
    private boolean hasKid;
    private ArrayList<String> type;
    private int physical,financial,emotional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_question2_3);
        flag10 = false;
        flag11 = false;
        flag12 = false;
        flag13 = false;
        hasKid = false;
        emotional = 0;
        physical = getIntent().getIntExtra("physical", 0);
        financial = getIntent().getIntExtra("financial",0);
        final RadioGroup q10 = (RadioGroup) findViewById(R.id.q110);
        final RadioGroup q11 = (RadioGroup) findViewById(R.id.q111);
        final RadioGroup q12 = (RadioGroup) findViewById(R.id.q112);
        final RadioGroup q13 = (RadioGroup) findViewById(R.id.q113);
        type = new ArrayList<>();
        q10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                flag10 = true;
                switch (checkedId) {
                    case R.id.q100:
                        emotional += 1;
                        break;
                    case R.id.q101:
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
                        emotional += 1;
                        break;
                    case R.id.q1111:
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
                        emotional += 1;
                        break;
                    case R.id.q1121:
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
        Button button = (Button)findViewById(R.id.q2submit);
        button.getBackground().setAlpha(100);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag10 == false || flag11==false || flag12 == false || flag13 == false ){
                    Toast.makeText(Question2_3Activity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(physical > 1)
                    type.add("physical");
                if(financial > 1)
                    type.add("financial");
                if(emotional > 1)
                    type.add("emotional");
                Intent intent = new Intent();
                intent.setClass(Question2_3Activity.this, Result2Activity.class);
                intent.putExtra("age", getIntent().getStringExtra("age"));
                intent.putExtra("partnerGender", getIntent().getStringExtra("partnerGender"));
                intent.putExtra("lastTime", getIntent().getStringExtra("lastTime"));
                intent.putExtra("hasKid", hasKid);
                intent.putStringArrayListExtra("type", type);
                startActivity(intent);
            }
        });
    }
}
