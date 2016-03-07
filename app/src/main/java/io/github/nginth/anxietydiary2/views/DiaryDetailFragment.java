package io.github.nginth.anxietydiary2.views;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Date;

import io.github.nginth.anxietydiary2.R;
import io.github.nginth.anxietydiary2.controllers.Provider;

/**
 * Created by nginther on 3/6/16.
 */
public class DiaryDetailFragment extends Fragment {
    private Cursor cursor;

    public DiaryDetailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments().getInt("id");

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

        View view = inflater.inflate(R.layout.fragment_diary_detail, container, false);
        TextView dateTV = (TextView) view.findViewById(R.id.detail_date);
        TextView diaryTV = (TextView) view.findViewById(R.id.detail_diary_edit);

        // retrieve entry text and date from db
        String diaryEntry = cursor.getString(cursor.getColumnIndexOrThrow(Provider.Diaries.ENTRY));
        int date = cursor.getInt(cursor.getColumnIndexOrThrow(Provider.Diaries.DATE));
        Date d = new Date(date);
        dateTV.append(" " + d.toString());
        diaryTV.setText(diaryEntry);

        return view;
    }
}
