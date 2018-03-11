package com.csegsahack.friendinneed.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.csegsahack.friendinneed.R;

/**
 * Created by puneet on 11/11/17.
 */

public class ChatActivity extends BaseActivity {

  public static final String EXTRA_THIS_USER_PROFILE = "this_user_profile";
  public static final String EXTRA_CHAT_KEY          = "chat_key";
  public static final String EXTRA_MATCH_UID         = "match_uid";
  public static final String EXTRA_MATCH_NAME        = "match_name";
  public static final String EXTRA_MATCH_PIC         = "match_pic_uri";
  public static final String EXTRA_MATCH_UPDATE_TIME = "match_update_time";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    TextView textView = (TextView) findViewById(R.id.text_message);
    textView.setText(
        "I am very depressed nowadays. My Girlfriend left me recently, and I am feeling very lonely. This is the first time I have been single in 5 years and I can not bear this feeling anymore.");
  }
}
