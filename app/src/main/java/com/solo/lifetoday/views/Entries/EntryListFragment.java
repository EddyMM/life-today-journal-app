package com.solo.lifetoday.views.Entries;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.solo.lifetoday.EntriesViewModel;
import com.solo.lifetoday.R;
import com.solo.lifetoday.Utils;
import com.solo.lifetoday.presenters.EntriesListPresenter;
import com.solo.lifetoday.views.SignIn.SignInActivity;

import java.util.List;

/**
 * @author eddy.
 */

public class EntryListFragment extends Fragment implements EntriesListPresenter.EntriesView {
    private static final String TAG = EntryListFragment.class.getSimpleName();
    RecyclerView mEntriesRecyclerView;
    private View mNoEntriesView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Ensure offline mode is supported
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.entry_list_fragment, container, false);

        Toolbar appToolBar = fragmentView.findViewById(R.id.app_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(appToolBar);

        mNoEntriesView = fragmentView.findViewById(R.id.noEntriesLayout);

        FloatingActionButton addEntryFab = fragmentView.findViewById(R.id.addEntryFab);
        addEntryFab.setOnClickListener(view -> {
            Intent addEntryIntent = new Intent(requireContext(), EntryDetailActivity.class);
            startActivity(addEntryIntent);
        });

        mEntriesRecyclerView = fragmentView.findViewById(R.id.entriesRecyclerView);
        mEntriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        EntriesViewModel entriesViewModel = ViewModelProviders.of(this)
                .get(EntriesViewModel.class);

        LiveData<DataSnapshot> entriesLiveData = entriesViewModel.getDataSnapshotLiveData();
        entriesLiveData.observe(requireActivity(), dataSnapshot -> {
            if (dataSnapshot != null) {
                Log.d(TAG, String.format("Entries snapshot(%d): %s",
                        dataSnapshot.getChildrenCount(),
                        dataSnapshot));
                List<DataSnapshot> entries = Utils.toList(dataSnapshot.getChildren());
                EntriesListPresenter entriesListPresenter = new EntriesListPresenter(
                        entries, this);

                mEntriesRecyclerView.setAdapter(new EntriesAdapter(
                        getContext(), entriesListPresenter));
            } else {
                Log.d(TAG, "Snapshot is null");
            }
        });

        return fragmentView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            Log.d(TAG, "Logout from app");
            signOut();
            openSignIn();
        }

        return true;
    }

    /**
     * Sign out google account user
     */
    private void signOut() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Firebase sign out
        firebaseAuth.signOut();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(
                requireContext(), googleSignInOptions);

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(
                task -> Log.i(TAG, "Signed out"));
    }

    /**
     * Start the sign in activity and end current activity
     */
    private void openSignIn() {
        Intent intent = new Intent(requireContext(), SignInActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void showNoEntries() {
        mEntriesRecyclerView.setVisibility(android.view.View.INVISIBLE);
        mNoEntriesView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showEntries() {
        mEntriesRecyclerView.setVisibility(android.view.View.VISIBLE);
        mNoEntriesView.setVisibility(android.view.View.INVISIBLE);
    }
}
