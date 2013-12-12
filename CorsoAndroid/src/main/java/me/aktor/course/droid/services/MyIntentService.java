package me.aktor.course.droid.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyIntentService extends IntentService {
    private final static String SLEEP_ARG = "sleep";

    public final static int STATUS_IDLE = 0;
    public final static int STATUS_STARTING = 1;
    public final static int STATUS_MIDDLE = 2;
    public final static int STATUS_TERMINATING =3;
    public final static int STATUS_RESET = 4;


    private final ServiceBinder mBinder = new ServiceBinder();

    private final AtomicInteger mStatus = new AtomicInteger();

    private final ConcurrentHashMap<OnStatusChangeListener,OnStatusChangeListener> mListeners =
            new ConcurrentHashMap<OnStatusChangeListener, OnStatusChangeListener>();

    public MyIntentService() {
        super(MyIntentService.class.getSimpleName());
    }

    public static interface OnStatusChangeListener{
        public void onStatusChange(int status);

    }


    public class ServiceBinder extends Binder{
        public void addOnStatusChangeListener(OnStatusChangeListener listener){
            mListeners.putIfAbsent(listener,listener);
        }

        public int getState(){
            return mStatus.get();
        }

        public void removeOnStatusChangeListener(OnStatusChangeListener listener){
            mListeners.remove(listener);
        }
    }

    public static void startSleep(Context context,long millis){
        context.startService(new Intent(context,MyIntentService.class)
        .putExtra(SLEEP_ARG,millis));
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mStatus.set(STATUS_IDLE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long time = intent.getLongExtra(SLEEP_ARG,10000);
        notifyState(STATUS_STARTING);
        sleepUntilInterrupted(time);
        notifyState(STATUS_MIDDLE);
        sleepUntilInterrupted(time);
        notifyState(STATUS_RESET);
    }

    private void notifyState(int status) {
        mStatus.set(status);
        for (OnStatusChangeListener listener:mListeners.keySet()){
            listener.onStatusChange(status);
        }
    }

    private void sleepUntilInterrupted(long time) {
        try {
            Thread.sleep(time);
        }catch (InterruptedException e){
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notifyState(STATUS_TERMINATING);
    }


}
