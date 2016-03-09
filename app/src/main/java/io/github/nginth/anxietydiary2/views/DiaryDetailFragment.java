package io.github.nginth.anxietydiary2.views;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import io.github.nginth.anxietydiary2.R;
import io.github.nginth.anxietydiary2.controllers.DetailOnClickListener;
import io.github.nginth.anxietydiary2.controllers.Provider;
import io.github.nginth.anxietydiary2.util.InputFilterNumBounds;

/**
 * Created by nginther on 3/6/16.
 */
public class DiaryDetailFragment extends Fragment {
    private Cursor cursor;
    private int id;

    public DiaryDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getInt("id");

        cursor = getActivity()
            .getContentResolver()
            .query(Provider.Diaries.CONTENT_URI, Provider.Diaries.ALL_ROWS,
                    "? = " + Provider.Diaries._ID,
                    new String[]{String.valueOf(id)},
                    Provider.Diaries.DEFAULT_SORT);

        cursor.moveToFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // no ViewGroup exists so there's no point in populating the view b/c it won't be rendered anyway
        if(container == null)
            return null;
        Boolean isNew = getArguments().getBoolean("isNew");
        View view = inflater.inflate(R.layout.fragment_diary_detail, container, false);
        loadEntry(view, isNew);

        return view;
    }

    private void loadEntry(View view, Boolean isNew) {
        TextView dateTextView = (TextView) view.findViewById(R.id.detail_date);
        EditText diaryEditText = (EditText) view.findViewById(R.id.detail_diary_edit);
        EditText levelEditText = (EditText) view.findViewById(R.id.detail_level_edit);
        EditText titleEditText = (EditText) view.findViewById(R.id.detail_title_edit);

        // make sure the user only inputs 0-10
        levelEditText.setFilters(new InputFilter[]{new InputFilterNumBounds(0, 10)});
        Button saveButton = (Button) view.findViewById(R.id.detail_save);

        saveButton.setOnClickListener(
                new DetailOnClickListener(getActivity(), view, id, getFragmentManager(), isNew));

        // retrieve entry text and date from db
        if(!isNew) {
            String diaryEntry = cursor.getString(cursor.getColumnIndexOrThrow(Provider.Diaries.ENTRY));
            int level = cursor.getInt(cursor.getColumnIndexOrThrow(Provider.Diaries.LEVEL));
            int date = cursor.getInt(cursor.getColumnIndexOrThrow(Provider.Diaries.DATE));
            Date d = new Date(date);

            dateTextView.append(d.toString());
            diaryEditText.setText(diaryEntry);
            levelEditText.setText(String.valueOf(level));
        }
    }
}
