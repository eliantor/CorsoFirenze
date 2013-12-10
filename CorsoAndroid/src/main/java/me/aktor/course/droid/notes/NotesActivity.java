package me.aktor.course.droid.notes;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import me.aktor.course.droid.NotesContract;
import me.aktor.course.droid.R;

/**
 * Created by eto on 10/12/13.
 */
public class NotesActivity extends FragmentActivity implements ConcurrentBridge.OnMessageListener{
    private final static String BRIDGE_TAG = "BRIDGE";


    private ConcurrentBridge mConcurrentBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(new ConcurrentBridge(),BRIDGE_TAG)
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof InsertFragment){
            ((InsertFragment) fragment).setOnNewNoteListener(mNoteListenerASync);
        } else if (fragment instanceof ConcurrentBridge){
            mConcurrentBridge = (ConcurrentBridge)fragment;

        }
    }

    private final InsertFragment.OnNewNote  mNoteListenerSync = new InsertFragment.OnNewNote() {
        @Override
        public void onNewNote(String title, String content) {
            ContentValues v = new ContentValues();
            v.put(NotesContract.Note.TITLE,title);
            v.put(NotesContract.Note.CONTENT,content);
            v.put(NotesContract.Note.DATE,System.currentTimeMillis());
            Uri uri= getContentResolver().insert(NotesContract.Note.CONTENT_URI, v);
        }
    };


    private final InsertFragment.OnNewNote  mNoteListenerASync = new InsertFragment.OnNewNote() {
        @Override
        public void onNewNote(String title, String content) {

            ContentValues v = new ContentValues();
            v.put(NotesContract.Note.TITLE,title);
            v.put(NotesContract.Note.CONTENT,content);
            v.put(NotesContract.Note.DATE,System.currentTimeMillis());
            mConcurrentBridge.insertNote(v);

        }
    };

    @Override
    public void onMessageReceived(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
