package com.solo.lifetoday;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.solo.lifetoday.views.SignIn.SignInActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 */
@RunWith(AndroidJUnit4.class)
public class SignInTest {
    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule =
            new ActivityTestRule(SignInActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.solo.lifetoday", appContext.getPackageName());
    }

    public void testSignInProgressBar() {
        onView(withId(R.id.googleSignInButton))
                .perform(click());
        onView(withId(R.id.signInProgressBar))
                .check(matches(isDisplayed()));
    }
}
