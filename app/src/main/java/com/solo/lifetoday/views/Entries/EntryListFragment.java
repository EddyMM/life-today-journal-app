package com.solo.lifetoday.views.Entries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solo.lifetoday.Constants;
import com.solo.lifetoday.R;
import com.solo.lifetoday.models.Entry;
import com.solo.lifetoday.presenters.EntriesListPresenter;

import java.util.ArrayList;

/**
 * @author eddy.
 */

public class EntryListFragment extends Fragment {
    RecyclerView mEntriesRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.entry_list_fragment, container, false);

        Toolbar appToolBar = fragmentView.findViewById(R.id.app_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(appToolBar);

        mEntriesRecyclerView = fragmentView.findViewById(R.id.entriesRecyclerView);
        mEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Existence", "The property of being involved in the"
                + "events in the universe"));
        entries.add(new Entry("Death", "The process of losing the ability to"
                + "interact with the reality assumed to be life"));

        EntriesListPresenter entriesListPresenter = new EntriesListPresenter(entries);

        mEntriesRecyclerView.setAdapter(new EntriesAdapter(entriesListPresenter));

        return fragmentView;
    }

    /**
     * Adapter to provide the entries
     */
    private class EntriesAdapter extends RecyclerView.Adapter<EntryViewHolder> {
        private EntriesListPresenter mEntriesListPresenter;

        EntriesAdapter(EntriesListPresenter entriesListPresenter) {
            mEntriesListPresenter = entriesListPresenter;
        }

        @NonNull
        @Override
        public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View entryView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.entry_view, parent, false);

            return new EntryViewHolder(entryView);
        }

        @Override
        public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
            mEntriesListPresenter.onBindEntry(position, holder);
        }

        @Override
        public int getItemCount() {
            return mEntriesListPresenter.getEntriesCount();
        }
    }

    /**
     * ViewHolder for an entry in the journal
     */
    private class EntryViewHolder extends RecyclerView.ViewHolder implements
            EntriesListPresenter.View {
        private TextView mTitleTextView, mContentTextView, mLastUpdatedOnTextView;

        EntryViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
            mLastUpdatedOnTextView = itemView.findViewById(R.id.lastUpdatedOnTextView);
        }

        @Override
        public void setTitle(String title) {
            mTitleTextView.setText(title);
        }

        @Override
        public void setContent(String content) {
            String subString;
            try {
                subString = content.substring(0, Constants.ENTRY_ITEM_EXCERPT_SIZE - 1);
            } catch(StringIndexOutOfBoundsException e) {
                subString = content;
            }
            mContentTextView.setText(String.format("%s %s",
                    subString,
                    getString(R.string.continuation_symbol)));
        }

        @Override
        public void setLastUpdatedOn(String date) {
            mLastUpdatedOnTextView.setText(String.format("%s %s",
                    getString(R.string.last_update_on),
                    date));
        }
    }
}
