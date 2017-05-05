package com.example.sampletest.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by KorotayevR on 3/14/2017.
 */

public class Crime extends Object {
    private UUID    mId;
    private String  mTitle;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    private Date    mDate;
    private boolean mSolved;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public Crime() {
        this.mId = UUID.randomUUID();
        mDate    = new Date();
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
