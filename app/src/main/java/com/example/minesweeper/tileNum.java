package com.example.minesweeper;

public class tileNum {

    public float xCord;

    private float yCord;

    private int numDisplayed;


    public tileNum(float x, float y, int n) {
        xCord = x;
        yCord = y;
        numDisplayed = n;
    }




    public float getTileNumxCord() {
        return xCord;
    }

    public float getTileNumyCord() {
        return  yCord;
    }

    public int getTileNumDisplayed() {
        return  numDisplayed;
    }




}
