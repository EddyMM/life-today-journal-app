package com.solo.lifetoday.views.Entries;

import android.support.v4.app.Fragment;

import com.solo.lifetoday.views.SingleFragmentActivity;

/**
 * @author eddy.
 */

public class EntryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new EntryListFragment();
    }
}
