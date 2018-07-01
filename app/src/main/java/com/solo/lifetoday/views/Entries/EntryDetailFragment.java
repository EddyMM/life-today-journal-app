package com.solo.lifetoday.views.Entries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solo.lifetoday.Constants;
import com.solo.lifetoday.R;
import com.solo.lifetoday.Utils;
import com.solo.lifetoday.models.Entry;

import java.util.Date;
import java.util.HashMap;

/**
 * @author eddy.
 */

public class EntryDetailFragment extends Fragment {
    private static final String TAG = EntryDetailFragment.class.getSimpleName();

    private EditText mTitleEditText, mContentEditText;
    private TextView mCreatedOnTextView, mLastUpdatedOn;
    private View mFragmentView;
    private String mEntryKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(
                R.layout.entry_detail_fragment, container, false);

        initUI();

        Intent intent = requireActivity().getIntent();
        if (intent.hasExtra(EntryDetailActivity.ENTRY_ID_EXTRA)) {
            Log.d(TAG, "Has the key!");
            mEntryKey = intent.getStringExtra(EntryDetailActivity.ENTRY_ID_EXTRA);
            populateViewsWithEntry(mEntryKey);
        } else {
            Log.d(TAG, "No extra with the key :(");
        }

        return mFragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.entry_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_save:
                Log.d(TAG, "Save entry");
                // Ensure entry was typed
                if (TextUtils.isEmpty(mTitleEditText.getText().toString())) {
                    mTitleEditText.setError(requireActivity().getString(R.string.title_empty));
                    break;
                }
                if (TextUtils.isEmpty(mContentEditText.getText().toString())) {
                    mContentEditText.setError(requireActivity().getString(R.string.content_empty));
                    break;
                }

                saveEntry();
                break;
            case R.id.menu_share:
                Log.d(TAG, "Share entry");
                // Ensure entry was typed
                if (TextUtils.isEmpty(mTitleEditText.getText().toString())) {
                    mTitleEditText.setError(requireActivity().getString(R.string.title_empty));
                    break;
                }
                if (TextUtils.isEmpty(mContentEditText.getText().toString())) {
                    mContentEditText.setError(requireActivity().getString(R.string.content_empty));
                    break;
                }
                shareEntry(mTitleEditText.getText().toString(),
                        mContentEditText.getText().toString());
                break;
            case R.id.menu_delete:
                Log.d(TAG, "Delete entry");
                deleteEntry();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(requireActivity());
                break;
        }

        return true;
    }

    private void initUI() {
        Toolbar appToolbar = mFragmentView.findViewById(R.id.app_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(appToolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Display date
        mCreatedOnTextView = mFragmentView.findViewById(R.id.createdOnTextView);
        mLastUpdatedOn = mFragmentView.findViewById(R.id.lastUpdatedOnTextView);

        mTitleEditText = mFragmentView.findViewById(R.id.titleEditText);
        mContentEditText = mFragmentView.findViewById(R.id.contentEditText);
    }

    private void saveEntry() {
        String title = mTitleEditText.getText().toString();
        String content = mContentEditText.getText().toString();

        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference()
                    .child(Constants.JOURNAL_DB_REF);

            Entry entry;

            if (!TextUtils.isEmpty(mEntryKey)) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("title", title);
                result.put("content", content);
                result.put("lastUpdatedOn", new Date());

                databaseReference.child(mEntryKey).updateChildren(result);
                // entry = new Entry(mEntryKey, title, content);
                // Update last update time
                // entry.setLastUpdatedOn((new Date()));
            } else {
                entry = new Entry(databaseReference.push().getKey(), title, content);
                entry.setCreatedOn(new Date());
                databaseReference.child(entry.getKey()).setValue(entry);
            }

            Toast.makeText(requireContext(),
                    "Saved",
                    Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Error while saving",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void shareEntry(String title, String content) {
        String mimeType = "text/plain";

        String chooserTitle = "Share entry";

        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle(chooserTitle)
                .setText(String.format("%s:\n\n%s", title, content))
                .startChooser();
    }

    private void deleteEntry() {
        try {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference()
                    .child(Constants.JOURNAL_DB_REF);

            if (!TextUtils.isEmpty(mEntryKey)) {
                databaseReference.child(mEntryKey).removeValue();

                // Go back to list
                requireActivity().finish();
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Error while deleting",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void populateViewsWithEntry(String key) {
        // Get entry
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(
                Constants.JOURNAL_DB_REF);
        DatabaseReference entryReference = databaseReference.child(key);
        entryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Entry entry = dataSnapshot.getValue(Entry.class);
                if (entry != null) {
                    mTitleEditText.setText(entry.getTitle());
                    mContentEditText.setText(entry.getContent());
                    mLastUpdatedOn.setText(
                            String.format("%s: %s",
                                requireActivity().getString(R.string.last_update_on),
                                Utils.getFormattedDateWithTime(entry.getLastUpdatedOn())));
                    mCreatedOnTextView.setText(
                            String.format("%s: %s",
                                requireActivity().getString(R.string.created_on),
                                Utils.getFormattedDateWithTime(entry.getCreatedOn())));
                } else {
                    Log.w(TAG, "Could not fetch entry with key: " + key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
