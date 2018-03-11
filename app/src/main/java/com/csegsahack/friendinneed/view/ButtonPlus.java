package com.csegsahack.friendinneed.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.csegsahack.friendinneed.util.CustomFontHelper;

/**
 * Created by charlesmarino on 11/29/16.
 */

public class ButtonPlus extends android.support.v7.widget.AppCompatButton {
    public ButtonPlus(Context context) {
        super(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }
}
