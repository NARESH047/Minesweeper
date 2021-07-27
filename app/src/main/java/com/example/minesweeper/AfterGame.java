package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AfterGame extends AppCompatActivity {

    TextView currentScore;
    TextView highestScore;
    SharedPreferences sharedPreferences;
    Boolean audioState;
    MediaPlayer END;lk

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game);
        sharedPreferences = this.getSharedPreferences("preferences", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        END = MediaPlayer.create(this, R.raw.game_end);
        if (audioState) {
            END.start();
        }
        currentScore = findViewById(R.id.current_score);
        highestScore = findViewById(R.id.highest_score);
        sharedPreferences = getSharedPreferences("preferences", 0);
        int score = sharedPreferences.getInt("currentScore", 0);
        currentScore.setText(String.valueOf(score));
        int highest = sharedPreferences.getInt("highest", 0);
        highestScore.setText(String.valueOf(highest));
   }

    public void restart(View view) {
        END.release();
        Intent newGame = new Intent(AfterGame.this, MainActivity.class);
        startActivity(newGame);
        finish();
    }

    public void exit(View view) {
        END.release();
        finish();
    }

}
