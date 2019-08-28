package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Result2Activity extends AppCompatActivity {
    private ArrayList<String> type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        final LinearLayout layout1 = findViewById(R.id.layout1);
        final LinearLayout layout2 = findViewById(R.id.layout2);
        final LinearLayout layout3 = findViewById(R.id.layout3);
        TextView textView = findViewById(R.id.display);
        type = getIntent().getStringArrayListExtra("type");
        StringBuilder result = new StringBuilder("You are suffering from ");
        for (int i=0; i<type.size(); i++){
            if(i!=0)
                result.append(" and ");
            result.append(type.get(i) + " violence");
        }
        result.append("!");
        result.append("\r\n Please sign up with your username and password so that we can display customized content for you");
        if(type.isEmpty())
            result = new StringBuilder("You may not suffer from any domestic violence, click exit if you want to leave");
        if(type.contains("physical")){
            ImageView imageView = new ImageView(Result2Activity.this);
            imageView.setImageResource(R.drawable.physical_abuse);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110,
                    110);
            imageView.setLayoutParams(params);
            if(type.size() ==1)
                layout2.addView(imageView);
            else
                layout1.addView(imageView);
        }

        if (type.contains("financial")){
            ImageView imageView = new ImageView(Result2Activity.this);
            imageView.setImageResource(R.drawable.financial);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110,
                    110);
            imageView.setLayoutParams(params);
            layout2.addView(imageView);
        }

        if (type.contains("emotional")){
            ImageView imageView = new ImageView(Result2Activity.this);
            imageView.setImageResource(R.drawable.emotional_abuse);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110,
                    110);
            imageView.setLayoutParams(params);
            if(type.size() ==1)
                layout2.addView(imageView);
            else
                layout3.addView(imageView);
        }
        textView.setText(result.toString());
        Button exit = findViewById(R.id.exit2);
        Button cont = findViewById(R.id.continue2);
        exit.getBackground().setAlpha(100);
        cont.getBackground().setAlpha(100);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result2Activity.super.onDestroy();
                System.exit(0);
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Result2Activity.this, RegisterActivity.class);
                intent.putStringArrayListExtra("type", type);
                intent.putExtra("age", getIntent().getStringExtra("age"));
                intent.putExtra("partnerGender", getIntent().getStringExtra("partnerGender"));
                intent.putExtra("lastTime", getIntent().getStringExtra("lastTime"));
                intent.putExtra("hasKid", getIntent().getBooleanExtra("hasKid", false));
                Result2Activity.this.startActivity(intent);
            }
        });
    }
}
