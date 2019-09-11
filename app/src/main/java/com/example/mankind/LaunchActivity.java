package com.example.mankind;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.example.mankind.Entity.Users;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
        Handler x = new Handler();

        x.postDelayed(new splashhandler(), 3000);
    }

    class splashhandler implements Runnable{
        public void run() {
            ObjectInputStream ois = null;
            Users user = null;
            try {
                ois = new ObjectInputStream(new FileInputStream("user"));
                user = (Users) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(LaunchActivity.this,GuideActivity.class));
            LaunchActivity.this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.launch, menu);
        return true;
    }

}
