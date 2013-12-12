package me.aktor.course.droid.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.ResultReceiver;

/**
 * Created by eto on 11/12/13.
 */
public class StickyReceiver extends ResultReceiver {

    public static interface Receiver {
        public void onReceiveResult(int code,Bundle data);

    }

    private Receiver mReceiver;
    private boolean mHasPending;
    private Bundle mLastResult;
    private int mLastCode;

    public StickyReceiver() {
        super(new Handler(Looper.getMainLooper()));
    }

    public void setReceiver(Receiver receiver){
        mReceiver = receiver;
        if (mReceiver!=null && mHasPending){
            mHasPending = false;
            mReceiver.onReceiveResult(mLastCode,mLastResult);
            mLastCode = 0;
            mLastResult = null;
        }
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver!=null){
            mReceiver.onReceiveResult(resultCode,resultData);
        } else {
            mLastCode = resultCode;
            mLastResult = resultData;
            mHasPending = true;

        }
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

    }

    public static void notifyEvent(Intent intent,String key,int code,Bundle data){
        ResultReceiver rec = intent.getParcelableExtra(key);
        if (rec!=null){
            rec.send(code,data);
        }
    }

    public static Creator<StickyReceiver> CREATOR = new Creator<StickyReceiver>() {
        @Override
        public StickyReceiver createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public StickyReceiver[] newArray(int size) {
            return new StickyReceiver[0];
        }
    };
}
