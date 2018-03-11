package com.csegsahack.friendinneed.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.csegsahack.friendinneed.R;

/**
 * Created by salscrudato on 11/11/16.
 */

public class DoubleTextViewList extends LinearLayout {

    public DoubleTextViewList(Context context) {
        super(context);
        initializeViews(context);
    }

    public DoubleTextViewList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public DoubleTextViewList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DoubleTextViewList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
/*        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.double_text_view_row, this);*/
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public void addViewFromList(String header, String subHeader){
        //i dont really understand this line below
        View layout2 = LayoutInflater.from(this.getContext()).inflate(R.layout.double_text_view_row, this, false);
        TextView rowHeader = (TextViewPlus) layout2.findViewById(R.id.header);
        TextView rowSubHeader = (TextViewPlus) layout2.findViewById(R.id.subheader);
        ImageButton deleteBtn = (ImageButton) layout2.findViewById(R.id.delete_work_exp);
        rowHeader.setText(header);
        rowSubHeader.setText(subHeader);
        this.addView(layout2);
    }

}
