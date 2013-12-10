package me.aktor.course.droid.platform.dependent;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import me.aktor.course.droid.R;

/**
 * Created by Andrea Tortorella on 10/12/13.
 */
public class ConfigurationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res  = getResources();
        Configuration config = res.getConfiguration();
        DisplayMetrics display = res.getDisplayMetrics();
        float density =display.density;

    }
}
