package com.reactlibrary.service;

import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.reactlibrary.PushHelper;
import com.reactlibrary.RNJJPushModule;
import com.reactlibrary.RNJJPushPackage;

public class HmsService extends HmsMessageService {
    public static String TAG = "jjpushservice";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived is called");

        // 判断消息是否为空
        if (remoteMessage == null) {
            Log.e(TAG, "Received message entity is null!");
            return;
        }

        RNJJPushPackage.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("jjpush_notify", remoteMessage.getData());

        // 获取消息内容
        Log.i(TAG, "get Data: " + remoteMessage.getData());
    }
}
