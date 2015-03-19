package com.example.sripadmanaban.criminalintent;

import java.util.UUID;

/**
 * Data model for the app
 * Created by Sripadmanaban on 3/19/2015.
 */
public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
