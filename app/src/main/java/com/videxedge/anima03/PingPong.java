package com.videxedge.anima03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class PingPong extends SurfaceView implements Runnable {
    private int width;
    private int height;
    private Bitmap imgRacketR, imgRacketB, imgBall, imgNet;
    private Paint bgPaint;
    private Ball ball;
    private Racket racketD, racketU;
    private Net netD, netU;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Thread thread;
    private boolean isRunning = true;
    private int scoreU = 0;
    private int scoreD = 0;
    int interval = 50;
    private Handler scoreHandler;

    public PingPong(Context context, int width, int height, Handler scoreHandler) {
        super(context);
        this.width = width;
        this.height = height;
        this.scoreHandler = scoreHandler;
        holder = getHolder();
// image creating
        imgRacketB = BitmapFactory.decodeResource(getResources(),R.drawable.bluet);
        imgRacketB = Bitmap.createScaledBitmap(imgRacketB,height/10,height/10,false);
        imgRacketR = BitmapFactory.decodeResource(getResources(),R.drawable.redt);
        imgRacketR = Bitmap.createScaledBitmap(imgRacketR,height/10,height/10,false);
        imgBall = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        imgBall = Bitmap.createScaledBitmap(imgBall,height/20,height/20,false);
        imgNet = BitmapFactory.decodeResource(getResources(),R.drawable.net);
        imgNet = Bitmap.createScaledBitmap(imgNet,width,height/20,false);
// painting background
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
// creatong objects
        ball = new Ball(0,height/2,imgBall,width,height);
        racketD = new Racket(width/2, (int) (0.95*height-height/10),imgRacketB,width,height);
        racketU = new Racket(width/2, (int) (0.05*height),imgRacketR,width,height);
        netD = new Net(0,(int)(0.95*height),imgNet,width, height);
        netU = new Net(0,0,imgNet,width, height);

// running thread
        thread = new Thread(this);
        thread.start();
    }

    public void drawCanvas() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas(); // canvas lock & create
            canvas.drawPaint(bgPaint); // paint background
            ball.draw(canvas);              // draw ball
            racketD.draw(canvas);           // draw down racket
            racketU.draw(canvas);           // draw up racket
            netD.draw(canvas);           // draw down racket
            netU.draw(canvas);           // draw up racket
            holder.unlockCanvasAndPost(canvas); // unlock canvas after paint
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            ball.move();
            drawCanvas();
            logicTest();
//            racketD.move();
//            racketU.move();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean pauseResume() {
        isRunning = !isRunning;
        if (isRunning) {
            thread = new Thread(this);
            thread.start();
        }
        return isRunning;
    }

    public int getScoreU() {
        return scoreU;
    }

    public int getScoreD() {
        return scoreD;
    }

    private void logicTest() {
        if (ball.inSide(racketU.getRect())) {
            ball.initdY();
            ball.initdX();
        } else if (ball.inSide(racketD.getRect())) {
            ball.initdY();
            ball.initdX();
        } else if (ball.inSide(netU.getRect())) {
            scoreD++;
            Message m = scoreHandler.obtainMessage();
            m.arg1 = scoreD;
            m.arg2 = scoreU;
            scoreHandler.sendMessage(m);
            ball.restart();
        } else if (ball.inSide(netD.getRect())) {
            scoreU++;
            Message m = scoreHandler.obtainMessage();
            m.arg1 = scoreD;
            m.arg2 = scoreU;
            scoreHandler.sendMessage(m);
            ball.restart();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int count = event.getPointerCount();
        for (int i=0; i<count; i++) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                if (y < (int)(0.25*height) && racketU.inRange(x,y)) {
                    racketU.setX(x);
                    racketU.setY(y);
                }
                if (y > (int)(0.75*height) && racketD.inRange(x,y)) {
                    racketD.setX(x);
                    racketD.setY(y);
                }
            }
        }
        return true;
    }
}
