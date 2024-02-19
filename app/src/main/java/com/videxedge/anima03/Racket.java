package com.videxedge.anima03;

import android.graphics.Bitmap;

public class Racket extends Figure {
    private int dx = 30;

    public Racket(int x, int y, Bitmap bitmap, int w, int h) {
        super(x, y, bitmap, w, h);
    }
/*
    @Override
    public void move() {
        x += dx;
        if (x<0 && dx<0) dx = -dx;
        if (getXr()>width && dx>0) dx = -dx;
    }
*/
    public void setX(int newX) {
        x = newX - bitmap.getWidth()/2;
    }

    public void setY(int newY) {
        y = newY - bitmap.getHeight()/2;
    }
}
