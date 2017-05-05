package com.fsw.chat_ui.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.fsw.chat_ui.ProcessConnection;

/**
 * Created by Admin on 2017/4/11.
 */

public class MessageService extends Service {

    private final int MessageId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("fsw--", "等待接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(MessageId, new Notification());
        //绑定建立服务
        bindService(new Intent(this, MessageGuardService.class), sc, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }


    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接上
            Log.e("fsw--sc--msg", "连接上");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开链接时，重新启动MessageService,重新绑定
            startService(new Intent(MessageService.this, MessageGuardService.class));
            //绑定建立服务
            bindService(new Intent(MessageService.this, MessageGuardService.class), sc, Context.BIND_IMPORTANT);
        }
    };

}
