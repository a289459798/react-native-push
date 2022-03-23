package com.reactlibrary.push;

import android.content.Context;
import android.util.Log;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.util.VivoPushException;

public class Vivo extends BasePush implements IPushActionListener {
    public Vivo(Context context) {
        super(context);
    }

    @Override
    public void init() {
        try {
            PushClient.getInstance(context).initialize();
            PushClient.getInstance(context).turnOnPush(this);
        } catch (VivoPushException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(String appId, String appKey) {
        this.init();
    }

    @Override
    public void setAlias(String alias) {
        PushClient.getInstance(context).bindAlias(alias, this);
    }

    @Override
    public void unsetAlias(String alias) {
        PushClient.getInstance(context).unBindAlias(alias, this);
    }

    @Override
    public void setTag(String tag) {
        PushClient.getInstance(context).setTopic(tag, this);
    }

    @Override
    public void unsetTag(String tag) {
        PushClient.getInstance(context).delTopic(tag, this);
    }

    @Override
    public void onStateChanged(int i) {
        Log.d(TAG, "onStateChanged:" + i);
    }
}
