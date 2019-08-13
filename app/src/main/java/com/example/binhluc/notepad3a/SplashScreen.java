package com.example.binhluc.notepad3a;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(500)
                .withBackgroundColor(Color.parseColor("#49AAFF"))
                .withLogo(R.mipmap.ic_launcher_foreground);

        //Set to view
        View view = config.create();

        //Set view to content view
        setContentView(view);
    }
}
