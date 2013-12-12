package me.aktor.course.droid.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

public class Events {
	public final static int INVALID_NOTIFICATION=-1;
	public final static int NOTIFICATION_OK=0;
	public final static int NOTIFICATION_OFFLINE = 1;
	public final static int NOTIFICATION_NETWORK_ERROR=2;
	public final static int NOTIFICATION_NO_CONTENT=3;
	public final static int NOTIFICATION_UNAUTHORIZED = 4;
	public final static int NOTIFICATION_FAILURE = 5;

	
	public final static String EVENT_KEY="event";
	public final static String ACTION_KEY="action_key";
	public final static String PARAM_BUNDLE_KEY="params_bundle";
	public final static String NOTIFY_TO_KEY ="notify_to";
	
	public final static String REQUEST_ID="request_id";
	public static final String RECEIVER = "receiver";
	public static final String ACTION = "action";
	public final static int NO_REQUEST_ID = 0;

    public static void broadcastLocally(Context context,Intent source,int event){
		final String address = source.getStringExtra(NOTIFY_TO_KEY);
		if(address==null) return;
		Intent intent = new Intent(address);
		intent.putExtra(REQUEST_ID, source.getIntExtra(REQUEST_ID, NO_REQUEST_ID));
		intent.putExtra(EVENT_KEY, event);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
	
}
