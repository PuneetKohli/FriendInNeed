package com.csegsahack.friendinneed.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.csegsahack.friendinneed.R;
import java.util.ArrayList;

/**
 * Created by salscrudato on 11/11/16.
 */

public class TagsView extends LinearLayout {
    private ArrayList<String> tagStrings = new ArrayList<String>();
    private ArrayList<TextView> tags;
    public TagsView(Context context) {
        super(context);
        initializeViews(context);
    }

    public TagsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public TagsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tags_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tags = new ArrayList<TextView>() {
            {
                add((TextView) findViewById(R.id.tagOneOne));
                add((TextView) findViewById(R.id.tagOneTwo));
                add((TextView) findViewById(R.id.tagOneThree));
                add((TextView) findViewById(R.id.tagTwoOne));
                add((TextView) findViewById(R.id.tagTwoTwo));
            }
        };
        this.setGravity(Gravity.CENTER_HORIZONTAL);
        this.setOrientation(VERTICAL);
    }

    public void setRemoveTagsOnClickListeners() {
        OnClickListener tagClickedListener = new OnClickListener() {
            @Override
            public void onClick(final View view) {
                //TODO add dialog
                if(!(view instanceof TextView)) return;
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to delete this tag?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                TextView selectedTextView = (TextView) view;
                                String selectedTag = selectedTextView.getText().toString();
                                for (int i = 0; i < tagStrings.size(); i++){
                                    if(tagStrings.get(i).equals(selectedTag)){
                                        tagStrings.remove(i);
                                    }
                                }
                                setTagsFromList();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                Typeface face=Typeface.createFromAsset(getContext().getAssets(),"fonts/Raleway-SemiBold.ttf");
                textView.setTextColor(getResources().getColor(R.color.dark_text));
                textView.setTypeface(face);
            }
        };

        for(TextView item : tags){
            item.setOnClickListener(tagClickedListener);
        }

    }

    public void addTag(String tag){
        //TODO check for dups, if dup, then dont add
        tagStrings.add(tag);
        setTagsFromList();

    }

    public int size(){
        return tagStrings.size();
    }

    public ArrayList<String> getTagStrings(){
        ArrayList<String> clone = new ArrayList<String>();
        for (String item : tagStrings){
            clone.add(new String(item));
        }
        return clone;
    }

    public void setTagsFromList() {
        TextView tempTextView;

        for (int i = 0; i < tags.size() ; i++) {
            tempTextView = tags.get(i);
            tempTextView.setText("");
            if (i < tagStrings.size()) {
                tempTextView.setText(tagStrings.get(i));
                tempTextView.setVisibility(VISIBLE);
            } else {
                tempTextView.setVisibility(GONE);
            }
        }
    }

}
