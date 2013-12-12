package me.aktor.course.droid.notes;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.aktor.course.droid.NotesContract;
import me.aktor.course.droid.concurrency.DelayedRandomGenerator;
import me.aktor.course.droid.services2.SimpleService;

/**
 * Created by eto on 04/12/13.
 */
public class ConcurrentBridge extends Fragment implements DelayedRandomGenerator.OnComplete{

    public static int INSERT_OPERATION = 1;

    public static class NotesQueryHandler extends AsyncQueryHandler{

        private ConcurrentBridge fragment;

        public NotesQueryHandler(ConcurrentBridge fragment) {
            super(fragment.getActivity().getContentResolver());
            this.fragment = fragment;
        }


        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
            if (fragment!=null){
                fragment.handleInsertComplete(uri);
            }
        }

        public void detachFromFragment() {
            this.fragment = null;
        }
    }


    private NotesQueryHandler mQueryHandler;
    private OnMessageListener mMessageListener;
    private boolean mReady;
    private List<String> mPending;
    private Context mGlobalContext;


    public  static interface OnMessageListener{
        public void onMessageReceived(String message);
    }


    public void insertNote(ContentValues v) {
        mQueryHandler.startInsert(INSERT_OPERATION,null, NotesContract.Note.CONTENT_URI,v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnMessageListener){
            mMessageListener = (OnMessageListener)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMessageListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mQueryHandler = new NotesQueryHandler(this);

        mPending = new ArrayList<String>();
        mGlobalContext = getActivity().getApplicationContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mQueryHandler!=null){
            mQueryHandler.cancelOperation(0);
            mQueryHandler.detachFromFragment();
        }
        mPending = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mReady = true;
        deliverPending();
    }

    private void deliverPending() {
        if (mPending.size()>0){
            for (String message:mPending){
                mMessageListener.onMessageReceived(message);
            }
            mPending.clear();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mReady = false;
    }



    private void handleInsertComplete(Uri uri) {
        if (mReady){
            mMessageListener.onMessageReceived(uri.toString());

        } else {
            mPending.add(uri.toString());
        }
        SimpleService.startSimpleService(mGlobalContext,"Inserted text is "+uri.toString());

    }

    @Override
    public void onMessage(String message) {
        if (mReady){
            mMessageListener.onMessageReceived(message);
        } else {
            mPending.add(message);
        }
    }
}
