package me.aktor.course.droid;

import android.content.Context;

/**
 * Created by eto on 04/12/13.
 */
public class CapabilityDetector {
    private final Context fContext;
    private volatile static CapabilityDetector sInstance;
    private final static Object sLOCK  = new Object();

    CapabilityDetector(Context context){
        fContext = context;
    }

    public static CapabilityDetector get(){
        return from(MyApp.app().getApplicationContext());
    }

    public static CapabilityDetector from(Context context){
        if(sInstance==null){
            synchronized (sLOCK){
                if(sInstance==null){
                    sInstance = new CapabilityDetector(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }


    public boolean isTablet(){
        return fContext.getResources().getBoolean(R.bool.is_tablet);
    }
}
