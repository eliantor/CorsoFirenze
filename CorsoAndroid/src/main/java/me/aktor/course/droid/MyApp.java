package me.aktor.course.droid;

import android.app.Application;
import android.os.AsyncTask;

/**
 * Created by eto on 04/12/13.
 */
public class MyApp extends Application {

    private static MyApp sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        try {
            Class.forName(AsyncTask.class.getName());
        } catch (ClassNotFoundException e) {
        }
    }

    public static MyApp app(){
        return sApp;
    }
}
