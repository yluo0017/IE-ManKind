package com.example.mankind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Result2Activity extends AppCompatActivity {
    private ArrayList<String> type;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        ImageView physical = findViewById(R.id.physical);
        ImageView financial = findViewById(R.id.financial);
        ImageView emotional = findViewById(R.id.emotional);
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
            physical.setVisibility(View.VISIBLE);
//            if(type.size()==1)

        }

        if (type.contains("financial"))
            financial.setVisibility(View.VISIBLE);
        if (type.contains("emotional"))
            emotional.setVisibility(View.VISIBLE);
        textView.setText(result.toString());
        Button exit = findViewById(R.id.exit);
        Button cont = findViewById(R.id.contin);
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
