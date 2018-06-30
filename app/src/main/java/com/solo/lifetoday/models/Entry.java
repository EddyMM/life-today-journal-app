package com.solo.lifetoday.models;

import java.util.Date;

/**
 * @author eddy.
 */

public class Entry {
    private String mTitle, mContent;
    private Date mCreatedOn, mLastUpdatedOn;

    public Entry(String title, String content) {
        mTitle = title;
        mContent = content;
        mCreatedOn = new Date();
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
