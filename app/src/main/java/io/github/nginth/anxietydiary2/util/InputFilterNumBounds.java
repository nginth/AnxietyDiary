package io.github.nginth.anxietydiary2.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * Created by nginther on 3/7/16.
 */
public class InputFilterNumBounds implements InputFilter {
    private static final String LOG_TAG = InputFilterNumBounds.class.getSimpleName();
    private int min;
    private int max;

    public InputFilterNumBounds(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
            if(min <= input && input <= max) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            Log.v(LOG_TAG, "Filtered " + e.getMessage());
        }
        return "";
    }
}
