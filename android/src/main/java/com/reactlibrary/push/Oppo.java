package com.reactlibrary.push;

import android.content.Context;
import android.util.Log;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.reactlibrary.PushHelper;

public class Oppo extends BasePush implements ICallBackResultService {
    public Oppo(Context context) {
        super(context);
    }

    @Override
    public void init() {
        try {
            HeytapPushManager.init(context, true);
            HeytapPushManager.register(context, String.valueOf(PushHelper.getConfigValue(context, "com.oppo.push.app_key")), String.valueOf(PushHelper.getConfigValue(context, "com.oppo.push.app_secret")), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(String appId, String appKey) {
        this.init();
    }

    @Override
    public String setAlias(String alias) {
        return HeytapPushManager.getRegisterID();
    }

    @Override
    public String unsetAlias(String alias) {
        return HeytapPushManager.getRegisterID();
    }

    @Override
    public String setTag(String tag) {
        return HeytapPushManager.getRegisterID();
    }

    @Override
    public String unsetTag(String tag) {
        return HeytapPushManager.getRegisterID();
    }

    @Override
    public void onRegister(int i, String s) {
        Log.d(TAG, "注册成功" + s);
    }

    @Override
    public void onUnRegister(int i) {

    }

    @Override
    public void onSetPushTime(int i, String s) {

    }

    @Override
    public void onGetPushStatus(int i, int i1) {

    }

    @Override
    public void onGetNotificationStatus(int i, int i1) {

    }

    @Override
    public void onError(int i, String s) {

    }
}
