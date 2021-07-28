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
    TextView highestEasyScore, highestMediumScore, highestHardScore;
    int backPress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sharedPreferences = getSharedPreferences("preferences",0);

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
        Intent newGame = new Intent(highScoreDisplay.this, MainActivity.class);
        startActivity(newGame);
        finish();
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

    public void exit(View view) {
        finishAffinity();
    }

}
