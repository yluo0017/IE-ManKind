package com.example.mankind;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Result 1 activity.
 */
public class Result1Activity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        initActionBar();
        ImageView good = findViewById(R.id.good);
        ImageView danger = findViewById(R.id.danger);
        ImageView signal = findViewById(R.id.signal);
        int result = getIntent().getIntExtra("result",0)*100/44;
        String display;
        if(result<30){
            display = "If you do not feel loved or happy, it is your opinion. Do not be afraid to tell your partner and fix it at the earliest.";
            good.setVisibility(View.VISIBLE);
        }
        else if(result < 60) {
            display = "There are signs that your partner might be abusing you. Stay strong and follow the rest of the content to end this at the earliest";
            signal.setVisibility(View.VISIBLE);
        }
        else if(result < 80) {
            display = "There are strong signals that your partner is abusing you. You don’t need to be scared. You are not alone. Follow the rest of the content to find your voice to fight against this injustice.";
            danger.setVisibility(View.VISIBLE);
        }
        else{
            display = "It does not look like your safe right now, you are at a serious threat. Call 000 if you are in immediate danger or call the helplines in the sources section to talk to someone. Follow the content of the app and get the will to put an end to this abuse immediately";
            danger.setVisibility(View.VISIBLE);
        }
        TextView textView = (TextView) findViewById(R.id.display);
        textView.setText(display);
        Button con = (Button)findViewById(R.id.contin);
//        con.getBackground().setAlpha(180);
                con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Result1Activity.this, Instruction2Activity.class);
                startActivity(intent);
            }
        });
        Button exit = findViewById(R.id.exit);
//                exit.getBackground().setAlpha(180);
                        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Result1Activity.this, Question1Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setLogo(null);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setCustomView(R.layout.action_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
}