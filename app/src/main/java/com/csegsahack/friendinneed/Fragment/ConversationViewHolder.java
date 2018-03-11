package com.csegsahack.friendinneed.Fragment;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csegsahack.friendinneed.Model.Conversation;
import com.csegsahack.friendinneed.R;
import com.csegsahack.friendinneed.util.CustomFontHelper;
import com.csegsahack.friendinneed.view.InverseProgressBar;
import com.csegsahack.friendinneed.view.TextViewPlus;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * Created by charlesmarino on 5/25/16.
 */
public class ConversationViewHolder extends RecyclerView.ViewHolder {

    private TextViewPlus       messageTextView;
    private TextViewPlus       nameTextView;
    private CircularImageView  senderImage;
    private InverseProgressBar progressBar;

    public ConversationViewHolder(View itemView) {
        super(itemView);
        messageTextView = (TextViewPlus) itemView.findViewById(R.id.message_text_view);
        nameTextView = (TextViewPlus) itemView.findViewById(R.id.nameTextView);
        senderImage = (CircularImageView) itemView.findViewById(R.id.senderImage);
        progressBar = (InverseProgressBar) itemView.findViewById(R.id.progress_bar);
    }

    public void bindToConversationCell(Conversation conversation) {

            messageTextView.setTextColor(messageTextView.getResources().getColor(R.color.dark_text));
            CustomFontHelper.setCustomFont(messageTextView, "fonts/Raleway-Bold.ttf",messageTextView.getContext());
            CustomFontHelper.setCustomFont(nameTextView, "fonts/Raleway-Bold.ttf",nameTextView.getContext());

            progressBar.setVisibility(View.GONE);
            this.messageTextView.setText("Tap for message");
        this.nameTextView.setText("Anonymous");

    }
}
