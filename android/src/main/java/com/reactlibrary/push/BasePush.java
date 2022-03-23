package com.reactlibrary.push;

import android.content.Context;
import com.facebook.react.bridge.ReactApplicationContext;
import com.reactlibrary.IPush;

public abstract class BasePush implements IPush {
    public Context context;

    public static String TAG = "jjpush";

    public BasePush(Context context) {
        this.context = context;
    }
}
