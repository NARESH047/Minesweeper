package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean audioState;
    ImageButton audioImage;
    int gameType;
    MediaPlayer GAMEMUSIC;
    int backPressNum;
    Toast toastForBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        audioImage = findViewById(R.id.audioImage);
        sharedPreferences = getSharedPreferences("preferences",0);
        gameType = sharedPreferences.getInt("gameType", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        if(audioState){
            audioImage.setImageResource(R.drawable.audio_on);
            GAMEMUSIC = MediaPlayer.create(this, R.raw.arcade_music_loop);
            GAMEMUSIC.start();
            GAMEMUSIC.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    GAMEMUSIC.start();
                }
            });
        }else{
            if (GAMEMUSIC != null ) {
                GAMEMUSIC.release();}
            audioImage.setImageResource(R.drawable.audio_off);
        }
    }

    public void audioPref(View view) {
        if(audioState){
            audioState = false;
            audioImage.setImageResource(R.drawable.audio_off);
            if (GAMEMUSIC != null ) {
            GAMEMUSIC.release();}
        }else{
            audioState = true;
            audioImage.setImageResource(R.drawable.audio_on);
            GAMEMUSIC = MediaPlayer.create(this, R.raw.arcade_music_loop);
            GAMEMUSIC.start();
            GAMEMUSIC.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    GAMEMUSIC.start();
                }
            });
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("audioState", audioState);
        editor.commit();    }


    public void easyGame(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gameType", 0);
        editor.commit();
        DuringGame duringGame = new DuringGame(this);
        setContentView(duringGame);
    }
    public void mediumGame(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gameType", 1);
        editor.commit();
        DuringGame duringGame = new DuringGame(this);
        setContentView(duringGame);
    }
    public void hardGame(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("gameType", 2);
        editor.commit();
        DuringGame duringGame = new DuringGame(this);
        setContentView(duringGame);
    }
    public void highestScore(View view) {
        Intent highScore = new Intent(MainActivity.this, highScoreDisplay.class);
        startActivity(highScore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (GAMEMUSIC != null ) {
            GAMEMUSIC.release();
            GAMEMUSIC = MediaPlayer.create(this, R.raw.arcade_music_loop);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GAMEMUSIC != null ) {
            GAMEMUSIC.start();
        }
    }

    public void exit(View view) {
        finishAffinity();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        Toast backPressToast = Toast.makeText(getApplicationContext(), "Press back button again to exit", Toast.LENGTH_SHORT);
        backPressNum++;
        if (backPressNum==1) {
            backPressToast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressToast.cancel();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }, 1000);
        } else if(backPressNum==2){
            backPressToast.cancel();
            backPressNum = 0;
            System.exit(0);
        }
    }

    }
