package com.csegsahack.friendinneed.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.csegsahack.friendinneed.Helper.PreferenceHelper;
import com.csegsahack.friendinneed.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


//TODO: Check if needed
public class AppIntroActivity extends AppIntro {

  //private Realm       realm;
  //private RealmHelper realmHelper;

  // Please DO NOT override onCreate. Use init
  @Override
  public void init(Bundle savedInstanceState) {

    //realm = Realm.getDefaultInstance();
    //realmHelper = new RealmHelper();
    String slide1_title = getResources().getString(R.string.intro_slide1_title);
    String slide1_content = getResources().getString(R.string.intro_slide1_content);

    String slide2_title = getResources().getString(R.string.intro_slide2_title);
    String slide2_content = getResources().getString(R.string.intro_slide2_content);

    String slide3_title = getResources().getString(R.string.intro_slide3_title);
    String slide3_content = getResources().getString(R.string.intro_slide3_content);

    // Instead of fragments, you can also use our default slide
    // Just set a title, description, background and image. AppIntro will do the rest

    addSlide(AppIntroFragment.newInstance("", "", R.drawable.dashmd_bg,
        getResources().getColor(R.color.white)));
    addSlide(AppIntroFragment.newInstance(slide1_title, slide1_content, R.drawable.ic_introslide1,
        getResources().getColor(R.color.colorPrimary)));
    addSlide(AppIntroFragment.newInstance(slide2_title, slide2_content, R.drawable.ic_introslide2,
        getResources().getColor(R.color.colorPrimary)));
    addSlide(AppIntroFragment.newInstance(slide3_title, slide3_content, R.drawable.ic_introslide3,
        getResources().getColor(R.color.colorPrimary)));

    // OPTIONAL METHODS
    // Override bar/separator color
    setBarColor(Color.parseColor("#3F51B5"));
    setSeparatorColor(Color.parseColor("#2196F3"));

    // Hide Skip/Done button
    showSkipButton(true);
    showDoneButton(true);

    //TODO: load Realm data
  }

  @Override
  public void onSkipPressed() {
    PreferenceHelper.setIntroShown(getApplicationContext());
    Intent mIntent = new Intent(AppIntroActivity.this, MainActivity.class);
    startActivity(mIntent);
    this.finish();
  }

  @Override
  public void onNextPressed() {

  }

  @Override
  public void onDonePressed() {
    PreferenceHelper.setIntroShown(getApplicationContext());
    Intent mIntent = new Intent(AppIntroActivity.this, MainActivity.class);
    startActivity(mIntent);
    this.finish();
  }

  @Override
  public void onSlideChanged() {
  }

  @Override
  public void onBackPressed() {
  }
}