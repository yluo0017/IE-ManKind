package com.example.mankind;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Result 2 activity.
 */
public class Result2Activity extends Activity {
    //violence type
    private String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        initActionBar();
        final ImageView physical = findViewById(R.id.physical);
        final ImageView financial = findViewById(R.id.financial);
        final ImageView emotional = findViewById(R.id.emotional);
        final ImageView smile = findViewById(R.id.smile);
        TextView textView = findViewById(R.id.display);
        typeInit();
        StringBuilder result = new StringBuilder("You are suffering from ");
        if(type.equals("physical"))
            result.append("physical violence");
        else if (type.equals("financial"))
            result.append("financial violence");
        else
            result.append("emotional violence");
        result.append("!");
        result.append("\r\n Please sign up with your username and password so that we can display customized content for you.");
        if(type.length() == 0){
            result = new StringBuilder("You may not suffer from any domestic violence, click exit if you want to leave.");
            smile.setVisibility(View.VISIBLE);
        }
        if(type.equals("physical")){
            physical.setVisibility(View.VISIBLE);
        }
        else if (type.equals("financial")){
            financial.setVisibility(View.VISIBLE);
        }
        else if (type.equals("emotional")){
            Log.e("emotional", "onCreate: " );
            emotional.setBackgroundResource(R.drawable.emotional_abuse);
            emotional.setVisibility(View.VISIBLE);
        }

        textView.setText(result.toString());
        ((MyApplication)getApplication()).setType(type);
        Button exit = findViewById(R.id.exit2);
        Button cont = findViewById(R.id.contin);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Result2Activity.this, Question2_1Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(getIntent().getIntExtra("flag",0) == 0)
                    intent.setClass(Result2Activity.this, RegisterActivity.class);
                else
                    intent.setClass(Result2Activity.this, NavigationActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("age", getIntent().getStringExtra("age"));
                intent.putExtra("partnerGender", getIntent().getStringExtra("partnerGender"));
                intent.putExtra("lastTime", getIntent().getStringExtra("lastTime"));
                intent.putExtra("hasKid", getIntent().getBooleanExtra("hasKid", false));
                Result2Activity.this.startActivity(intent);
            }
        });
    }

    //store type locally
    private void typeInit() {
            type = getIntent().getStringExtra("type");
            ((MyApplication)getApplication()).setType(type);
            try{
                FileOutputStream fileOutputStream = openFileOutput("type", Context.MODE_APPEND);
                BufferedWriter bufferedWriter = new BufferedWriter(new
                        OutputStreamWriter(fileOutputStream));
                bufferedWriter.write(type);
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileOutputStream.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }

    //Init action bar with app name
    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setLogo(null);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setCustomView(R.layout.action_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
}