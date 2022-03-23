package com.reactlibrary.push;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.reactlibrary.PushHelper;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class XM extends BasePush{
    public XM(Context context) {
        super(context);
    }

    @Override
    public void init() {
        try {
            if (shouldInit(context)) {
                MiPushClient.registerPush(context, String.valueOf(PushHelper.getConfigValue(context, "com.xm.push.appid")), String.valueOf(PushHelper.getConfigValue(context, "com.xm.push.appkey")));
            }
            //打开Log
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    Log.d(TAG, content, t);
                }
                @Override
                public void log(String content) {
                    Log.d(TAG, content);
                }
            };
            Logger.setLogger(context, newLogger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(String appId, String appKey) {
        this.init();
    }

    @Override
    public void setAlias(String alias) {
        MiPushClient.setAlias(context, alias, null);
    }

    @Override
    public void unsetAlias(String alias) {
        MiPushClient.unsetAlias(context, alias, null);
    }

    @Override
    public void setTag(String tag) {
        MiPushClient.subscribe(context, tag, null);
    }

    @Override
    public void unsetTag(String tag) {
        MiPushClient.unsubscribe(context, tag, null);
    }

    private boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
