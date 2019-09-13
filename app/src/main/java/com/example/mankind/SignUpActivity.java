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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import androidx.annotation.NonNull;

public class SignUpActivity extends Activity {
    private EditText username;
    private EditText password;
    private boolean flag;
    private String type;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        final TextView info = (TextView) findViewById(R.id.password_check);
        final EditText confirmation = (EditText) findViewById(R.id.confirmation);
        firebaseAuth = FirebaseAuth.getInstance();
        typeInit();
        flag = true;
        confirmation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!confirmation.getText().toString().equals(password.getText().toString())) {
                    info.setVisibility(View.VISIBLE);
                } else {
                    flag = true;
                    info.setVisibility(View.INVISIBLE);
                }

            }
        });
        Button register = (Button) findViewById(R.id.createAccount);
        register.getBackground().setAlpha(180);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag)
                    return;
                String email = username.getText().toString() + "@qq.com";
                String pwd = password.getText().toString();
                if(pwd.length()<3){
                    Toast.makeText(SignUpActivity.this, "Password should contain more than 3 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.length() == 6 || TextUtils.isEmpty(pwd)){
                    Toast.makeText(SignUpActivity.this, "All Fields Are Required!", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                      Log.d("login", "createUserWithEmail:success");
                                      FirebaseUser user = firebaseAuth.getCurrentUser();
                                      Registration registration = new Registration();
                                      registration.execute();
                                  }
                                  else{
                                      Toast.makeText(SignUpActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                  }
                                }
                            });

                }
            }
        });
    }
    private void typeInit() {
                try {
                    type = getIntent().getStringExtra("type");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
    private class Registration extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            Users user = new Users();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setType(type);
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream oos = null;
            try
            {
                fileOutputStream = openFileOutput("user",
                        Context.MODE_APPEND);
                oos = new ObjectOutputStream(fileOutputStream);
                oos.writeObject(user);
                oos.close();
                fileOutputStream.close();
            }catch (IOException io){
                io.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(Object o) {
            Intent intent = new Intent(SignUpActivity.this, NavigationActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
    }
}


