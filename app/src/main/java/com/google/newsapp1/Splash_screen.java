package com.google.newsapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class Splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              try {
                  sleep(1500);
                  Intent intent = new Intent(Splash_screen.this,MainActivity.class);
                  startActivity(intent);
                  finish();
              } catch (Exception e) {
                  e.printStackTrace();
              }
            }
        });thread.start();
    }
}