package me.aktor.course.droid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

/**
 * Created by eto on 10/12/13.
 */
public class DatabaseCopy {

    private Context mContext;

    public void copyAssets() throws IOException {
        File file = new File("file:///android_asset/data.sql");
        mContext.getAssets().open("myscript.sql");
        mContext.getDatabasePath(Constansts.DB_NAME);
    }


    private void executeInsertTransactionally(SQLiteDatabase db){
        db.beginTransaction();
        try{
            for(int i=0;i<1000000;i++){
                // operazione sul db
                db.yieldIfContendedSafely();
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }

}
