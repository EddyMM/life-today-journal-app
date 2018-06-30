package com.solo.lifetoday.presenters;

import com.solo.lifetoday.models.Entry;

import java.util.ArrayList;

/**
 * @author eddy.
 */

public class EntriesListPresenter {
    private ArrayList<Entry> mEntries;

    public EntriesListPresenter(ArrayList<Entry> entries) {
        mEntries = entries;
    }

    public void onBindEntry(int position, EntriesListPresenter.View view) {
        Entry entry = mEntries.get(position);
        view.setTitle(entry.getTitle());
        view.setContent(entry.getContent());
        view.setLastUpdatedOn(entry.getLastUpdatedOn().toString());
    }

    public int getEntriesCount() {
        return mEntries.size();
    }

    public interface View {
        void setTitle(String title);
        void setContent(String content);
        void setLastUpdatedOn(String date);
    }
}
