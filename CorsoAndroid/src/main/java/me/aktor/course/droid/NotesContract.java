package me.aktor.course.droid;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eto on 10/12/13.
 */
public final class NotesContract {
    private NotesContract(){}

    public final static String AUTHORITY = "me.aktor.course.droid.notes";

    public final static Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);


    public final static class Note {
        private Note(){}

        public final static String PATH = "notes";
        // content://me.aktor.course.droid.notes/notes
        public final static Uri CONTENT_URI = NotesContract.CONTENT_URI.buildUpon().appendPath(PATH).build();

        public final static String _ID = BaseColumns._ID; // BaseColumns._ID = "_id"
        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String DATE = "date";


    }
}
