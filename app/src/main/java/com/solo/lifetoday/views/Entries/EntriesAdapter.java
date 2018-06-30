package com.solo.lifetoday.views.Entries;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solo.lifetoday.Constants;
import com.solo.lifetoday.R;
import com.solo.lifetoday.presenters.EntriesListPresenter;

/**
 * Adapter to provide the entries
 */
public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntryViewHolder> {
    private EntriesListPresenter mEntriesListPresenter;
    private Context mContext;

    EntriesAdapter(Context context, EntriesListPresenter entriesListPresenter) {
        mContext = context;
        mEntriesListPresenter = entriesListPresenter;
    }

    @NonNull
    @Override
    public EntriesAdapter.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {
        View entryView = LayoutInflater.from(mContext)
                .inflate(R.layout.entry_view, parent, false);

        return new EntriesAdapter.EntryViewHolder(entryView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesAdapter.EntryViewHolder holder, int position) {
        mEntriesListPresenter.onBindEntry(position, holder);
    }

    @Override
    public int getItemCount() {
        return mEntriesListPresenter.getEntriesCount();
    }

    /**
     * ViewHolder for an entry in the journal
     */
    class EntryViewHolder extends RecyclerView.ViewHolder implements
            EntriesListPresenter.ViewHolderView {
        private TextView mTitleTextView, mContentTextView, mLastUpdatedOnTextView;

        EntryViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(view -> {
                Intent editEntryIntent = new Intent(mContext,
                        EntryDetailActivity.class);
                editEntryIntent.putExtra(
                        EntryDetailActivity.ENTRY_ID_EXTRA,
                        mEntriesListPresenter.getEntries().get(getAdapterPosition()).getKey());
                mContext.startActivity(editEntryIntent);
            });

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
            } catch (StringIndexOutOfBoundsException e) {
                subString = content;
            }
            mContentTextView.setText(String.format("%s %s",
                    subString,
                    mContext.getString(R.string.continuation_symbol)));
        }

        @Override
        public void setLastUpdatedOn(String date) {
            mLastUpdatedOnTextView.setText(String.format("%s %s",
                    mContext.getString(R.string.last_update_on),
                    date));
        }
    }
}
