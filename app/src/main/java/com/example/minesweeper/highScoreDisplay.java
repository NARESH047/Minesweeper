package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class highScoreDisplay extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean audioState;
    MediaPlayer VICTORY;
    TextView highestEasyScore, highestMediumScore, highestHardScore;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
        sharedPreferences = getSharedPreferences("preferences",0);
        audioState = sharedPreferences.getBoolean("audioState", true);

    if(audioState){
        VICTORY = MediaPlayer.create(this, R.raw.victory);
        VICTORY.start();
    }

        highestEasyScore = findViewById(R.id.highest_score_easy);
        highestMediumScore = findViewById(R.id.highest_score_medium);
        highestHardScore = findViewById(R.id.highest_score_hard);

        int easyhighest = sharedPreferences.getInt("easyhighest", 0);
        int mediumhighest = sharedPreferences.getInt("mediumhighest", 0);
        int hardhighest = sharedPreferences.getInt("hardhighest", 0);

        highestEasyScore.setText(String.valueOf(easyhighest));
        highestMediumScore.setText(String.valueOf(mediumhighest));
        highestHardScore.setText(String.valueOf(hardhighest));

    }

    public void back(View view) {
        if(audioState){
            VICTORY.release();
        }
        finish();
    }

    public void exit(View view) {
        if(audioState){
            VICTORY.release();
        }
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        if(audioState){
            VICTORY.release();
        }
        finish();
    }
}
