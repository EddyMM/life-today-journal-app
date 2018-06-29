package com.solo.lifetoday.views.SignIn;

import android.support.v4.app.Fragment;

import com.solo.lifetoday.views.SingleFragmentActivity;

public class SignInActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SignInFragment();
    }
}
