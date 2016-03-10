package io.github.nginth.anxietydiary2.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by nginther on 2/19/16.
 *  DatabaseHelper - A helper class for accessing the diary database.
 *  Follows the singleton design pattern, only one instance of DatabaseHelper may exist at a time.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper singletonDb;
    private static final String DATABASE_NAME = "diary.db";
    private static final int VERSION = 1;
    public static final String TITLE = "title";
    public static final String ENTRY = "entry";
    public static final String LEVEL = "level";
    public static final String DATE = "d_date";
    public static final String TABLE = "diary";
    public static final String _ID = "_id";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(singletonDb == null){
            singletonDb = new DatabaseHelper(context.getApplicationContext());
        }
        return singletonDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createStatement = "CREATE TABLE " + TABLE + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE +  " TEXT, " + ENTRY + " TEXT, " + LEVEL + " INTEGER, " + DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createStatement);

        ContentValues cv = new ContentValues();
        Date d = new Date();

        cv.put(TITLE, "title 1");
        cv.put(ENTRY, "test entry 1");
        cv.put(LEVEL, 1);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "title 2");
        cv.put(ENTRY, "test entry 2");
        cv.put(LEVEL, 2);
        db.insert(TABLE, TITLE, cv);

        cv.put(TITLE, "title 3");
        cv.put(ENTRY, "test entry 3");
        cv.put(LEVEL, 3);
        db.insert(TABLE, TITLE, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("Shouldn't be upgrading the database, what are you doing man");
    }

}
