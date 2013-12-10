package me.aktor.course.droid.lists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class InputFragment extends Fragment {

    public interface OnNewItemListener {
        public void onNewItem(String text);
    }

    private EditText mInput;
    private final Callbacks fCallabacks = new Callbacks();
    private OnNewItemListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_fragment,container,false);
        mInput = (EditText)v.findViewById(R.id.edt_input);
        v.findViewById(R.id.btn_add).setOnClickListener(fCallabacks);
        return v;
    }

    public void setOnNewItemListener(OnNewItemListener listener){
        mListener = listener;
    }

    private void dontDoThisAtHome(String text){
        ListActivity a = (ListActivity)getActivity();
        ListFragment f =(ListFragment)a.getSupportFragmentManager().findFragmentById(R.id.List);
//        f.addItem(text);
    }

    private class Callbacks implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String text= mInput.getText().toString();
            mInput.setText(null);
            if (TextUtils.isEmpty(text)) return;
//            dontDoThisAtHome(text);
            if (mListener!=null){
                mListener.onNewItem(text);
            }
        }

    }
}