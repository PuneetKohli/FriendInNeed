package com.csegsahack.friendinneed.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.csegsahack.friendinneed.Model.InitData;
import com.csegsahack.friendinneed.Model.UserProfile;
import com.csegsahack.friendinneed.Model.UserProfileUpdatedEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by charlesmarino on 10/19/16.
 */


public class BaseActivity extends ProgressDialogActivity {

  private InitData mInitData;
  public static final  String EXTRA_INIT_DATA = "extra_init_data";
  private static final String TAG             = BaseActivity.class.getSimpleName();
  private ValueEventListener profileEventListener;
  private DatabaseReference  mUserProfileReference;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      mInitData = savedInstanceState.getParcelable("initData");
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    // Save UI state changes to the savedInstanceState.
    // This bundle will be passed to onCreate if the process is
    // killed and restarted.
    savedInstanceState.putParcelable("initData", mInitData);
    // etc.
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    // Restore UI state from the savedInstanceState.
    // This bundle has also been passed to onCreate.
    mInitData = savedInstanceState.getParcelable("initData");
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (profileEventListener != null) {
      mUserProfileReference.removeEventListener(profileEventListener);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (mInitData != null && mInitData.getUserProfile() != null) {
      createUserProfileListener(mInitData.getUserProfile());
    }
  }

  private void createUserProfileListener(UserProfile user) {
    if (profileEventListener == null && mUserProfileReference == null) {
      mUserProfileReference = FirebaseDatabase.getInstance().getReference().child("user-profile")
          .child(user.id);
      profileEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
          if (snapshot.exists()) {
            UserProfile profile = snapshot.getValue(UserProfile.class);
            mInitData.setUserProfile(profile);
            EventBus.getDefault().post(new UserProfileUpdatedEvent(profile));
            return;
          }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
      };
    }
    mUserProfileReference.addValueEventListener(profileEventListener);
  }

  public void startLoginActivity() {
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
  }

  public void setInitData(InitData data) {
    mInitData = data;
  }

  public InitData getInitData() {
    return mInitData;
  }

}