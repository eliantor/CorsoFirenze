package me.aktor.course.droid.handlers;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import me.aktor.course.droid.R;

/**
 * Created by eto on 11/12/13.
 */
public class HandlerActivity extends Activity {
    private final static int TOGGLE_EVENT = 1;

    private CompoundButton mCheckBox;
    private Handler mMyHandler;
    private MyCustomHandler mMyCustomHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_activity);
        findViewById(R.id.check_delayed).setOnClickListener(mCustomClickListener);
        mCheckBox = (CompoundButton) findViewById(R.id.auto_check);
        mMyHandler = new Handler();
        mMyCustomHandler = new MyCustomHandler(this);
    }

    private static class MyCustomHandler extends Handler{
        HandlerActivity activity;

        MyCustomHandler(HandlerActivity activity){
            super();
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TOGGLE_EVENT:
                    if (activity!=null){
                        Toast.makeText(activity,(String)msg.obj,Toast.LENGTH_LONG).show();
                        activity.mCheckBox.toggle();
                    }
            }
        }

        public void detach() {
            activity = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyHandler.removeCallbacks(fAction);
        mMyCustomHandler.detach();
        mMyCustomHandler.removeMessages(TOGGLE_EVENT);
    }

    private final View.OnClickListener mCustomClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message m  = mMyCustomHandler.obtainMessage(TOGGLE_EVENT,"ciao "+Math.random());
                    mMyCustomHandler.sendMessageDelayed(m,2000);

                    //mMyCustomHandler.sendEmptyMessageDelayed(TOGGLE_EVENT,2000);
                }
            };

    private final View.OnClickListener mClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyHandler.postDelayed(fAction,1000);
                }
            };

    private final Runnable fAction = new Runnable() {
        @Override
        public void run() {
            mCheckBox.toggle();
        }
    };
}
