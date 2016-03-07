package io.github.nginth.anxietydiary2.controllers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import io.github.nginth.anxietydiary2.models.DatabaseHelper;

/**
 * Created by nginther on 2/19/16.
 */
public class Provider extends ContentProvider {
    private static final int DIARIES = 1;
    private static final int DIARY_ID = 2;
    private static final String TABLE = "diary";

    public static final class Diaries implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.parse("content://io.github.nginth.anxietydiaries.Provider/diaries");
        public static final String DEFAULT_SORT = DatabaseHelper.DATE;
        public static final String TITLE = DatabaseHelper.TITLE;
        public static final String ENTRY = DatabaseHelper.ENTRY;
        public static final String LEVEL = DatabaseHelper.LEVEL;
        public static final String DATE = DatabaseHelper.DATE;
        public static final String[] ALL_ROWS = {TITLE, ENTRY, LEVEL, DATE};
    }

    private static final UriMatcher MATCHER;
    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI("io.github.nginth.axietydiaries.Provider", "diaries", DIARIES);
        MATCHER.addURI("io.github.nginth.axietydiaries.Provider", "diaries/#", DIARY_ID);
    }

    private DatabaseHelper db = null;

    @Override
    public boolean onCreate() {
        db = DatabaseHelper.getInstance(getContext());
        return !(db == null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE);

        String order;

        if(sortOrder == null || sortOrder.length() <= 0)
            order = Diaries.DEFAULT_SORT;
        else
            order = sortOrder;

        Cursor c = qb.query(db.getReadableDatabase(), projection, selection, selectionArgs, null, null, order);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        if(MATCHER.match(uri) == DIARIES) {
            return "vnd.nginth.cursor.dir/diary";
        }
        return "vnd.nginth.cursor.item/diary";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.getWritableDatabase().insert(TABLE, Diaries.TITLE, values);
        if(rowID > 0) {
            Uri u = ContentUris.withAppendedId(Diaries.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uri, null); //no content observer
            return u;
        }

        throw new SQLiteException("Failed to insert to database: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.getWritableDatabase().delete(TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = db.getWritableDatabase().update(TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
