package me.aktor.course.droid.tablet.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class CollectionFragment extends Fragment {


    public final static int NONE = -1;
    public final static int FIRST = 0;
    public final static int SECOND = 1;

    public static interface OnItemSelected{
        public void onItemSelected(int item);
    }

    private OnItemSelected mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.collection_fragment,container,false);
        RadioGroup rg  = (RadioGroup)v.findViewById(R.id.rg_select);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mListener == null)return;
                if (checkedId==R.id.select_first){
                    mListener.onItemSelected(FIRST);
                } else if(checkedId==R.id.select_second){
                    mListener.onItemSelected(SECOND);
                } else {
                    mListener.onItemSelected(NONE);
                }
            }
        });
        return v;
    }


    public void setListener(OnItemSelected listener){
        mListener = listener;
    }
}
