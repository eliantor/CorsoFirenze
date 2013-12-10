package me.aktor.course.droid.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import me.aktor.course.droid.NotesContract;

public class NotesProvider extends ContentProvider {

    private NotesDBHelper mDBHelper;

    public NotesProvider() {
    }


    @Override
    public boolean onCreate() {
        mDBHelper = new NotesDBHelper(getContext());
        return true; // true se e' possibile usare il content provider
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final String table;
        switch (Constansts.MATCHER.match(uri)){
            case Constansts.ALL_NOTES:
                table = NotesContract.Note.PATH;
                break;
            default:
                throw new UnsupportedOperationException("uri note supported "+uri);

        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long newId =db.insert(table,null,values);
        if (newId == -1){
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);

        Uri newUri = ContentUris.withAppendedId(uri,newId);
        return newUri;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqb = new SQLiteQueryBuilder();
        final String tables;
        switch (Constansts.MATCHER.match(uri)){
            case Constansts.ALL_NOTES:
                tables = NotesContract.Note.PATH;
                break;
            case Constansts.SINGLE_NOTE:
                tables = NotesContract.Note.PATH;
                long id = ContentUris.parseId(uri);
                sqb.appendWhere("_id = "+id);
            break;
            default:
                throw new UnsupportedOperationException("uri not supported "+uri);
        }
        sqb.setTables(tables);

        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursor =sqb.query(db,projection,selection,selectionArgs,sortOrder,/*group by*/null,/*having*/null);
        if(cursor!=null)cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
