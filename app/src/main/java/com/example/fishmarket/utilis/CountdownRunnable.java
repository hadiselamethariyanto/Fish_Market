package com.example.fishmarket.utilis;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;


public class CountdownRunnable implements Runnable {

    public long millisUntilFinished;
    public TextView holder;
    Handler handler;
    public Context context;

    public CountdownRunnable(Handler handler, TextView holder, long millisUntilFinished, Context context) {
        this.handler = handler;
        this.holder = holder;
        this.millisUntilFinished = millisUntilFinished;
        this.context = context;
    }

    @Override
    public void run() {
        long seconds = millisUntilFinished / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;


        String time;
        if (hours > 0) {
            time = hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
        } else {
            time = minutes % 60 + ":" + seconds % 60;
        }
        holder.setText(time);

        millisUntilFinished += 1000;

        handler.postDelayed(this, 1000);
    }

}