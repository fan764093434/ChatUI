package com.fsw.chat_ui.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.List;

/**
 * Created by Admin on 2017/4/11.
 * android 5.0以上才有
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WakeUpService extends JobService {

    private final int wakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启一个轮询
        JobInfo.Builder jobBuilder = new JobInfo.Builder(wakeUpId, new ComponentName(this, WakeUpService.class));
        jobBuilder.setPeriodic(10000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuilder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务，定时轮询，看MessageService有没有被杀死
        //如果杀死则启动服务
        boolean isMessageServiceAlive = serviceAlive(MessageService.class.getName());
        if (!isMessageServiceAlive) {
            startService(new Intent(this, MessageService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断服务是否存活
     *
     * @param serviceName 是包名+服务的类名(例如：com.fsw.chat_ui.service.MessageService)
     * @return true代表服务还在运行 false则代表服务已结束
     */
    private boolean serviceAlive(String serviceName) {
        boolean isAlive = false;
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        if (infos.size() < 1) {
            return false;
        }
        for (int i = 0; i < infos.size(); i++) {
            String className = infos.get(i).service.getClassName().toString();
            if (serviceName.equals(className)) {
                isAlive = true;
                break;
            }
        }
        return isAlive;
    }

}
