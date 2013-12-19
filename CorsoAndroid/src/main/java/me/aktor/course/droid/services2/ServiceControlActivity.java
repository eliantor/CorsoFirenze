package me.aktor.course.droid.services2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import me.aktor.course.droid.R;
import me.aktor.course.droid.util.StickyReceiver;

/**
 * Created by eto on 11/12/13.
 */
public class ServiceControlActivity extends FragmentActivity
    implements View.OnClickListener, StickyReceiver.Receiver {
    private final static String STATION = "my_station";
    private TextView mLog;

    private Station mStation;


    private final static IntentFilter ADDRESS =
            new IntentFilter(STATION);

    @Override
    public void onReceiveResult(int code, Bundle data) {
        mLog.append(data.getString(SimpleLongRunningServiceFeedback.MESSAGE_KEY));
    }

    private class Station extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mLog.append(intent.getStringExtra(SimpleLongRunningServiceFeedback.MESSAGE_KEY));
        }
    }

    private StickyReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_control_activity);
        findViewById(R.id.start_service).setOnClickListener(this);
        mLog = (TextView)findViewById(R.id.log_view);
        mReceiver = new StickyReceiver();
        if (savedInstanceState != null){
            mReceiver = savedInstanceState.getParcelable("CHIAVE");
        }
    }

    @Override
    public void onClick(View v) {
        SimpleLongRunningServiceFeedback.startLongRunning(this,2000,STATION,mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver.setReceiver(this);

        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        mStation = new Station();
        bm.registerReceiver(mStation,ADDRESS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);

        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(this);
        bm.unregisterReceiver(mStation);
        mStation = null;
    }
}
