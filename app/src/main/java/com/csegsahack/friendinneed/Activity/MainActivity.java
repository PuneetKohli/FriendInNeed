package com.csegsahack.friendinneed.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.csegsahack.friendinneed.Fragment.AddMessageFragment;
import com.csegsahack.friendinneed.Fragment.MatchListFragment;
import com.csegsahack.friendinneed.Model.InitData;
import com.csegsahack.friendinneed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

  private static final int    CHAT_TAB_POSITION = 1;

  private MatchListFragment  mMatchListFragment;
  private AddMessageFragment mAddMessageFragment;

  private AHBottomNavigation mBottomNavigation;
  private Integer            currentTabIndex;

  private   ValueEventListener mNotificationListener;
  private DatabaseReference  mNotificationReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    InitData initData = getIntent().getParcelableExtra(EXTRA_INIT_DATA);
    if (initData != null) {
      setInitData(initData);
    }

    currentTabIndex = 0;
    setUpBottomBar();
    setupBottomBarFragments(currentTabIndex);
  }



  private AHBottomNavigation setUpBottomBar() {
    mBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
    mBottomNavigation.removeAllItems();
    AHBottomNavigationItem item1 = new AHBottomNavigationItem("Get Help",
        getVectorDrawable(R.drawable.ic_feed));
    AHBottomNavigationItem item2 = new AHBottomNavigationItem("My Network",
        getVectorDrawable(R.drawable.ic_chat));
    AHBottomNavigationItem item3 = new AHBottomNavigationItem("Settings",
        getVectorDrawable(R.drawable.ic_settings));
    AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile",
        getVectorDrawable(R.drawable.ic_profile));
    mBottomNavigation.addItem(item1);
    mBottomNavigation.addItem(item2);

    // Change colors
    mBottomNavigation
        .setDefaultBackgroundColor(getResources().getColor(R.color.background_grey_white));
    mBottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
    mBottomNavigation.setInactiveColor(getResources().getColor(R.color.disabled_grey));

    // Force the titles to be displayed (against Material Design guidelines!)

    //mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

    // Add notification listener
    mNotificationListener = new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        long count = dataSnapshot.getChildrenCount();
        final String notificationText = (count > 0) ? String.valueOf(count) : "";
        mBottomNavigation.setNotification(notificationText, CHAT_TAB_POSITION);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
      }
    };
    mBottomNavigation
        .setNotificationBackgroundColor(getResources().getColor(R.color.notifications_color));

    return mBottomNavigation;
  }

  private void setupBottomBarFragments(int position) {
    if (findViewById(R.id.fragmentContainer) != null) {
      // However, if we're being restored from a previous state,
      // then we don't need to do anything and should return or else
      // we could end up with overlapping fragments.

      FrameLayout fl = (FrameLayout) findViewById(R.id.fragmentContainer);
      fl.removeAllViewsInLayout();
      FragmentManager fm = getSupportFragmentManager();
      removeAllFragments(fm);
      setmAddMessageFragment(fm);
      setupMatchListFragment(fm);

      mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
        @Override
        public boolean onTabSelected(int position, boolean wasSelected) {
          currentTabIndex = position;
          //TODO scroll to top if tab is already selected and clicked on again
          showFragment(position);
          return true;
        }
      });
      mBottomNavigation.setCurrentItem(position);
    }
  }

  private void showFragment(int position) {
    switch (position) {
      case 0:
        showFragment(mAddMessageFragment,
            Arrays.<Fragment>asList(mMatchListFragment));
        break;
      case 1:
        showFragment(mMatchListFragment,
            Arrays.<Fragment>asList(mAddMessageFragment));
        break;
      /*case 2:
        showFragment(mSettingsFragment,
            Arrays.asList(mFeedFragment, mMatchListFragment, mProfileFragment));
        break;
      case 3:
        showFragment(mProfileFragment,
            Arrays.asList(mFeedFragment, mSettingsFragment, mMatchListFragment));
        break;*/
    }
  }

  private void removeAllFragments(FragmentManager fm) {
    Fragment fragment = fm.findFragmentByTag(AddMessageFragment.class.toString());
    Fragment fragment2 = fm.findFragmentByTag(MatchListFragment.class.toString());
    //Fragment fragment3 = fm.findFragmentByTag(ProfileFragment.class.toString());

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    if (fragment != null) {
      ft.remove(fragment);
    }
    if (fragment2 != null) {
      ft.remove(fragment2);
    }
    /*if (fragment3 != null) {
      ft.remove(fragment3);
    }*/

    ft.commit();
  }


  private void setupMatchListFragment(FragmentManager fm) {
    if (mMatchListFragment == null) {
      mMatchListFragment = new MatchListFragment();
    }
  }

  private void setmAddMessageFragment(FragmentManager fm) {
    if (mAddMessageFragment == null) {
      mAddMessageFragment = new AddMessageFragment();
    }
  }

  private void showFragment(Fragment fragment, List<Fragment> otherFragments) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    if (fragment.isVisible()) {
      return;
    }
    if (fragment.isAdded()) {
      fragmentTransaction.show(fragment);
    } else {
      fragmentTransaction.add(R.id.fragmentContainer, fragment, fragment.getClass().toString());
    }
    for (Fragment otherFragment : otherFragments) {
      if (otherFragment.isAdded()) {
        fragmentTransaction.hide(otherFragment);
      }
    }
    fragmentTransaction.commit();
  }

}
