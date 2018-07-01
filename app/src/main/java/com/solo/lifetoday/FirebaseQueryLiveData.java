package com.solo.lifetoday;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * @author eddy.
 */

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
    private static final String TAG = FirebaseQueryLiveData.class.getSimpleName();

    private final Query query;
    private final EntriesEventListener listener = new EntriesEventListener();

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref.orderByChild("lastUpdatedOn");
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
        query.removeEventListener(listener);
    }

    private class EntriesEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}
