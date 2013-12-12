package me.aktor.course.droid.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;

/**
* Created by eto on 11/12/13.
*/
public class FeedbackReceiver extends ResultReceiver {

    public static interface Receiver{
        public void onReceiveResult(int requestId, int resultCode, String action, Bundle data);
    }

    private Receiver mReceiver;

    private boolean mSticky = false;
    private Bundle mLastReceivedData = null;
    private int mLastReceivedCode = 0;

    public FeedbackReceiver(){
        super(new Handler(Looper.getMainLooper()));
    }
    public FeedbackReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver){
        mReceiver = receiver;
        if(mReceiver !=null && mSticky == true){
            onReceiveResult(mLastReceivedCode, mLastReceivedData);
        }
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mReceiver!=null){
            mSticky = false;
            mLastReceivedCode=0;
            mLastReceivedData=null;
            int requestId = resultData.getInt(Events.REQUEST_ID, Events.NO_REQUEST_ID);
            String action = resultData.getString(Events.ACTION);
            resultData.remove(Events.REQUEST_ID);
            resultData.remove(Events.ACTION);
            mReceiver.onReceiveResult(requestId, resultCode, action, resultData);
        }else{
            mSticky = true;
            mLastReceivedCode=resultCode;
            mLastReceivedData=resultData;
        }
    }

    public static void notifyEvent(Intent intent,int resultCode){
        notifyEvent(intent, resultCode,null);
    }

    public static void notifyEvent(Intent intent,int code,Bundle data) {
        ResultReceiver receiver = intent.getParcelableExtra(Events.RECEIVER);
        if(receiver!=null){
            final Bundle resultData = new Bundle();
            resultData.putString(Events.ACTION, intent.getStringExtra(Events.ACTION));
            resultData.putInt(Events.REQUEST_ID, intent.getIntExtra(Events.REQUEST_ID, Events.NO_REQUEST_ID));
            if(data!=null)resultData.putAll(data);
            receiver.send(code, resultData);
        }
    }


}
