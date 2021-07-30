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

public class Game extends AppCompatActivity {
    int backPressNum;
    Toast backPressToast;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.during_game);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        backPressToast = Toast.makeText(getApplicationContext(), "Press back button again to exit", Toast.LENGTH_SHORT);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        backPressNum++;
        if (backPressNum==1) {
            backPressToast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressToast.cancel();
                    backPressNum=0;
                }
            }, 1500);
        } else if(backPressNum==2){
            backPressToast.cancel();
            Intent newGame = new Intent(Game.this, MainActivity.class);
            startActivity(newGame);
            finish();
        }
    }


}


