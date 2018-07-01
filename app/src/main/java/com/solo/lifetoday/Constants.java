package com.solo.lifetoday;

import com.google.firebase.auth.FirebaseAuth;

/**
 * @author eddy.
 */

public class Constants {
    public static final int ENTRY_ITEM_EXCERPT_SIZE = 60;
    public static final String JOURNAL_DB_REF =
            "/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/journal";
}
