package me.aktor.course.droid.services2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by eto on 11/12/13.
 */
public class SimpleLongRunningService extends IntentService {
    private final static int NOTIFICATION_ID = 2;

    private final static String SLEEP_TIME = "sleep_time";

    private NotificationManager mNotificationManager;

    public static void startLongRunning(Context context,long time){
        Intent intent = new Intent(context,SimpleLongRunningService.class);
        intent.putExtra(SLEEP_TIME,time);
        context.startService(intent);

    }

    public SimpleLongRunningService() {
        super(SimpleLongRunningService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        long sleepTime = intent.getLongExtra(SLEEP_TIME,2000);
        notifyUser("I'm going to sleep "+sleepTime);
        LongRun.sleep(sleepTime);
        notifyUser("I slept for "+sleepTime);
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

}
