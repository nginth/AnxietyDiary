package io.github.nginth.anxietydiary2.views;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import io.github.nginth.anxietydiary2.R;
import io.github.nginth.anxietydiary2.controllers.Provider;
import io.github.nginth.anxietydiary2.models.DatabaseHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class DiaryListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = DiaryListFragment.class.getSimpleName();
    private OnListItemSelectedListener mListener;
    private SimpleCursorAdapter adapter;
    private static final int ADAPTER_FLAGS = 0;
    private static final String[] PROJECTION =
            {Provider.Diaries._ID, Provider.Diaries.TITLE, Provider.Diaries.ENTRY, Provider.Diaries.LEVEL, Provider.Diaries.DATE};

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DiaryListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d(LOG_TAG, "id: " + id + "exp: " + R.id.action_add);
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_add:
                addEntry();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEntry() {
        Bundle b = new Bundle();
        b.putBoolean("isNew", true);
        Fragment addEntryFragment = new DiaryDetailFragment();
        addEntryFragment.setArguments(b);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(android.R.id.content, addEntryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Provider.Diaries.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        adapter.swapCursor(c);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* create CursorAdapter to populate the list view */
        adapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.list_main,
                        null,
                        new String[] {DatabaseHelper.TITLE, DatabaseHelper.ENTRY},
                        new int[] {R.id.title, R.id.entry},
                        ADAPTER_FLAGS);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.onListItemSelected((int) id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnListItemSelectedListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnListItemSelectedListener.");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnListItemSelectedListener {
        public void onListItemSelected(int id);
    }

}
