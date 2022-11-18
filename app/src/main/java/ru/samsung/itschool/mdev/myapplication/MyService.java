package ru.samsung.itschool.mdev.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    Timer timer = new Timer();
    MyTimerTask timerTask;
    ResultReceiver resultReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        resultReceiver = intent.getParcelableExtra("receiver");
        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        Bundle bundle = new Bundle();
        bundle.putString("end", "Timer stopped...");
        resultReceiver.send(200, bundle);
    }

    class MyTimerTask extends TimerTask {
        public MyTimerTask() {
            Bundle bundle = new Bundle();
            bundle.putString("start", "Timer started...");
            resultReceiver.send(100, bundle);
        }
        @Override
        public void run() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("s");
            resultReceiver.send(Integer.parseInt(dateFormat.format(System.currentTimeMillis())), null);
        }
    }
}
