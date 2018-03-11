package com.csegsahack.friendinneed.Model;

/**
 * Created by salscrudato on 12/22/16.
 */


public class UserProfileUpdatedEvent {

    private UserProfile mUserProfile;

    public UserProfileUpdatedEvent(UserProfile userProfile) {
        mUserProfile = userProfile;
    }
    
    public UserProfile getUserProfile() {
        return mUserProfile;
    }

}
