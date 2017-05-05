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
 * Created by fsw on 2017/4/11.
 * Version 1.0
 * Use MessageService守护进程服务
 */

public class MessageGuardService extends Service {


    private final int GuardId = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(GuardId, new Notification());
        //绑定建立服务
        bindService(new Intent(this, MessageService.class), sc, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接上
            Log.e("fsw--sc--guard", "连接上");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开链接时，重新启动MessageService,重新绑定
            startService(new Intent(MessageGuardService.this, MessageService.class));
            //绑定建立服务
            bindService(new Intent(MessageGuardService.this, MessageService.class), sc, Context.BIND_IMPORTANT);
        }
    };

}
