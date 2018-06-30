package com.solo.lifetoday.views.Entries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solo.lifetoday.R;

import java.util.Date;

/**
 * @author eddy.
 */

public class EntryDetailFragment extends Fragment {
    private static final String TAG = EntryDetailFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(
                R.layout.entry_detail_fragment, container, false);

        Toolbar appToolbar = fragmentView.findViewById(R.id.app_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(appToolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Display date
        TextView createdOnTextView = fragmentView.findViewById(R.id.createdOnTextView);
        createdOnTextView.setText(new Date().toString());

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.entry_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch(itemId) {
            case R.id.menu_save:
                Log.d(TAG, "Save entry");
                break;
            case R.id.menu_share:
                Log.d(TAG, "Share entry");
                break;
            case R.id.menu_delete:
                Log.d(TAG, "Delete entry");
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(requireActivity());
                break;
        }

        return true;
    }
}
