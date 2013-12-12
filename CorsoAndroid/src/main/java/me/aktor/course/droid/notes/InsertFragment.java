package me.aktor.course.droid.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.aktor.course.droid.R;
import me.aktor.course.droid.services2.SimpleLongRunningService;

/**
 * Created by eto on 10/12/13.
 */
public class InsertFragment extends Fragment {

    public static interface OnNewNote {
        public void onNewNote(String title,String content);
    }

    private OnNewNote mOnNewNote;
    private EditText mTitleInput;
    private EditText mContentInput;

    public void setOnNewNoteListener(OnNewNote onNewNote){
        this.mOnNewNote = onNewNote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_layout,container,false);
        mTitleInput = (EditText)v.findViewById(R.id.edt_title_input);
        mContentInput = (EditText)v.findViewById(R.id.edt_input);
        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleLongRunningService.startLongRunning(getActivity(),10000);
                mOnNewNote.onNewNote(mTitleInput.getText().toString(),
                                     mContentInput.getText().toString());
            }
        });
        return v;
    }
}
