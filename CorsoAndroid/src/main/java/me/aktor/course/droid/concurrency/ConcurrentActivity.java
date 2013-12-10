package me.aktor.course.droid.concurrency;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class ConcurrentActivity extends FragmentActivity
        implements ConcurrentControlFragment.OnStartTask,
        ConcurrentBridge.OnMessageListener{

    private final static String BRIDGE_TAG = "BRIDGE";

    ConcurrentBridge mBridge;
    ConcurrentControlFragment mControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concurrent_activity);
        if (savedInstanceState==null){
//           getSupportFragmentManager()
//                   .beginTransaction()
//                   .add(new ConcurrentBridge(),BRIDGE_TAG)
//                   .commit();
        }

        mControl = (ConcurrentControlFragment)
                    getSupportFragmentManager().findFragmentById(R.id.ConcurrentControl);
        mControl.setListener(this);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ConcurrentBridge){
            mBridge = (ConcurrentBridge)fragment;
        }
    }

    @Override
    public void startTask(long delay) {

//        mBridge.startTask(delay);
        DelayedRandomGenerator.startWithDelay(new DelayedRandomGenerator.OnComplete() {
            @Override
            public void onMessage(String message) {
                mControl.appendLog(message);
            }
        },delay,false);

    }


    @Override
    public void onMessageReceived(String message) {
        mControl.appendLog(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
