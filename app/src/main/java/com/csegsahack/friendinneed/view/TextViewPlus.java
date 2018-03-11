package com.csegsahack.friendinneed.view;

/**
 * Created by charlesmarino on 11/29/16.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.csegsahack.friendinneed.util.CustomFontHelper;

public class TextViewPlus extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "TextView";

    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

}