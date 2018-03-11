package com.csegsahack.friendinneed;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;

public class FriendInNeedApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
	}
}
