package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AfterGame extends AppCompatActivity {

    TextView currentScore;
    TextView highestScore;
    SharedPreferences sharedPreferences;
    Boolean audioState;
    MediaPlayer END;
    int gameType, backPress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game);
        sharedPreferences = this.getSharedPreferences("preferences", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        gameType = sharedPreferences.getInt("gameType", 0);
        END = MediaPlayer.create(this, R.raw.game_end);
        if (audioState) {
            END.start();
        }
        currentScore = findViewById(R.id.current_score);
        highestScore = findViewById(R.id.highest_score);
        sharedPreferences = getSharedPreferences("preferences", 0);
        int score = sharedPreferences.getInt("currentScore", 0);
        currentScore.setText(String.valueOf(score));
        if(gameType==0){
            int highest = sharedPreferences.getInt("easyhighest", 0);
            highestScore.setText(String.valueOf(highest));
        }
        else if(gameType==1){
            int highest = sharedPreferences.getInt("mediumhighest", 0);
            highestScore.setText(String.valueOf(highest));
        }
        else{
            int highest = sharedPreferences.getInt("hardhighest", 0);
            highestScore.setText(String.valueOf(highest));
        }
   }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        Toast backPressToast = Toast.makeText(getApplicationContext(), "Press back button again to exit", Toast.LENGTH_SHORT);
        backPress++;
        if (backPress==1) {
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
        } else if(backPress==2){
            backPressToast.cancel();
            backPress = 0;
            System.exit(0);
        }
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
