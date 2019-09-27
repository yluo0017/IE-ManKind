package com.example.mankind;import android.app.ActionBar;import android.app.Activity;import android.app.AlertDialog;import android.content.Intent;import android.os.Bundle;import android.util.Log;import android.view.Gravity;import android.view.View;import android.view.Window;import android.widget.Button;import android.widget.LinearLayout;import android.widget.RadioGroup;import android.widget.TextView;import android.widget.Toast;import androidx.appcompat.app.AppCompatActivity;/** * The type Question 1 3 activity. */public class Question1_3Activity extends Activity {    private int question9, question10, question11;    private int result;    private int flag;    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.fragment_question1_3);        flag = getIntent().getIntExtra("flag",0);        initActionBar();        question9 = 4;        question10 = 4;        question11 = 4;        result = getIntent().getIntExtra("result", 0);        final RadioGroup q9 = (RadioGroup) findViewById(R.id.q9);        final RadioGroup q10 = (RadioGroup) findViewById(R.id.q110);        final RadioGroup q11 = (RadioGroup) findViewById(R.id.q111);        q9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q90:                        question9 = 0;                        break;                    case R.id.q91:                        question9 = 1;                        break;                    case R.id.q92:                        question9 = 2;                        break;                    case R.id.q93:                        question9 = 3;                        break;                    case R.id.q94:                        question9 = 4;                        break;                }            }        });        q10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q100:                        question10 = 0;                        break;                    case R.id.q101:                        question10 = 1;                        break;                    case R.id.q102:                        question10 = 2;                        break;                    case R.id.q103:                        question10 = 3;                        break;                    case R.id.q104:                        question10 = 4;                        break;                }            }        });        q11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q1110:                        question11 = 0;                        break;                    case R.id.q1111:                        question11 = 1;                        break;                    case R.id.q1112:                        question11 = 2;                        break;                    case R.id.q1113:                        question11 = 3;                        break;                    case R.id.q1114:                        question11 = 4;                        break;                }            }        });        Button submit = findViewById(R.id.q1submit);//        submit.getBackground().setAlpha(180);        submit.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                result += question9;                result += question10;                result += question11;                Intent intent = new Intent();                intent.setClass(Question1_3Activity.this, Result1Activity.class);                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                intent.putExtra("result", result);                intent.putExtra("flag", flag);                startActivity(intent);            }        });    }    //Init action bar with app name    private void initActionBar() {        ActionBar actionBar = getActionBar();        actionBar.setLogo(null);        actionBar.setDisplayUseLogoEnabled(false);        actionBar.setCustomView(R.layout.action_bar);        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);    }}