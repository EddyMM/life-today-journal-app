package com.solo.lifetoday;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author eddy.
 */

public class EntriesViewModel extends ViewModel {
    private static final DatabaseReference JOURNAL_REF =
            FirebaseDatabase.getInstance().getReference(Constants.JOURNAL_DB_REF);

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(JOURNAL_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }
}
