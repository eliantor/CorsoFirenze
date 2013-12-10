package me.aktor.course.droid.tablet.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class DetailFragment extends Fragment {

    private TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment,container,false);
        mTextView = (TextView)v.findViewById(R.id.tv_output);
        return v;
    }

    public void show(int item){
        switch (item){
            case -1:
                mTextView.setText("");
                break;
            case 0:
                mTextView.setText("PRIMO ELEMENTO");
                break;
            case 1:
                mTextView.setText("SECONDO ELEMENTO");
                break;
        }
    }
}
