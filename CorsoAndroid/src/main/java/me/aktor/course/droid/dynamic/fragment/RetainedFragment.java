package me.aktor.course.droid.dynamic.fragment;

import android.os.Bundle;

/**
 * Created by eto on 04/12/13.
 */
public class RetainedFragment extends PlaceholderFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}
