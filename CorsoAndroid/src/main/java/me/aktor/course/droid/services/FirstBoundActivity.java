package me.aktor.course.droid.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import me.aktor.course.droid.R;

/**
 * Created by eto on 10/12/13.
 */
public class FirstBoundActivity extends Activity {

    private boolean mIsBound;
    private MyIntentService.ServiceBinder mServiceBinder;
    private int mCurrentState;
    private TextView mTvOut;

    private final View.OnClickListener fClick =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.start_bkg_service:
                            doStartService();
                            break;
                        case R.id.next:
                            doNextActivity();
                            break;
                    }
                }
            };

    private final StatusServiceConnection mConnection =
            new StatusServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mServiceBinder = (MyIntentService.ServiceBinder) service;
                    mServiceBinder.addOnStatusChangeListener(this);
                    changeStatus(mServiceBinder.getState());
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mServiceBinder.removeOnStatusChangeListener(this);
                    mServiceBinder =  null;
                }

                @Override
                public void onStatusChange(int status) {
                    changeStatus(status);
                }
            };


    private void changeStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCurrentState = status;
                mTvOut.setText("Status is "+status);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bound_activity);
        mTvOut = (TextView)findViewById(R.id.tv_feedback);
        findViewById(R.id.start_bkg_service).setOnClickListener(fClick);
        findViewById(R.id.next).setOnClickListener(fClick);

    }


    protected void doStartService(){
        MyIntentService.startSleep(this,5000);
    }

    protected void doNextActivity(){
        startActivity(new Intent(FirstBoundActivity.this,SecondActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        doBindService();
    }

    private void doBindService(){
        bindService(new Intent(this,MyIntentService.class),mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        doUnbindService();
    }

    private void doUnbindService(){
        if (mIsBound){
            unbindService(mConnection);
            mIsBound = false;
        }
    }


}
