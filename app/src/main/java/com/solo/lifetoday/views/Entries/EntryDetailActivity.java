package com.solo.lifetoday.views.Entries;

import android.support.v4.app.Fragment;

import com.solo.lifetoday.views.SingleFragmentActivity;

/**
 * @author eddy.
 */

public class EntryDetailActivity extends SingleFragmentActivity {
    public static final String ENTRY_ID_EXTRA = "entry_id";

    @Override
    protected Fragment createFragment() {
        return new EntryDetailFragment();
    }
}
