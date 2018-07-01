package com.solo.lifetoday.presenters;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.solo.lifetoday.Utils;
import com.solo.lifetoday.models.Entry;

import java.util.List;

/**
 * @author eddy.
 */

public class EntriesListPresenter {
    private static final String TAG = EntriesListPresenter.class.getSimpleName();
    private List<DataSnapshot> mEntries;
    private EntriesView mView;

    public EntriesListPresenter(List<DataSnapshot> entries, EntriesView view) {
        mEntries = entries;
        mView = view;
    }

    public void onBindEntry(int position, ViewHolderView view) {
        Entry entry = mEntries.get(position).getValue(Entry.class);
        if(entry != null) {
            view.setTitle(entry.getTitle());
            view.setContent(entry.getContent());
            view.setLastUpdatedOn(Utils.getFormattedDateWithTime(entry.getLastUpdatedOn()));
        } else {
            Log.w(TAG, "Could not find entry at position: " + position);
        }
    }

    public int getEntriesCount() {
        int noOfEntries = mEntries.size();
        if (noOfEntries <= 0) {
            mView.showNoEntries();
        } else {
            mView.showEntries();
        }

        return noOfEntries;
    }

    public List<DataSnapshot> getEntries() {
        return mEntries;
    }

    public interface ViewHolderView {
        void setTitle(String title);

        void setContent(String content);

        void setLastUpdatedOn(String date);
    }

    public interface EntriesView {
        void showNoEntries();

        void showEntries();
    }
}
