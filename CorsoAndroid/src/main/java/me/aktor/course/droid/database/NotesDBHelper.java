package me.aktor.course.droid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eto on 10/12/13.
 */
public class NotesDBHelper extends SQLiteOpenHelper {

    public NotesDBHelper(Context context) {
        super(context, Constansts.DB_NAME,
              Constansts.CURSOR_FACTORY, Constansts.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constansts.CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion>oldVersion){
            db.execSQL(Constansts.DROP_NOTES_TABLE);
            onCreate(db);
        }
    }
}
