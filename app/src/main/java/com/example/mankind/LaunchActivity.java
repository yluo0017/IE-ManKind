package com.example.mankind;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.example.mankind.Entity.Users;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * The type Launch activity.
 */
public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
        Handler x = new Handler();

        x.postDelayed(new splashhandler(), 3000);
    }

    /**
     * The type Splashhandler.
     */
    class splashhandler implements Runnable{
        public void run() {
            String type = "";
            try{
                FileInputStream fileInputStream= openFileInput("type");
                if (fileInputStream!=null){
                    BufferedReader bufferedReader= new BufferedReader(new
                            InputStreamReader(fileInputStream));
                    String temp;
                    while ((temp =bufferedReader.readLine()) != null){
                        type = temp;
                    }
                    fileInputStream.close();
                }
            }catch (IOException io){
                io.printStackTrace();
            }
            if(type.length()>0){
                ((MyApplication)getApplication()).setType(type);
                Intent intent = new Intent(LaunchActivity.this, RegisterActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
            else{
                startActivity(new Intent(LaunchActivity.this,GuideActivity.class));
                LaunchActivity.this.finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.launch, menu);
        return true;
    }

}
