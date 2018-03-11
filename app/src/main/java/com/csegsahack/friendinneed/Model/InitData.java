package com.csegsahack.friendinneed.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by charlesmarino on 10/19/16.
 */
public class InitData implements Parcelable {
	private UserProfile mUserProfile;
	public InitData(){}

	protected InitData(Parcel in) {
		mUserProfile = in.readParcelable(UserProfile.class.getClassLoader());

	}

	public static final Creator<InitData> CREATOR = new Creator<InitData>() {
		@Override
		public InitData createFromParcel(Parcel in) {
			return new InitData(in);
		}

		@Override
		public InitData[] newArray(int size) {
			return new InitData[size];
		}
	};

	public UserProfile getUserProfile() {
		return mUserProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.mUserProfile = userProfile;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mUserProfile, flags);
	}
}