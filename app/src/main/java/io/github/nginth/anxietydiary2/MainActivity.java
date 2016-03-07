package io.github.nginth.anxietydiary2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import io.github.nginth.anxietydiary2.views.DiaryDetailFragment;
import io.github.nginth.anxietydiary2.views.DiaryListFragment;


public class MainActivity extends Activity implements DiaryListFragment.OnListItemSelectedListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Create main list fragment */
        if(getFragmentManager().findFragmentById(android.R.id.content) == null) {
            getFragmentManager().beginTransaction()
                                .add(android.R.id.content,
                                        new DiaryListFragment()).commit();
        }
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        Toast.makeText(this,
                (CharSequence)parent.getAdapter().getItem(position),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemSelected(int id) {
//        Cursor c = this
//                .getContentResolver()
//                .query(Provider.Diaries.CONTENT_URI, Provider.Diaries.ALL_ROWS, "? = " + Provider.Diaries._ID,
//                        new String[]{String.valueOf(id)}, Provider.Diaries.DEFAULT_SORT);
//        c.moveToFirst();

        Fragment newFragment = new DiaryDetailFragment();
        Bundle b = new Bundle();
        b.putInt("id", id);
        newFragment.setArguments(b);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
