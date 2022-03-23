package com.reactlibrary.push;

import android.content.Context;
import android.util.Log;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;

public class Oppo extends BasePush implements ICallBackResultService {
    public Oppo(Context context) {
        super(context);
    }

    @Override
    public void init() {
        HeytapPushManager.init(context, true);
        HeytapPushManager.register(context, "", "", this);
    }

    @Override
    public void init(String appId, String appKey) {
        this.init();
    }

    @Override
    public void setAlias(String alias) {

    }

    @Override
    public void unsetAlias(String alias) {

    }

    @Override
    public void setTag(String tag) {

    }

    @Override
    public void unsetTag(String tag) {

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
