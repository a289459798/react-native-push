package com.reactlibrary.service;

import android.content.Context;
import android.util.Log;
import com.heytap.msp.push.mode.DataMessage;
import com.heytap.msp.push.service.CompatibleDataMessageCallbackService;
import com.reactlibrary.NotificationUtil;

public class OppoCompatibleDataMessageCallbackService extends CompatibleDataMessageCallbackService {
    @Override
    public void processMessage(Context context, DataMessage message) {
        super.processMessage(context, message);

        String content = message.getContent();
        Log.d("jjpush", "processMessage  message" +message.toString());
        NotificationUtil.showNotification(context,message.getTitle(),message.getContent(),message.getNotifyID(), true);
    }
}
