package com.example.karaens.rubtix;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    RelativeLayout screen;
    TextView cdTime;
    int flag=0;
    int msecs,secs,mins=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        cdTime=findViewById(R.id.cdTime);
        screen=findViewById(R.id.screen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                switch (flag)
                {
                    case 1:

                }
            }
        });
    }
    CountDownTimer inspectionTime=new CountDownTimer(15000,0) {
        @Override
        public void onTick(long millisLeft) {
            secs=(int)millisLeft/1000;
            mins=secs/60;
            secs=secs%60;
            msecs=(int) millisLeft/10;
            cdTime.setText(String.format("%02d",mins)+":"+String.format("%02d",secs)+":"+String.format("%02d",msecs));
            }

        @Override
        public void onFinish() {

        }
    };
}
