// ReactNativePushModule.java

package com.reactlibrary;

import android.util.Log;
import com.facebook.react.bridge.*;
import com.reactlibrary.push.BasePush;
import com.reactlibrary.push.Hms;
import com.reactlibrary.push.XM;

import java.util.Locale;

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
    public void init(ReadableMap readableMap) {
        try {
            String brand = SystemUtil.getDeviceBrand();
            Log.d("jjpush", SystemUtil.getDeviceBrand());
            Log.d("jjpush", SystemUtil.getSystemModel());
            Log.d("jjpush", SystemUtil.getSystemVersion());
            if (brand.toUpperCase(Locale.ROOT).startsWith("HUAWEI")) {
                push = new Hms(reactContext);
            } else {
                push = new XM(reactContext);
            }

            push.init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void setAlias(String alias) {
        try {
            push.setAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unsetAlias(String alias) {
        try {
            push.unsetAlias(alias);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void setTag(String tag) {
        try {
            push.setTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void unsetTag(String tag) {
        try {
            push.unsetTag(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
