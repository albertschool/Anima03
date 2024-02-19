package com.videxedge.anima03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Anima extends AppCompatActivity implements Runnable {
    private FrameLayout frameLayout;
    private Button btn;
    private TextView tVTimer, tVScore;
    private int counter = 0;
    private Thread thread;
    private boolean result = true;
    PingPong pingPong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima);

        frameLayout = findViewById(R.id.frm);
        tVTimer = findViewById(R.id.tVTimer);
        tVScore = findViewById(R.id.tVScore);
        btn = findViewById(R.id.btn);
        btn.setBackgroundResource(android.R.drawable.ic_media_pause);

        thread = new Thread(this);
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            tVTimer.setText(String.format("%02d:%02d",counter/60,counter%60));
        }
    };

    private Handler scoreHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int scoreD = msg.arg1;
            int scoreU = msg.arg2;
            tVScore.setText(String.format("%02dU\n%02dD",scoreU,scoreD));
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            pingPong = new PingPong(this,
                    frameLayout.getWidth(),frameLayout.getHeight(),scoreHandler);
            frameLayout.addView(pingPong);
        }
    }

    public void changeState(View view) {
        Button btn = (Button) view;
        result = pingPong.pauseResume();
        if (result) {
            btn.setBackgroundResource(android.R.drawable.ic_media_pause);
            thread = new Thread(this);
            thread.start();
        } else {
            btn.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (result) {
            try {
                Thread.sleep(1000);
                counter++;
                handler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}