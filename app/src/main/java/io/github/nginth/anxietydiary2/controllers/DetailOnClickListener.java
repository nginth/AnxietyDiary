package io.github.nginth.anxietydiary2.controllers;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import io.github.nginth.anxietydiary2.R;

/**
 * Created by nginther on 3/7/16.
 */
public class DetailOnClickListener implements View.OnClickListener {
    private EditText diary;
    private EditText level;
    private EditText title;
    private Context ctxt;
    private int id;
    private FragmentManager fm;
    private Boolean isNew;

    public DetailOnClickListener(Context ctxt, View view, int id, FragmentManager fm, Boolean isNew) {
        diary = (EditText) view.findViewById(R.id.detail_diary_edit);
        level = (EditText) view.findViewById(R.id.detail_level_edit);
        title = (EditText) view.findViewById(R.id.detail_title_edit);
        this.ctxt = ctxt;
        this.id = id;
        this.fm = fm;
        this.isNew = isNew;
    }

    @Override
    public void onClick(View v) {
        String entry = diary.getText().toString();
        String levelString = level.getText().toString();
        String titleString = title.getText().toString();

        ContentValues cv = new ContentValues();
        if(!levelString.isEmpty()) {
            int level = Integer.parseInt(levelString);
            cv.put(Provider.Diaries.LEVEL, level);
        }
        if(!entry.isEmpty()) {
            cv.put(Provider.Diaries.ENTRY, entry);
        }
        if(!titleString.isEmpty()) {
            cv.put(Provider.Diaries.TITLE, titleString);
        }

        String where = Provider.Diaries._ID + " = ?";
        String[] whereargs = new String[] {String.valueOf(id)};
        // if new, insert, else update
        if(isNew)
            ctxt.getContentResolver().insert(Provider.Diaries.CONTENT_URI, cv);
        else
            ctxt.getContentResolver().update(Provider.Diaries.CONTENT_URI, cv, where, whereargs);
        fm.popBackStackImmediate();
    }
}
