package com.example.mankind;import android.app.ActionBar;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.RadioGroup;/** * The type Question 1 2 activity. */public class Question1_2Activity extends Activity {    //result for question5,6,7,8    private int question5, question6, question7, question8;    //otal result so far    private int result;    //flag indicates the destination    private int flag;    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.fragemnt_question1_2);        initActionBar();        flag = getIntent().getIntExtra("flag",0);        question5 = 4;        question6 = 4;        question7 = 4;        question8 = 4;        result = getIntent().getIntExtra("result", 0);        final RadioGroup q5 = (RadioGroup) findViewById(R.id.q5);        final RadioGroup q6 = (RadioGroup) findViewById(R.id.q6);        final RadioGroup q7 = (RadioGroup) findViewById(R.id.q7);        final RadioGroup q8 = (RadioGroup) findViewById(R.id.q8);        q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q50:                        question5 = 0;                        break;                    case R.id.q51:                        question5 = 1;                        break;                    case R.id.q52:                        question5 = 2;                        break;                    case R.id.q53:                        question5 = 3;                        break;                    case R.id.q54:                        question5 = 4;                        break;                }            }        });        q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q60:                        question6 = 0;                        break;                    case R.id.q61:                        question6 = 1;                        break;                    case R.id.q62:                        question6 = 2;                        break;                    case R.id.q63:                        question6 = 3;                        break;                    case R.id.q64:                        question6 = 4;                        break;                }            }        });        q7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q70:                        question7 = 0;                        break;                    case R.id.q71:                        question7 = 1;                        break;                    case R.id.q72:                        question7 = 2;                        break;                    case R.id.q73:                        question7 = 3;                        break;                    case R.id.q74:                        question7 = 4;                        break;                }            }        });        q8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q80:                        question8 = 0;                        break;                    case R.id.q81:                        question8 = 1;                        break;                    case R.id.q82:                        question8 = 2;                        break;                    case R.id.q83:                        question8 = 3;                        break;                    case R.id.q84:                        question8 = 4;                        break;                }            }        });        Button btnNext = findViewById(R.id.next2);//        btnNext.getBackground().setAlpha(180);        btnNext.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                result += question5;                result += question6;                result += question7;                result += question8;                Intent intent = new Intent();                intent.setClass(Question1_2Activity.this, Question1_3Activity.class);                intent.putExtra("result", result);                intent.putExtra("flag", flag);                startActivity(intent);            }        });    }    //Init action bar with app name    private void initActionBar() {        ActionBar actionBar = getActionBar();        actionBar.setLogo(null);        actionBar.setDisplayUseLogoEnabled(false);        actionBar.setCustomView(R.layout.action_bar);        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);    }}