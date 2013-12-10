package me.aktor.course.droid.tablet.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;

import me.aktor.course.droid.CapabilityDetector;
import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class DetailActivity extends FragmentActivity {

    public final static String ITEM = "ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i =getIntent();
        int item =i.getIntExtra(ITEM,-1);
        setContentView(R.layout.detail_activity);
        DetailFragment frag = (DetailFragment)getSupportFragmentManager().findFragmentById(R.id.Detail);
        frag.show(item);

        // salva lo stato del fragment
        //Fragment.SavedState state =getSupportFragmentManager().saveFragmentInstanceState(frag);

        // restore dello stato tra istanze diverse
        //frag.setInitialSavedState(state);


        CapabilityDetector.from(this).isTablet();
    }

}
