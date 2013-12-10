package me.aktor.course.droid.database;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;

import me.aktor.course.droid.NotesContract;

/**
 * Created by eto on 10/12/13.
 */
class Constansts {
    final static String DB_NAME = "notes.db";
    final static int DB_VERSION = 1;
    final static SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;

    static final String CREATE_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS "+ NotesContract.Note.PATH+
                "("+
                NotesContract.Note._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NotesContract.Note.TITLE + " TEXT, " +
                NotesContract.Note.CONTENT + " TEXT, "+
                NotesContract.Note.DATE+ " INTEGER)";

    static final String DROP_NOTES_TABLE =
            "DROP TABLE IF EXISTS "+NotesContract.Note.PATH;



    static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static final int ALL_NOTES = 1;
    static final int SINGLE_NOTE = 2;

    static{
        String authority = NotesContract.AUTHORITY;
        MATCHER.addURI(authority,NotesContract.Note.PATH,ALL_NOTES);
        MATCHER.addURI(authority,NotesContract.Note.PATH+"/#",SINGLE_NOTE);
        //MATCHER.addURI(authority,NotesContract.Note.PATH+"/*",3);
    }
}
