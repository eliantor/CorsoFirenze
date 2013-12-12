package me.aktor.course.droid.services;

import android.content.ServiceConnection;

/**
 * Created by eto on 10/12/13.
 */
interface StatusServiceConnection extends ServiceConnection,
        MyIntentService.OnStatusChangeListener{

}
