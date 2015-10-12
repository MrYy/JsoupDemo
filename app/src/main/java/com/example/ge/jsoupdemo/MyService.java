package com.example.ge.jsoupdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by ge on 2015/10/12.
 */
public class MyService extends Service {
    private Handler handler=new Handler() {
        @Override
        public void publish(LogRecord record) {

        }

        @Override
        public void flush() {

        }

        @Override
        public void close() {

        }

    };
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
