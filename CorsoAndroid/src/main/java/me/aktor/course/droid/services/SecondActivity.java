package me.aktor.course.droid.services;

import android.os.Bundle;
import android.view.View;

import me.aktor.course.droid.R;

/**
 * Created by eto on 11/12/13.
 */
public class SecondActivity extends FirstBoundActivity {

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        findViewById(R.id.start_bkg_service).setVisibility(View.INVISIBLE);
        findViewById(R.id.next).setVisibility(View.INVISIBLE);
    }
}
