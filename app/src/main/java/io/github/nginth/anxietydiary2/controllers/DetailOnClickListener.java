package io.github.nginth.anxietydiary2.controllers;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

/**
 * Created by nginther on 3/7/16.
 */
public class DetailOnClickListener implements View.OnClickListener {
    private EditText diary;
    private EditText level;
    private Context ctxt;
    private int id;
    private FragmentManager fm;

    public DetailOnClickListener(Context ctxt, EditText diary, EditText level, int id, FragmentManager fm) {
        this.ctxt = ctxt;
        this.diary = diary;
        this.level = level;
        this.id = id;
        this.fm = fm;
    }

    @Override
    public void onClick(View v) {
        String entry = diary.getText().toString();
        String levelString = level.getText().toString();

        ContentValues cv = new ContentValues();
        if(!levelString.isEmpty()) {
            int level = Integer.parseInt(levelString);
            cv.put(Provider.Diaries.LEVEL, level);
        }
        if(!entry.isEmpty()) {
            cv.put(Provider.Diaries.ENTRY, entry);
        }

        String where = Provider.Diaries._ID + " = ?";
        String[] whereargs = new String[] {String.valueOf(id)};
        ctxt.getContentResolver().update(Provider.Diaries.CONTENT_URI, cv, where, whereargs);
        fm.popBackStackImmediate();
    }
}
