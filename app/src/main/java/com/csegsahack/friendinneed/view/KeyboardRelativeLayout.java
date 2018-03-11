package com.csegsahack.friendinneed.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

/**
 * Created by charlesmarino on 2/2/17.
 */

public class KeyboardRelativeLayout extends RelativeLayout {
    private Activity mActivity;

    public KeyboardRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardRelativeLayout(Context context) {
        super(context);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    /**
     * Overrides the handling of the back key to move back to the
     * previous sources, instead of dismissing the input method.
     */
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (mActivity != null &&
                event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getRepeatCount() == 0) {
                    state.startTracking(event, this);
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP
                        && !event.isCanceled() && state.isTracking(event)) {
                    mActivity.onBackPressed();
                    return true;
                }
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }
}