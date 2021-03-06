// ReactNativePushModule.java

package com.jjpush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.*;
import com.heytap.msp.push.HeytapPushManager;
import com.jjpush.push.*;
import com.vivo.push.PushClient;

public class RNJJPushModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private BasePush push;

    public RNJJPushModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNJJPush";
    }

    @ReactMethod
    public void init(String appkey, String secret, ReadableMap readableMap) {
        try {
            String brand = SystemUtil.getDeviceBrand();
           if (brand.toUpperCase().startsWith("HUAWEI") && (readableMap == null || !readableMap.getBoolean("hms"))) {
               push = new Hms(reactContext);
           } else if ((brand.toUpperCase().startsWith("OPPO") || brand.toUpperCase().startsWith("一加") || brand.toUpperCase().startsWith("REALME")) && (readableMap == null || !readableMap.getBoolean("oppo"))) {
                push = new Oppo(reactContext);
           } else if (brand.toUpperCase().startsWith("VIVO") && (readableMap == null || !readableMap.getBoolean("vivo")) && PushClient.getInstance(reactContext).isSupport()) {
               push = new Vivo(reactContext);
           } else {
               // 其他走小米推送
               push = new XM(reactContext);
           }
            push.init(appkey, secret);

            HeytapPushManager.requestNotificationPermission();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void setAlias(String alias, final Promise promise) {
        try {
            String regId = push.setAlias(alias);
            if (!TextUtils.isEmpty(regId)) {
                if (promise != null) {
                    promise.resolve(regId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unsetAlias(String alias, final Promise promise) {
        try {
            String regId = push.unsetAlias(alias);
            if (!TextUtils.isEmpty(regId)) {
                if (promise != null) {
                    promise.resolve(regId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void setTag(String tag, final Promise promise) {
        try {
            String regId = push.setTag(tag);
            if (!TextUtils.isEmpty(regId)) {
                if (promise != null) {
                    promise.resolve(regId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unsetTag(String tag, final Promise promise) {
        try {
            String regId = push.unsetTag(tag);
            if (!TextUtils.isEmpty(regId)) {
                if (promise != null) {
                    promise.resolve(regId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void notify(String title, String body) {
        int id = reactContext.getResources().getIdentifier("ic_launcher", "mipmap", reactContext.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(reactContext, 0, new Intent(),
                PendingIntent.FLAG_ONE_SHOT);
        final NotificationManager notificationManager = (NotificationManager) reactContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel("ID", "NAME", importance);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(reactContext, "ID");
        mBuilder.setSmallIcon(id);
        mBuilder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(body);
        mBuilder.setContentIntent(pendingIntent);
        final Notification notify = mBuilder.build();

        //随机id 1000-2000
        final int notifyId = (int) (Math.random() * 1000 + 1000);
        if (notificationManager != null) {
            notificationManager.notify(notifyId, notify);
        }
    }
}
