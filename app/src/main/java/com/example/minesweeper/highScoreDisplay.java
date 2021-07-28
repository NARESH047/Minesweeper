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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        sharedPreferences = getSharedPreferences("preferences",0);

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
        Intent newGame = new Intent(highScoreDisplay.this, MainActivity.class);
        startActivity(newGame);
        finish();
    }



    public void exit(View view) {
        finishAffinity();
    }

}
