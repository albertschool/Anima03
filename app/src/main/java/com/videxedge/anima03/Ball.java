package com.videxedge.anima03;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Ball extends Figure {
    Random rnd = new Random();
    private int dx = 20;
    private int dy = 40;
    public Ball(int x, int y, Bitmap bitmap, int w, int h) {
        super(x, y, bitmap, w, h);
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
        if (x<0 && dx<0) initdX();
        if (getXr()>width && dx>0) initdX();
        if (y<(int)(height/20) && dy<0) initdY();
        if (getYd()>(int)(height-(int)(height/20)) && dy>0) initdY();
    }
    public void restart() {
        x = (int)(width/2);
        y = (int)(height/2);
        initdX();
        initdY();
    }

    public void init(){
        int num = rnd.nextInt(50)+50; // 50-100
    }

    public void initdX()
    {
        int d = dx < 0? 1:-1;
        dx = d*(10+rnd.nextInt(20));
        init();
    }
    public void initdY()
    {
        int d = dy < 0? 1:-1;
        dy = d*(10+rnd.nextInt(20));
        init();
    }

}
