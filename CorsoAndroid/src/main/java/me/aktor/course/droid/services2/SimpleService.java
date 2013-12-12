package me.aktor.course.droid.services2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.appcompat.R;

/**
 * Created by eto on 11/12/13.
 */
public class SimpleService extends Service {
    private final static String NOTIFICATION_MESSAGE = "extra_message";
    private final static int NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public static void startSimpleService(Context context,String message) {
        Intent intent = new Intent(context,SimpleService.class);
        intent.putExtra(NOTIFICATION_MESSAGE,message);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String message  = intent.getStringExtra(NOTIFICATION_MESSAGE);
        notifyUser(message);
        stopSelf(startId);
        return START_NOT_STICKY;
    }

    private void notifyUser(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder
                .setContentTitle(message)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .build();

        mNotificationManager.notify(NOTIFICATION_ID,notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
