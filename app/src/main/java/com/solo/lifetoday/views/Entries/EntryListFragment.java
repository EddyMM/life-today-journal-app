package com.solo.lifetoday.views.Entries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solo.lifetoday.Constants;
import com.solo.lifetoday.R;
import com.solo.lifetoday.models.Entry;

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

        mEntriesRecyclerView = fragmentView.findViewById(R.id.entriesRecyclerView);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry("Existence", "The property of being involved in the"
                + "events in the universe"));
        entries.add(new Entry("Death", "The process of losing the ability to"
                + "interact with the reality assumed to be life"));
        mEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mEntriesRecyclerView.setAdapter(new EntriesAdapter(entries));

        return fragmentView;
    }

    /**
     * Adapter to provide the entries
     */
    private class EntriesAdapter extends RecyclerView.Adapter<EntryViewHolder> {
        private ArrayList<Entry> mEntries;

        EntriesAdapter(ArrayList<Entry> entries) {
            mEntries = entries;
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
            holder.bind(mEntries.get(position));
        }

        @Override
        public int getItemCount() {
            return mEntries.size();
        }
    }

    /**
     * ViewHolder for an entry in the journal
     */
    private class EntryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView, mContentTextView, mLastUpdatedOnTextView;

        EntryViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.titleTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
            mLastUpdatedOnTextView = itemView.findViewById(R.id.lastUpdatedOnTextView);
        }

        void bind(Entry entry) {
            mTitleTextView.setText(entry.getTitle());
            mContentTextView.setText(String.format("%s %s",
                    entry.getContent().substring(0, Constants.ENTRY_ITEM_EXCERPT_SIZE-1),
                    getString(R.string.continuation_symbol)));
            mLastUpdatedOnTextView.setText(String.format("%s %s",
                    getString(R.string.last_update_on),
                    entry.getLastUpdatedOn().toString()));
        }
    }
}
