package com.videxedge.anima03;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

public abstract class Figure {
    protected int x;
    protected int y;
    protected Rect rect = new Rect();
    protected Bitmap bitmap;
    protected static int width;
    protected static int height;

    public Figure(int x, int y, Bitmap bitmap, int w, int h) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        width = w;
        height = h;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y,null);
    }

    public void move() {
    }

    public int getXr() {
        return x+bitmap.getWidth();
    }
    public int getX() {
        return x;
    }
    public int getYd() {
        return y+bitmap.getHeight();
    }
    public int getY() {
        return y;
    }

    public boolean inRange(int xTouch, int yTouch) {
        rect.set(x,y,getXr(),getYd());
        return rect.contains(xTouch, yTouch);
    }

    public boolean inSide(Rect other) {
        rect.set(x,y,getXr(),getYd());
        return rect.intersect(other);
    }

    public Rect getRect() {
        rect.set(x,y,getXr(),getYd());
        return rect;
    }
}
