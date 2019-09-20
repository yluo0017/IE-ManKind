package com.example.mankind;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Instruction 2 activity.
 */
public class Instruction2Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction2);
        initActionBar();
        findViewById(R.id.next).getBackground().setAlpha(180);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Instruction2Activity.this, Question2_1Activity.class));
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