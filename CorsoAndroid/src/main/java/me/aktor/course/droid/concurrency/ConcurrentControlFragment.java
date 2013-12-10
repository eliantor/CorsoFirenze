package me.aktor.course.droid.concurrency;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import me.aktor.course.droid.R;

/**
 * Created by eto on 04/12/13.
 */
public class ConcurrentControlFragment extends Fragment {
    private static final String SAVED_LOG = "LOG";


    public static interface OnStartTask {
        public void startTask(long delay);
    }

    private OnStartTask mListener;
    private TextView mConsole;
    private String mLog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            mLog = "";
        } else {
            mLog=savedInstanceState.getString(SAVED_LOG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.concurrent_control_fragment,container,false);
        final EditText delayInput = (EditText)v.findViewById(R.id.input_delay);
        v.findViewById(R.id.start_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delayString = delayInput.getText().toString();
                long delay;
                if (TextUtils.isEmpty(delayString)){
                    delay = 5000;
                }else {
                    delay  = Long.parseLong(delayString);
                }
                if (mListener!=null){
                    mListener.startTask(delay);
                }
            }
        });

        mConsole = (TextView)v.findViewById(R.id.output_logging);
        mConsole.setText(mLog);
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_LOG,mLog);
    }


    public void appendLog(String message){
        mLog =String.format("%s\n%s",mLog,message);
        mConsole.setText(mLog);
    }

    public void setListener(OnStartTask mListener) {
        this.mListener = mListener;
    }
}
