package com.csegsahack.friendinneed.Fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.csegsahack.friendinneed.Activity.BaseActivity;
import com.csegsahack.friendinneed.Activity.ChatActivity;
import com.csegsahack.friendinneed.Model.Conversation;
import com.csegsahack.friendinneed.Model.UserProfile;
import com.csegsahack.friendinneed.R;
import com.csegsahack.friendinneed.service.FirebaseClient;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MatchListFragment extends Fragment {

	private ProgressBar mSpinner;
	private static final String TAG = MatchListFragment.class.getSimpleName();
	private FirebaseRecyclerAdapter<Conversation, ConversationViewHolder> mConversationsAdapter;
	private RecyclerView                                                  mRecycler;
	private DatabaseReference                                             mNotificationReference;
	private ValueEventListener                                            mNotificationListener;
	private RelativeLayout                                                mNoConversationsView;
	public MatchListFragment() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		//fragment_chat_list is the view that's used for the conversations
		View rootView = inflater.inflate(R.layout.fragment_chat_list, container, false);
		mRecycler = (RecyclerView) rootView.findViewById(R.id.recyclerview);
		mNoConversationsView = (RelativeLayout) rootView.findViewById(R.id.noConversationsView);
		//mNotificationReference = FirebaseClient.getNotificationReference();
		mSpinner = (ProgressBar) rootView.findViewById(R.id.progressCircle);
		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mSpinner.setVisibility(View.VISIBLE);
		mSpinner.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.lightpink), PorterDuff.Mode.MULTIPLY);
		Query conversationsQuery = FirebaseClient.getConversationsRef();
		conversationsQuery.limitToFirst((1)).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (!dataSnapshot.exists()) {
					mNoConversationsView.setVisibility(View.VISIBLE);
					mSpinner.setVisibility(View.GONE);
				}
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
		mConversationsAdapter = new FirebaseRecyclerAdapter<Conversation, ConversationViewHolder>(Conversation.class, R.layout.conversation_row,
				ConversationViewHolder.class, FirebaseClient.getConversationsQuery().limitToFirst(100)) {
			@Override
			protected void populateViewHolder(final ConversationViewHolder viewHolder, final Conversation model, final int position) {
				mNoConversationsView.setVisibility(View.GONE);
				mSpinner.setVisibility(View.GONE);
				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// When the current user clicks on a message, mark that notification as read
						Intent intent = new Intent(getActivity(), ChatActivity.class);
						startActivity(intent);
					}
				});
				// Bind Post to ViewHolder, setting OnClickListener for the accept button
				//Basically Bind to Conversation
				viewHolder.bindToConversationCell(model);
			}
		};
		final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
		mRecycler.setLayoutManager(linearLayoutManager);
		// Set up FirebaseRecyclerAdapter with the recycelr
		mRecycler.setAdapter(mConversationsAdapter);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		/*if (mConversationsAdapter != null) {
			mConversationsAdapter.cleanup();
		}*/
		if (mNotificationListener != null) {
			mNotificationReference.removeEventListener(mNotificationListener);
		}
	}
}