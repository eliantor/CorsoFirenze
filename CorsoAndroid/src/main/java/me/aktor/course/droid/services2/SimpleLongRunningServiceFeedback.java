package me.aktor.course.droid.services2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import me.aktor.course.droid.util.StickyReceiver;

/**
 * Created by eto on 11/12/13.
 */
public class SimpleLongRunningServiceFeedback extends IntentService {
    private final static int NOTIFICATION_ID = 2;

    private final static String SLEEP_TIME = "sleep_time";
    private final static String RADIO_STATION = "radio_station";
    private final static String RESULT_RECEIVER = "receiver";
    public final static String MESSAGE_KEY = "message_key";

    private NotificationManager mNotificationManager;

    public static void startLongRunning(Context context,long time,String radioStation,ResultReceiver receiver){
        Intent intent = new Intent(context,SimpleLongRunningServiceFeedback.class);
        intent.putExtra(SLEEP_TIME,time);
        intent.putExtra(RADIO_STATION,radioStation);
        intent.putExtra(RESULT_RECEIVER,receiver);
        context.startService(intent);

    }

    public SimpleLongRunningServiceFeedback() {
        super(SimpleLongRunningServiceFeedback.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        long sleepTime = intent.getLongExtra(SLEEP_TIME,2000);
        String station = intent.getStringExtra(RADIO_STATION);
        notifyBroadcast(station,"Going to sleep");
        notifyReceiver(intent,"Going to sleep2");
        LongRun.sleep(sleepTime);
        notifyBroadcast(station, "Done");
        notifyReceiver(intent,"Done2");
    }

    private void notifyBroadcast(String station,String message){
        if (station==null || station.length()==0) return;
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        Intent intentMessage = new Intent(station);
        intentMessage.putExtra(MESSAGE_KEY,message);
        bm.sendBroadcast(intentMessage);
    }

    private void notifyReceiver(Intent intent,String message){
        Bundle b = new Bundle();
        b.putString(MESSAGE_KEY,message);
        StickyReceiver.notifyEvent(intent,RESULT_RECEIVER,1,b);
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
