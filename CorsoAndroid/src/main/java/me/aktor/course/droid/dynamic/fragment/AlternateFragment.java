package me.aktor.course.droid.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.aktor.course.droid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlternateFragment extends Fragment {

    public AlternateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic_alternate, container, false);
        return rootView;
    }
}
