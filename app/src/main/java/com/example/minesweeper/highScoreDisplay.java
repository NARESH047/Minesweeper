package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class highScoreDisplay extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView highestEasyScore, highestMediumScore, highestHardScore;
    boolean audioState;
    MediaPlayer GAMEMUSIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        sharedPreferences = this.getSharedPreferences("preferences", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        GAMEMUSIC = MediaPlayer.create(this, R.raw.arcade_music_loop);
        if(audioState){
            if (GAMEMUSIC != null ) {
                GAMEMUSIC = MediaPlayer.create(this, R.raw.arcade_music_loop);
                GAMEMUSIC.start();
            }

        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        highestEasyScore = findViewById(R.id.highest_score_easy);
        highestMediumScore = findViewById(R.id.highest_score_medium);
        highestHardScore = findViewById(R.id.highest_score_hard);


        sharedPreferences = getSharedPreferences("preferences",0);
        int easyhighest = sharedPreferences.getInt("easyhighest", 0);
        int mediumhighest = sharedPreferences.getInt("mediumhighest", 0);
        int hardhighest = sharedPreferences.getInt("hardhighest", 0);

        highestEasyScore.setText(String.valueOf(easyhighest));
        highestMediumScore.setText(String.valueOf(mediumhighest));
        highestHardScore.setText(String.valueOf(hardhighest));

    }

    public void back(View view) {
        GAMEMUSIC.release();
        Intent newGame = new Intent(highScoreDisplay.this, MainActivity.class);
        startActivity(newGame);
        finish();
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
        GAMEMUSIC.release();
        finishAffinity();
    }

}
