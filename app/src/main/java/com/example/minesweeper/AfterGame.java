package com.example.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
    MediaPlayer END, VICTORY;
    int gameType;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_game);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);
//        VICTORY = MediaPlayer.create(this, R.raw.victory);
//        END = MediaPlayer.create(this, R.raw.game_end);
        sharedPreferences = this.getSharedPreferences("preferences", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);
        gameType = sharedPreferences.getInt("gameType", 0);
        currentScore = findViewById(R.id.current_score);
        highestScore = findViewById(R.id.highest_score);
        sharedPreferences = getSharedPreferences("preferences", 0);
        int score = sharedPreferences.getInt("currentScore", 0);
        currentScore.setText(String.valueOf(score));
        if(gameType==0){
            int highest = sharedPreferences.getInt("easyhighest", 0);
            highestScore.setText(String.valueOf(highest));
//            if ((score>=highest && score!=0) && audioState) {
//                VICTORY.start();
//            } else if(audioState){
//                END.start();
//            }
        }
        else if(gameType==1){
            int highest = sharedPreferences.getInt("mediumhighest", 0);
            highestScore.setText(String.valueOf(highest));
//            if ((score>=highest && score!=0) && audioState) {
//                VICTORY.start();
//            } else if(audioState){
//                END.start();
//            }
        }
        else{
            int highest = sharedPreferences.getInt("hardhighest", 0);
            highestScore.setText(String.valueOf(highest));
//            Vibrator w = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                w.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
//            } else {
//                w.vibrate(1000);
//            }
//            if ((score>=highest && score!=0) && audioState) {
//                VICTORY.start();
//            } else if(audioState){
//                END.start();
//            }
        }
   }

    @Override
    protected void onPause() {
//        if(END!=null){
//            END.release();
//        } else if(VICTORY!=null){
//            VICTORY.release();
//        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
//        if(END!=null){
//            END.release();
//        } else if(VICTORY!=null){
//            VICTORY.release();
//        }
        Intent newGame = new Intent(AfterGame.this, MainActivity.class);
        startActivity(newGame);
        finish();
    }

    public void restart(View view) {
//        if(END!=null){
//            END.release();
//        } else if(VICTORY!=null){
//            VICTORY.release();
//        }
        Intent newGame = new Intent(AfterGame.this, MainActivity.class);
        startActivity(newGame);
        finish();
    }

    public void exit(View view) {
//        if(END!=null){
//            END.release();
//        } else if(VICTORY!=null){
//            VICTORY.release();
//        }
        finishAffinity();
    }
}
