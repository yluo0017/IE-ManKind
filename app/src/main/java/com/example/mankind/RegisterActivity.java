package com.example.mankind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mankind.Entity.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends Activity {

    private EditText username;
    private EditText password;
    private boolean flag;
    private String type;
    private FirebaseAuth firebaseAuth;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        typeInit();
        flag = true;
        Button exit = (Button)findViewById(R.id.login);
        exit.getBackground().setAlpha(180);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString()+ "@qq.com";
                String pwd = password.getText().toString();
                if(email.length() == 6 || TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this, "All Fields Are Required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Log.d("Login", "signInWithEmail:success");
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        Intent i = new Intent(RegisterActivity.this, NavigationActivity.class);
                                        i.putExtra("type", type);
                                        startActivity(i);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
        });
        Button register = (Button)findViewById(R.id.register);
        register.getBackground().setAlpha(180);
                register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", type);
                intent.setClass(RegisterActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, NavigationActivity.class);
                i.putExtra("type", type);
                startActivity(i);
            }
        });
    }

    private void typeInit() {
        type = getIntent().getStringExtra("type");
        try{
            FileOutputStream fileOutputStream = openFileOutput("type", Context.MODE_APPEND);
            BufferedWriter bufferedWriter = new BufferedWriter(new
                    OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(type);
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileOutputStream.close();
            Log.e("*****", "createType: " + type );
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
