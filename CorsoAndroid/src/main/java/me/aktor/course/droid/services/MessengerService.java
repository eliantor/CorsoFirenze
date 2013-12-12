package me.aktor.course.droid.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;

/**
 * Created by eto on 11/12/13.
 */
public class MessengerService extends Service {
    private Messenger mMessenger;
    @Override
    public void onCreate() {
        super.onCreate();
        mMessenger = new Messenger(new MessageHandler());
    }

    private class MessageHandler extends Handler {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


}
