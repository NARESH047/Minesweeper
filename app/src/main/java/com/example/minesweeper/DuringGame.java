package com.example.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;

public class DuringGame extends View {
    Context Context;

    ArrayList<Cord> cordinates = new ArrayList<Cord>();
    ArrayList<Cord> randomForMineOrg = new ArrayList<Cord>();
    ArrayList<Cord> safeTiles = new ArrayList<Cord>();
    ArrayList<Cord> randomForMine = new ArrayList<Cord>();
    ArrayList<Cord> forNumber = new ArrayList<Cord>();
    ArrayList<tileNum> forTileNumber = new ArrayList<tileNum>();

    float xTouched, yTouched;
    Paint textPaint, numPaint, objPaint, borderPaint;
    Bitmap tileOrg, safeTileOrg, mineOrg, tile, safeTile, mine;
    int dWidth, dHeight;
    MediaPlayer SAFE, MINE, WIN, END;
    SharedPreferences sharedPreferences;
    float x, y, w, s;
    Boolean audioState;
    int i, n, mineNum, gameType;
    int score = 0;
    int numOfCord;
    int q,p =1;
    Cord o = new Cord(0,0);


    public DuringGame(Context context) {
        super(context);
        Context = context;
        textPaint = new Paint();
        numPaint = new Paint();
        objPaint = new Paint();
        borderPaint = new Paint();

        tileOrg = BitmapFactory.decodeResource(getResources(), R.drawable.tile);
        safeTileOrg = BitmapFactory.decodeResource(getResources(), R.drawable.safe_tile);
        mineOrg = BitmapFactory.decodeResource(getResources(), R.drawable.mine);

        sharedPreferences = context.getSharedPreferences("preferences", 0);
        audioState = sharedPreferences.getBoolean("audioState", true);

        gameType = sharedPreferences.getInt("gameType", 0);

        SAFE = MediaPlayer.create(context, R.raw.safe);
        MINE = MediaPlayer.create(context, R.raw.mine);
        WIN = MediaPlayer.create(context, R.raw.win);

        objPaint.setAntiAlias(true);
        objPaint.setFilterBitmap(true);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(120f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        numPaint.setColor(Color.WHITE);
        numPaint.setTextAlign(Paint.Align.CENTER);
        numPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        numPaint.setStyle(Paint.Style.FILL);
        borderPaint.setStrokeWidth(10);
        borderPaint.setColor(Color.parseColor("#FFD770"));
        borderPaint.setStyle(Paint.Style.STROKE);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        tile = Bitmap.createScaledBitmap(tileOrg, (dWidth-16)/8, (dWidth-16)/8, true);
        safeTile = Bitmap.createScaledBitmap(safeTileOrg, (dWidth-16)/8, (dWidth-16)/8, true);
        mine = Bitmap.createScaledBitmap(mineOrg, (dWidth-16)/8, (dWidth-16)/8, true);

        numPaint.setTextSize(tile.getHeight()/2);

        for(y= dHeight/4; y< dHeight/4 + 8*tile.getHeight() ; y = y + tile.getHeight()) {
            for(x=8; x<8+ 8*tile.getWidth(); x=x+tile.getWidth()) {
                cordinates.add(new Cord(x, y));
            }
        }

        if(gameType==0){
            mineNum=8;
        }else if(gameType==1){
            mineNum=16;
        }else {
            mineNum=20;
        }

        numOfCord = cordinates.size();
        while (randomForMineOrg.size()<mineNum){
            i = random();
            if(!randomForMineOrg.contains(cordinates.get(i))){
                randomForMineOrg.add(cordinates.get(i));
            }
        }

        score = safeTiles.size();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(0,0  ,dWidth, dHeight-10,borderPaint);
        for(Cord cord:cordinates){
            canvas.drawBitmap(tile, cord.getxCord(), cord.getyCord(), objPaint);
        }

        for(Cord cord:safeTiles){
            canvas.drawBitmap(safeTile, cord.getxCord(), cord.getyCord(), objPaint);
        }

        for(Cord cord:randomForMine){
            canvas.drawBitmap(mine, cord.getxCord(), cord.getyCord(), objPaint);
        }

        for(tileNum num:forTileNumber){
            canvas.drawText(String.valueOf(num.getTileNumDisplayed()), num.getTileNumxCord()-(tile.getWidth()/2), num.getTileNumyCord()-(tile.getWidth()/2), numPaint);
        }

        canvas.drawText("Score: "+safeTiles.size(), dWidth/2, dHeight/8, textPaint);
        invalidate();

        if(safeTiles.size()>3) {
            textPaint.setColor(Color.GREEN);
        }

    }

    public int random() {
        Random r = new Random();
        int randomIndex = r.nextInt(numOfCord) ;
        return randomIndex;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xTouched = event.getX();
        yTouched = event.getY();
        if (yTouched >= dHeight / 4 && yTouched < dHeight/4 + 8*tile.getWidth()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                p = (int) (((xTouched - 8) / tile.getWidth()) + 1);
                q = (int) (((yTouched - (dHeight / 4)) / tile.getHeight()) + 1);
                w  =  ((8+(p-1)*tile.getWidth()));
                s = dHeight/4 + (q-1)*tile.getHeight();
                o = new Cord(w, s);
                for(Cord cordi:randomForMineOrg){
                    {
                        if(cordEqual(cordi, o)){
                            randomForMine.add(o);
                            o = new Cord(0,0);
                            Vibrator w = (Vibrator) Context.getSystemService(VIBRATOR_SERVICE);
                            if (audioState) {
                                MINE.start();
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                w.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                w.vibrate(1000);
                            }
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("currentScore", safeTiles.size());
                            editor.commit();
                            int highest = sharedPreferences.getInt("highest", 0);
                            if(safeTiles.size() > highest){
                                highest = safeTiles.size();
                                editor.putInt("highest", highest);
                                editor.commit();
                            }
                            Intent intent = new Intent(Context, AfterGame.class);
                            Context.startActivity(intent);
                            ((Activity) Context).finish();
                        }
                    }
                }
                for (Cord cordi:cordinates){
                    if(cordEqual(cordi, o) && (!containedIn(o, randomForMineOrg)) && (!containedIn(o, safeTiles))){
                        safeTiles.add(o);
                        if(safeTiles.size() == 64-mineNum){
                            if (audioState) {
                                WIN.start();}
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("currentScore", safeTiles.size());
                                editor.commit();
                                int highest = sharedPreferences.getInt("highest", 0);
                                    highest = safeTiles.size();
                                    editor.putInt("highest", highest);
                                    editor.commit();
                                Intent intent = new Intent(Context, AfterGame.class);
                                Context.startActivity(intent);
                                ((Activity) Context).finish();

                        } else {
                            if (audioState) {
                                if(safeTiles.size()%3 == 0 && safeTiles.size()!=0) {
                                    WIN.start();
                                }
                            }
                        for(int i=-1; i<2; i++) {
                            forNumber.add(new Cord((o.getxCord() + i*tile.getWidth()), o.getyCord() - tile.getHeight()));
                        }
                        for(int i=-1; i<2; i++) {
                            forNumber.add(new Cord((o.getxCord() + i*tile.getWidth()), o.getyCord() +tile.getHeight()));
                        }
                        forNumber.add(new Cord((o.getxCord() - tile.getWidth()), o.getyCord()));
                        forNumber.add(new Cord((o.getxCord() + tile.getWidth()), o.getyCord()));
                        for(Cord cordn:forNumber){
                            for(Cord cordm:randomForMineOrg){
                                if(cordEqual(cordn, cordm)){
                                    n++;
                                }
                            }
                        }
                        forTileNumber.add(new tileNum(w+(tile.getWidth()),s+(tile.getHeight()),n));
                        n=0;
                        forNumber.clear();
                        o = new Cord(0,0);
                        if (audioState) {
                            SAFE.start();
                        }
                    }
                }}
            }

        }
        return true;
    }

    public boolean cordEqual(Cord a, Cord b) {
        float aX = a.getxCord();
        float aY = a.getyCord();
        float bX = b.getxCord();
        float bY = b.getyCord();
        if (aX == bX && (aY == bY)) {
            return true;
        } else return false;
    }

    public  boolean containedIn(Cord a, ArrayList<Cord> b) {
        for(Cord cordi:b){
            if(cordEqual(a, cordi)){
                return true;
            }
        }
        return false;
    }

}

