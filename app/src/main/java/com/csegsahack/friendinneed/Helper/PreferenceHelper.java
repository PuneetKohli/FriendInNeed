package com.csegsahack.friendinneed.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by townindore on 15/11/3.
 */
public class PreferenceHelper {

  public static final String SELECTED_CARE_PACKAGES           = "carepackage_set";
  public static final String INTRO_SHOWN_STATUS               = "intro_shown_status";
  public static final String LOCATION_DIALOG_SHOWN            = "LOCATION_DIALOG_SHOWN";
  public static final String LOCATION_PERMISSION_DIALOG_SHOWN = "LOCATION_PERMISSION_DIALOG_SHOWN";
  public static final String ACTIVATION_STATUS                = "is_activated";
  public static final String AGREE_TO_TERM_STATUS             = "agreed_to_tos";


  // return a set of the ids of selected care packages
  public static Set<String> getSelectedCarePackageIds(Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getStringSet(SELECTED_CARE_PACKAGES, new HashSet<String>());
  }

  // add an id of the selected care package to the sharedpreference
  public static void setSelectedCarePackageIdSet(Set<String> selectedCarePackages,
      Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putStringSet(SELECTED_CARE_PACKAGES, selectedCarePackages);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static void setIntroShown(Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(INTRO_SHOWN_STATUS, true);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static void resetIntroShown(Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(INTRO_SHOWN_STATUS, false);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static boolean isIntroShown(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getBoolean(INTRO_SHOWN_STATUS, false);
  }

  // add an id of the selected care package to the sharedpreference
  public static void setLocationDialogShown(Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(LOCATION_DIALOG_SHOWN, true);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static boolean isLocationDialogShown(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getBoolean(LOCATION_DIALOG_SHOWN, false);
  }

  // add an id of the selected care package to the sharedpreference
  public static void setLocationPermissionDialogShown(Context appcontext) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(LOCATION_PERMISSION_DIALOG_SHOWN, true);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static boolean isLocationPermissionDialogShown(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getBoolean(LOCATION_PERMISSION_DIALOG_SHOWN, false);
  }


  // add an id of the selected care package to the sharedpreference
  public static void setCareCentreId(Context appcontext, String centre_id) {

    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.CARE_CENTRE_ID, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putString(GlobalVar.CARE_CENTRE_ID, centre_id);
    editor.commit();
  }

  // add an id of the selected care package to the sharedpreference
  public static String getCareCentreId(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.CARE_CENTRE_ID, Context.MODE_PRIVATE);
    return appinfo.getString(GlobalVar.CARE_CENTRE_ID, "");
  }

  public static void setActivated(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(ACTIVATION_STATUS, true);
    editor.commit();
  }

  public static boolean isActivated(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getBoolean(ACTIVATION_STATUS, false);
  }

  public static void setAgreedToTerms(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putBoolean(AGREE_TO_TERM_STATUS, true);
    editor.commit();
  }

  public static boolean isAgreedToTerms(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getBoolean(AGREE_TO_TERM_STATUS, false);
  }

  public static void setUniqueId(Context appcontext, String uniqueId) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = appinfo.edit();
    editor.putString(GlobalVar.UNIQUE_ID, uniqueId);
    editor.commit();
  }

  public static String getUniqueId(Context appcontext) {
    SharedPreferences appinfo = appcontext
        .getSharedPreferences(GlobalVar.APP_INFO, Context.MODE_PRIVATE);
    return appinfo.getString(GlobalVar.UNIQUE_ID, "");
  }
}
