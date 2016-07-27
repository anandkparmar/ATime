package com.anandparmar.atime;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * VARIABLES:
     *
     */
    TextView timeTextView;
    Button btnStart, btnReset;

    int state = 0;  // 0 - (start->pause), 1 - (pause->start)

    long startTime = 0L;
    long timeInMilliSeconds = 0L;
    long updatedTime = 0L;
    long swapTime = 0L;

    int minutes = 0;
    int seconds = 0;
    int milliSeconds = 0;

    Handler myHandler = new Handler();

    /**
     *
     * @param savedInstanceState
     *
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = (TextView) findViewById(R.id.timeTextView);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(state==0){
                    btnStart.setText("Pause");
                    startTime = SystemClock.uptimeMillis();
                    myHandler.postDelayed(timerUpdate, 0);
                    state = 1;
                    timeTextView.setTextColor(Color.parseColor("#000000"));
                }
                else{
                    btnStart.setText("Start");
                    swapTime = swapTime + timeInMilliSeconds;
                    myHandler.removeCallbacks(timerUpdate);
                    state = 0;
                    timeTextView.setTextColor(Color.parseColor("#B6B6B6"));
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // if timer is already running
                if(state == 1){
                    btnStart.setText("Start");
                    myHandler.removeCallbacks(timerUpdate);
                    state = 0;
                    timeTextView.setTextColor(Color.parseColor("#B6B6B6"));
                }
                startTime = 0L;
                timeInMilliSeconds = 0L;
                updatedTime = 0L;
                swapTime = 0L;

                minutes = 0;
                seconds = 0;
                milliSeconds = 0;

                timeTextView.setText("00:00:000");
                btnStart.setText("Start");
                state = 0;
            }
        });

    }


    /**
     *
     */
    public Runnable timerUpdate = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = swapTime + timeInMilliSeconds;
            seconds = (int) updatedTime/1000;
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) updatedTime % 1000;
            timeTextView.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliSeconds) );
            myHandler.postDelayed(this, 0);
        }
    };

}
