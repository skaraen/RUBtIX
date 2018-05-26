package com.example.karaens.rubtix;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class RubikTimer extends AppCompatActivity {

    RelativeLayout screen;
    TextView cdTime,solveTime,guide;
    boolean isCtDownOn=false,isTimerOn=false;
    long startTime;
    long millisLeft=15000L;
    int msecs,secs,mins;
    Handler handler;
    MediaPlayer beep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubik_timer);
        cdTime=findViewById(R.id.cdTime);
        solveTime=findViewById(R.id.solveTime);
        guide=findViewById(R.id.guide);
        screen=findViewById(R.id.screen);
        beep=MediaPlayer.create(this,R.raw.beep);
        handler=new Handler();
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCtDownOn==false) {
                    if(isTimerOn==false) {
                        if (millisLeft == 15000L)
                            startCountDown();
                        else
                            reset();
                    }
                    else
                        stopTimer();
                }
                else {
                    stopCountDown();
                }
                }
        });
    }
    public void startCountDown()
    {
        isCtDownOn=true;
        guide.setText("Countdown has begun. Tap when ready.");
        cdTime.setTextColor(this.getResources().getColor(R.color.green));
        startTime = SystemClock.uptimeMillis();
        handler.post(ctDown);
    }
    public void stopCountDown()
    {
        isCtDownOn=false;
        handler.removeCallbacks(ctDown);
        cdTime.setTextColor(this.getResources().getColor(R.color.red));
        guide.setText("Timer has begun. Tap when done.");
        startTime=SystemClock.uptimeMillis();
        isTimerOn=true;
        solveTime.setTextColor(this.getResources().getColor(R.color.green));
        handler.post(solveTimer);
    }
    public void stopTimer()
    {
      handler.removeCallbacks(solveTimer);
      solveTime.setTextColor(this.getResources().getColor(R.color.red));
      guide.setText("That's your solve time. Tap to reset.");
      isTimerOn=false;
    }
    public  void reset()
    {
        solveTime.setText("00:00:00");
        solveTime.setTextColor(this.getResources().getColor(R.color.grey));
        cdTime.setText("00:15:00");
        cdTime.setTextColor(this.getResources().getColor(R.color.grey));
        guide.setText("Tap to begin countdown.");
        millisLeft=15000L;
    }
   Runnable ctDown=new Runnable() {
       @Override
       public void run() {
           millisLeft=15000-(SystemClock.uptimeMillis()-startTime);
           msecs = (int)millisLeft;
           secs = msecs / 1000;
           mins = secs / 60;
           secs = secs % 60;
           msecs=msecs%1000;
           msecs = msecs / 10;
           cdTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%02d", msecs));
           if(millisLeft<3000)
           {
               beep.start();
               guide.setText("Countdown about to end. Tap when ready.");
           }
           if (millisLeft<0) {
               cdTime.setText("Over !");
               beep.stop();
               try {
                   beep.prepare();
               } catch (IOException e) {
                   Log.d("Tag","Error detected");
               }
               stopCountDown();
           } else
               handler.postDelayed(ctDown, 0);
       }

   };
   Runnable solveTimer=new Runnable() {
       @Override
       public void run() {
           msecs = (int)(SystemClock.uptimeMillis()-startTime);
           secs = msecs / 1000;
           mins = secs / 60;
           secs = secs % 60;
           msecs=msecs%1000;
           msecs = msecs / 10;
           solveTime.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%02d", msecs));
           handler.postDelayed(solveTimer,0);
       }
   };
}
