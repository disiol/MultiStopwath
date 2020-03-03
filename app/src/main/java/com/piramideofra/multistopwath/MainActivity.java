package com.piramideofra.multistopwath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewShowTime;
    private ImageButton buttonStartStop, buttonCirkle;
    private LinearLayout linearLayout;

    private int seconds = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.time_list);
        textViewShowTime = findViewById(R.id.textViewShowTime);
        buttonStartStop = findViewById(R.id.start_stop_imageButton);
        buttonCirkle = findViewById(R.id.cirkle_imageButton);

        buttonStartStop.setOnClickListener(this);
        buttonCirkle.setOnClickListener(this);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");

        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_stop_imageButton:
                if (!isRunning) {
                    isRunning = true;
                    buttonStartStop.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.picture_frame));

                } else if (isRunning) {
                    isRunning = false;
                    buttonStartStop.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_play));
                    seconds = 0;
                    if (linearLayout.getChildCount() > 0) {
                        linearLayout.removeAllViews();

                    }
                }

                break;
            case R.id.cirkle_imageButton:
                if (isRunning) {
                    TextView textView = new TextView(this);
                    setTime(textView);
                    linearLayout.addView(textView);

                }
                break;
        }
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                setTime(MainActivity.this.textViewShowTime);

                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setTime(TextView textViewShowTime) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

        textViewShowTime.setText(time);
    }
}
