package com.example.mankind;import android.app.ActionBar;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.view.View;import android.widget.Button;import android.widget.RadioGroup;import android.widget.Toast;import java.util.ArrayList;import java.util.List;import androidx.appcompat.app.AppCompatActivity;/** * The type Question 2 3 activity. */public class Question2_3Activity extends Activity {    //violence type    private String type;    //result for question 10,11,12    private int question10, question11, question12;    //result for physical, financial and emotional    private int physical,financial,emotional;    //has kid or not    private boolean hasKid;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.fragment_question2_3);        initActionBar();        hasKid = true;        question10 = 1;        question11 = 1;        question12 = 1;        emotional = 0;        physical = getIntent().getIntExtra("physical", 0);        financial = getIntent().getIntExtra("financial",0);        final RadioGroup q10 = (RadioGroup) findViewById(R.id.q110);        final RadioGroup q11 = (RadioGroup) findViewById(R.id.q111);        final RadioGroup q12 = (RadioGroup) findViewById(R.id.q112);        final RadioGroup q13 = (RadioGroup) findViewById(R.id.q113);        q10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q100:                        question10 = 1;                        break;                    case R.id.q101:                        question10 = 0;                        break;                }            }        });        q11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q1110:                        question11 = 1;                        break;                    case R.id.q1111:                        question11 = 0;                        break;                }            }        });        q12.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q1120:                        question12 = 1;                        break;                    case R.id.q1121:                        question12 = 0;                        break;                }            }        });        q13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(RadioGroup group, int checkedId) {                switch (checkedId) {                    case R.id.q130:                        hasKid = true;                        break;                    case R.id.q131:                        hasKid = false;                        break;                }            }        });        Button button = (Button)findViewById(R.id.q2submit);//        button.getBackground().setAlpha(180);        button.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                emotional += question10 + question11 + question12;                if(physical > 1)                    type = "physical";                else if(financial > 1)                    type = "financial";                else if(emotional > 1)                    type = "emotional";                else                    type = "";                Intent intent = new Intent();                intent.setClass(Question2_3Activity.this, Result2Activity.class);                intent.putExtra("age", getIntent().getStringExtra("age"));                intent.putExtra("partnerGender", getIntent().getStringExtra("partnerGender"));                intent.putExtra("lastTime", getIntent().getStringExtra("lastTime"));                intent.putExtra("hasKid", hasKid);                intent.putExtra("type", type);                intent.putExtra("flag",getIntent().getIntExtra("flag",0));                startActivity(intent);            }        });    }    //Init action bar with app name    private void initActionBar() {        ActionBar actionBar = getActionBar();        actionBar.setLogo(null);        actionBar.setDisplayUseLogoEnabled(false);        actionBar.setCustomView(R.layout.action_bar);        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);    }}