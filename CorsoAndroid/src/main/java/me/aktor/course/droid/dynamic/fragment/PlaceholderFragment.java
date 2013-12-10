package me.aktor.course.droid.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import me.aktor.course.droid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private final static String TAG  = "LOGGING";

    protected String mMessage;

    public PlaceholderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mMessage = args!=null?args.getString("message"):"View";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dynamic_fargment, container, false);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        String message = String.format(Locale.US,
                "I'm %s, %x my message is %s",
                ((Object) this).getClass().getSimpleName(),
                System.identityHashCode(this), mMessage);
        Log.d(TAG,message );
    }


    public void setMessage(String msg){
        Bundle args = new Bundle();
        args.putString("message",msg);
        setArguments(args);
    }


}
