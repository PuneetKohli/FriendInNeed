package com.csegsahack.friendinneed.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by charlesmarino on 10/19/16.
 */


public class ProgressDialogActivity extends AppCompatActivity {

	private ProgressDialog mProgressDialog;
	private static final String TAG = ProgressDialogActivity.class.getSimpleName();
	private long mTimeDialogueStarted;
	private static final long MIN_TIME_DURATION_MS = 500;

	public void showProgressDialog() {
		mTimeDialogueStarted = System.currentTimeMillis();
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setMessage("Loading...");
		}
		mProgressDialog.show();
	}

	public void hideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	public VectorDrawableCompat getVectorDrawable(int resource) {
		return VectorDrawableCompat.create(getResources(), resource, getTheme());
	}

	public void hideKeyboard() {
		InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
	}

	public long timeToMinDuration(){
		long currentTime = System.currentTimeMillis();
		long minTimeDuration = MIN_TIME_DURATION_MS - (currentTime - mTimeDialogueStarted);
		return minTimeDuration > 0 ? minTimeDuration : 0;
	}

	public void pauseThenFinish(){
		Handler handler = new Handler();
		if (timeToMinDuration() > 10){
			handler.postDelayed(new Runnable() {
				public void run() {
					finish();
				}
			}, timeToMinDuration());
		} else {
			finish();
		}

	}

}