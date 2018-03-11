package com.csegsahack.friendinneed.Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * Created by charlesmarino on 10/19/16.
 */

public class UserProfile implements Parcelable {
	public String id;
	public String aboutMe;
	public String dribbble;
	public String github;
	public String website;
	public String fbid;
	public String name;
	public String fcmRegistrationToken;
	public String gender;
	public String location;
	public String profilePicUrl;
	public String email;
	public String fname;
	public String skill;
	public ArrayList<String> lookingFor = new ArrayList<>();
	public ArrayList<String> tags = new ArrayList<>();

	public UserProfile(){}

	public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
		@Override
		public UserProfile createFromParcel(Parcel in) {
			return new UserProfile(in);
		}

		@Override
		public UserProfile[] newArray(int size) {
			return new UserProfile[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	protected UserProfile(Parcel in) {
		id = in.readString();
		aboutMe = in.readString();
		dribbble = in.readString();
		github = in.readString();
		website = in.readString();
		fbid = in.readString();
		name = in.readString();
		fcmRegistrationToken = in.readString();
		gender = in.readString();
		location = in.readString();
		profilePicUrl = in.readString();
		email = in.readString();
		fname = in.readString();
		skill = in.readString();
		in.readStringList(lookingFor);
		in.readStringList(tags);
		//add all other variables to this
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(aboutMe);
		parcel.writeString(dribbble);
		parcel.writeString(github);
		parcel.writeString(website);
		parcel.writeString(fbid);
		parcel.writeString(name);
		parcel.writeString(fcmRegistrationToken);
		parcel.writeString(gender);
		parcel.writeString(location);
		parcel.writeString(profilePicUrl);
		parcel.writeString(email);
		parcel.writeString(fname);
		parcel.writeString(skill);
		parcel.writeStringList(lookingFor);
		parcel.writeStringList(tags);
		//add all other variables to this
	}
	//for inituser method on serverside
	public JSONObject toJSON() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("id", id);
		result.put("aboutMe", aboutMe);
		result.put("dribbble", dribbble);
		result.put("github", github);
		result.put("website", website);
		result.put("fbid", fbid);
		result.put("name", name);
		result.put("fcmRegistrationToken",fcmRegistrationToken);
		result.put("gender", gender);
		result.put("location", location);
		result.put("profilePicUrl", profilePicUrl);
		result.put("email", email);
		result.put("fname", fname);
		result.put("skill", skill);
		result.put("lookingFor", lookingFor);
		result.put("tags",  tags);
		return new JSONObject(result);
	}
}
