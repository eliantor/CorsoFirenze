package me.aktor.course.droid.concurrency;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eto on 04/12/13.
 */
public class ConcurrentBridge extends Fragment implements DelayedRandomGenerator.OnComplete{

    DelayedRandomGenerator mRandomGen;
    private OnMessageListener mMessageListener;
    private boolean mReady;
    private List<String> mPending;

    public void insertNote(ContentValues v) {

    }


    public  static interface OnMessageListener{
        public void onMessageReceived(String message);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnMessageListener){
            mMessageListener = (OnMessageListener)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMessageListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPending = new ArrayList<String>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRandomGen!=null){
            mRandomGen.cancel(true);
        }
        mPending = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mReady = true;
        deliverPending();
    }

    private void deliverPending() {
        if (mPending.size()>0){
            for (String message:mPending){
                mMessageListener.onMessageReceived(message);
            }
            mPending.clear();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mReady = false;
    }

    public void startTask(long delay) {
        mRandomGen = DelayedRandomGenerator.startWithDelay(this,delay,false);
    }

    @Override
    public void onMessage(String message) {
        if (mReady){
            mMessageListener.onMessageReceived(message);
        } else {
            mPending.add(message);
        }
    }
}
