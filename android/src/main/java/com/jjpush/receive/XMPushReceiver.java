package com.jjpush.receive;

import android.content.Context;
import android.content.Intent;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.gson.Gson;
import com.jjpush.RNJJPushPackage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

public class XMPushReceiver extends PushMessageReceiver {

    /**
     * 接受服务端发送过来的透传消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);

        sendListener("jjpush_notify", miPushMessage);
    }

    /**
     * 监听用户点击通知栏消息
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if(RNJJPushPackage.reactContext == null) {
            String packageName = context.getApplicationContext().getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(launchIntent);
            return;
        }
        sendListener("jjpush_click", miPushMessage);

        if(RNJJPushPackage.reactContext.getCurrentActivity() != null) {

            intent.setClass(context, RNJJPushPackage.reactContext.getCurrentActivity().getClass());
            context.startActivity(intent);
        } else {

            String packageName = context.getApplicationContext().getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(launchIntent);
        }

    }

    /**
     * 消息达到客户端触发
     * @param context
     * @param miPushMessage
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);

        sendListener("jjpush_notify", miPushMessage);
    }

    private void sendListener(String type, MiPushMessage miPushMessage) {
        Gson gson = new Gson();
        String json = gson.toJson(miPushMessage.getExtra());

        RNJJPushPackage.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(type, json);
    }

}
