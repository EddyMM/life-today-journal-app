package com.solo.lifetoday;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * @author eddy.
 */

public class LifeTodayApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
