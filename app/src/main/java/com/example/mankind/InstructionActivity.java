package com.example.mankind;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Instruction activity.
 */
public class InstructionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        initActionBar();
        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(InstructionActivity.this);
                dialogBuilder.setMessage("●    This is a test designed to measure the level of domestic violence being subjected to. It is based on a popularly used questionnaire developed by the School of Psychology, University of New South Wales, Australia." +
                        "\n" + "●    It is NOT INTENDED to provide a full psychological diagnosis but solely as an indication via self-reporting of the current condition; which could lead to seek professional help and support." +
                        "\n" + "NOTE: EVEN IF THE TEST DOES NOT CONCLUDE A POTENTIAL DOMESTIC VIOLENCE ISSUE, IF YOU FEEL DISTRESSED, PLEASE TALK TO A QUALIFIED PROFESSIONAL OR POLICE." +
                        "\n" + "THIS TEST DOES NOT INTEND TO SUBSTITUTE A PROFESSIONAL EVALUATION");
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                dialogBuilder.show();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstructionActivity.this, Question1Activity.class));
            }
        });
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.logo_bg);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
    }
}
