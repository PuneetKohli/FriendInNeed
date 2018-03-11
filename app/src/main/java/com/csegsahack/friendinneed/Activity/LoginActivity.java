package com.csegsahack.friendinneed.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.csegsahack.friendinneed.Model.InitData;
import com.csegsahack.friendinneed.Model.UserProfile;
import com.csegsahack.friendinneed.R;
import com.csegsahack.friendinneed.service.FirebaseClient;
import com.csegsahack.friendinneed.view.TextViewPlus;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ProgressDialogActivity {

  private static final String TAG = LoginActivity.class.getSimpleName();

  private FirebaseAuth                   mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private CallbackManager                mCallbackManager;
  private AccessToken                    mAccessToken;
  private ViewPager                      viewPager;
  private MyViewPagerAdapter             myViewPagerAdapter;
  private LinearLayout                   dotsLayout;
  private TextView[]                     dots;
  private int[]                          layouts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);

    FirebaseApp.initializeApp(this);

    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
          Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
          FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
          final DatabaseReference userProfileRef = FirebaseDatabase.getInstance().getReference()
              .child("user-profile").child(user.getUid());
          userProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
              if (snapshot.exists()) {
                InitData data = new InitData();
                UserProfile profile = snapshot.getValue(UserProfile.class);
                data.setUserProfile(profile);
                startMainActivity(data);
                return;
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
          });
        } else {
          Log.d(TAG, "onAuthStateChanged:signed_out");

        }
      }
    };
    mAuth.addAuthStateListener(mAuthListener);

    setContentView(R.layout.activity_login);
    viewPager = (ViewPager) findViewById(R.id.view_pager);
    dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
    layouts = new int[]{
        R.layout.welcome_slide1,
        R.layout.welcome_slide2,
        R.layout.welcome_slide3};
    addBottomDots(0);
    TextViewPlus termsTextViewPlus = (TextViewPlus) findViewById(R.id.termsTextView);
    termsTextViewPlus.setMovementMethod(LinkMovementMethod.getInstance());
    myViewPagerAdapter = new MyViewPagerAdapter();
    viewPager.setAdapter(myViewPagerAdapter);
    viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    mCallbackManager = CallbackManager.Factory.create();
    LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
    loginButton.setText("Sign in with Facebook");

    loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    float fbIconScale = 1.45F;
    Drawable drawable = this.getResources().getDrawable(
        com.facebook.R.drawable.com_facebook_button_icon);
    drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * fbIconScale),
        (int) (drawable.getIntrinsicHeight() * fbIconScale));
    loginButton.setCompoundDrawables(drawable, null, null, null);
    loginButton.setCompoundDrawablePadding(getResources().
        getDimensionPixelSize(R.dimen.fb_margin_override_textpadding));
    loginButton.setPadding(
        getResources().getDimensionPixelSize(
            R.dimen.fb_margin_override_lr),
        getResources().getDimensionPixelSize(
            R.dimen.fb_margin_override_top),
        0,
        getResources().getDimensionPixelSize(
            R.dimen.fb_margin_override_bottom));

    loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_location"));
    loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
      @Override
      public void onSuccess(LoginResult loginResult) {
        mAccessToken = loginResult.getAccessToken();
        showProgressDialog();
        handleFacebookAccessToken(loginResult.getAccessToken());
      }

      @Override
      public void onCancel() {
      }

      @Override
      public void onError(FacebookException error) {
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mCallbackManager.onActivityResult(requestCode, resultCode, data);
  }

  private void updateUI(final FirebaseUser user) {
    fetchTimeAndProfile(user);
  }

  public void setFcmToken() {
    String fcmToken = FirebaseInstanceId.getInstance().getToken();
    if (FirebaseAuth.getInstance().getCurrentUser() != null && fcmToken != null) {
      FirebaseDatabase.getInstance().getReference()
          .child(FirebaseClient.USER_FCM_REGISTRATION_TOKEN_CHILD)
          .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fcm_token")
          .setValue(fcmToken);
    }
  }

  private void fetchTimeAndProfile(final FirebaseUser user) {
    Map<String, String> parameters = new HashMap<String, String>();
    FirebaseClient.fetchUserProfile(user.getUid(), new FirebaseClient.FetchUserProfileListener() {
      @Override
      public void onComplete(UserProfile profile) {
        if (profile == null) {
          // start new user flow
          startNewUserFlow(user);
          return;
        }
        setFcmToken();
        final InitData initData = new InitData();
        initData.setUserProfile(profile);
        startMainActivity(initData);
        finish();
      }

      @Override
      public void onCancelled() {

      }
    });
  }

  private void startNewUserFlow(final FirebaseUser user) {
    final String[] firstName = new String[1];
    final String[] email = new String[1];
    final String[] gender = new String[1];
    final String[] largeProfileImage = new String[1];
    final String[] fbid = new String[1];
    final String[] lastName = new String[1];
    if (mAccessToken != null) {
      GraphRequest request = GraphRequest.newMeRequest(
          mAccessToken,
          new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
              if (response.getError() != null) {
                //todo handle error
              }
              try {
                firstName[0] = object.getString("first_name");
                lastName[0] = object.getString("last_name");
                email[0] = object.getString("email");
                gender[0] = object.getString("gender");
                fbid[0] = object.getString("id");
                if (object.has("picture")) {
                  largeProfileImage[0] = object.getJSONObject("picture")
                      .getJSONObject("data").getString("url");
                }
              } catch (JSONException e) {
                e.printStackTrace();
              }
              // I'll create the new user flow
              // go to new user flow
              final UserProfile userProfile = new UserProfile();
              userProfile.fname = firstName[0];
              userProfile.email = email[0];
              userProfile.gender = gender[0];
              userProfile.profilePicUrl = largeProfileImage[0];
              userProfile.fbid = fbid[0];
              userProfile.name = createFullName(firstName[0], lastName[0]);
              userProfile.id = user.getUid();
              userProfile.fcmRegistrationToken = FirebaseInstanceId.getInstance().getToken();
              //final UserProfile userProfile = new UserProfile(largeProfileImage[0],
              //		FirebaseClient.getCurrentUserId(), firstName[0], email[0],
              //		gender[0], 24, Arrays.asList("male", "female"), 20, 26);
              FirebaseClient.updateProfile(userProfile, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError,
                    DatabaseReference databaseReference) {
                  pauseThenFinish();
                }
              });

              finish();
            }
          });
      Bundle parameters = new Bundle();
      parameters.putString("fields",
          "id, email, first_name, last_name, picture.width(512).height(512), birthday, education, work, gender, location");
      request.setParameters(parameters);
      request.executeAsync();
    }
  }


  private static String createFullName(String firstName, String lastName) {
    if (firstName.length() > 12) {
      return firstName;
    } else {
      return firstName + " " + lastName;
    }
  }

  private void startMainActivity(InitData initData) {
    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    intent.putExtra(BaseActivity.EXTRA_INIT_DATA, initData);
    startActivity(intent);
    hideProgressDialog();
    finish();
  }

  private void handleFacebookAccessToken(AccessToken token) {
    showProgressDialog();
    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            updateUI(task.getResult().getUser());
            if (!task.isSuccessful()) {
              Toast.makeText(LoginActivity.this, "Authentication failed.",
                  Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  private void addBottomDots(int currentPage) {
    dots = new TextView[layouts.length];
    //int[] colorsActive = new int[]{R.color.accent,R.color.accent,R.color.accent,R.color.accent };
    //int[] colorsInactive = new int[]{R.color.disabled_grey,R.color.disabled_grey,R.color.disabled_grey,R.color.disabled_grey};
    dotsLayout.removeAllViews();
    for (int i = 0; i < dots.length; i++) {
      dots[i] = new TextView(this);
      dots[i].setText(Html.fromHtml("&#8226;"));
      dots[i].setTextSize(35);
      dots[i].setTextColor(getResources().getColor(R.color.disabled_grey));
      dotsLayout.addView(dots[i]);
    }

    if (dots.length > 0) {
      dots[currentPage].setTextColor(getResources().getColor(R.color.pink_active));
    }
    //dots[currentPage].setTextColor(colorsActive[currentPage]);
  }

  private int getItem(int i) {
    return viewPager.getCurrentItem() + i;
  }

  ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

    @Override
    public void onPageSelected(int position) {
      addBottomDots(position);
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }
  };

  /**
   * View pager adapter
   */
  public class MyViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;

    public MyViewPagerAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

      View view = layoutInflater.inflate(layouts[position], container, false);
      container.addView(view);

      return view;
    }

    @Override
    public int getCount() {
      return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
      return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      View view = (View) object;
      container.removeView(view);
    }
  }

  @Override
  public void onBackPressed() {
    // dont call **super**, if u want disable back button in current screen.
  }
}
