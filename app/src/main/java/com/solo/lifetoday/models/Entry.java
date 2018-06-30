package com.solo.lifetoday.models;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

/**
 * @author eddy.
 */

public class Entry {
    private String mKey;
    private String mTitle, mContent;
    private Date mCreatedOn, mLastUpdatedOn;

    public Entry() {}

    public Entry(String key, String title, String content) {
        mKey = key;
        mTitle = title;
        mContent = content;
        mCreatedOn = new Date();
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Date getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        mCreatedOn = createdOn;
    }

    public Date getLastUpdatedOn() {
        return (mLastUpdatedOn == null) ? mCreatedOn : mLastUpdatedOn;
    }

    public void setLastUpdatedOn(Date lastUpdatedOn) {
        mLastUpdatedOn = lastUpdatedOn;
    }
}
