package com.csegsahack.friendinneed.service;

import android.support.annotation.Nullable;
import com.csegsahack.friendinneed.Model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by charlesmarino on 10/19/16.
 */
public class FirebaseClient {

  private static final String USER_REQUESTS_NODE      = "user-requests";
  private static final String USER_SEEN_NODE          = "user-seen";
  private static final String USER_FEED_NODE          = "user-feed";
  private static final String USER_CONVERSATIONS_NODE = "messages";
  private static final String USER_PROFILE_NODE       = "user-profile";
  private static final String CHAT_MESSAGES_NODE      = "chat-messages";
  private static final String NOTIFICATION_NODE       = "user-notifications-unread-matches";

  public static final String USER_FEED_TIMESTAMP_CHILD         = "user-feed-changed-timestamp";
  public static final String USER_FEED_CHILD                   = "user-feed";
  public static final String USER_PROFILE_CHILD                = "user-profile";
  public static final String USER_FCM_REGISTRATION_TOKEN_CHILD = "user-fcmtoken";

  private static final String USER_PROFILE_WORK_NODE      = "work";
  private static final String USER_PROFILE_EDUCATION_NODE = "education";

  @Nullable
  public static String getCurrentUserId() {
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    return currentUser.getUid();
  }


  public static DatabaseReference getDatabaseReference() {
    return FirebaseDatabase.getInstance().getReference();
  }

  public static DatabaseReference getNotificationReference() {
    return getDatabaseReference().child(NOTIFICATION_NODE).child(getCurrentUserId());
  }

  public static DatabaseReference getConversationsRef() {
    return getDatabaseReference().child(USER_CONVERSATIONS_NODE).child(getCurrentUserId());
  }

  public static Query getConversationsQuery() {
    return getDatabaseReference().child(USER_CONVERSATIONS_NODE);
  }

  public static Query getConversationsNewMatchQuery() {
    return getDatabaseReference().child(USER_CONVERSATIONS_NODE).child(getCurrentUserId())
        .orderByChild("creationTime").startAt(new Date().getTime() / 1000);
  }

  public static Query getFAQQuery() {
    return getDatabaseReference().child("faq").limitToFirst(100);
  }

  public static Query getResourcesQuery() {
    return getDatabaseReference().child("resources").limitToFirst(100);
  }

  public static DatabaseReference getChatMessagesRef(String chatId) {
    return getDatabaseReference().child(CHAT_MESSAGES_NODE).child(chatId);
  }

  public static Query getChatMessagesQuery(String chatId) {
    return getChatMessagesRef(chatId).limitToFirst(100);
  }

  public static Query getHasChatMessagesQuery(String chatId) {
    return getChatMessagesRef(chatId).limitToFirst(1);
  }

  public static Query getLocationModeSettingQuery() {
    return getDatabaseReference().child(USER_PROFILE_NODE).child(getCurrentUserId())
        .child("location_mode");
  }

  public static Query getNotificationSettingQuery() {
    return getDatabaseReference().child(USER_FCM_REGISTRATION_TOKEN_CHILD).child(getCurrentUserId())
        .child("notification_flag");
  }

  public static void fetchUserProfile(String userUid, final FetchUserProfileListener callback) {
    final DatabaseReference userProfileRef = getDatabaseReference().child("user-profile")
        .child(userUid);
    userProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
        UserProfile profile = snapshot.getValue(UserProfile.class);
        callback.onComplete(profile);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        callback.onCancelled();
      }
    });
  }

  public interface FetchUserProfileListener {

    void onComplete(UserProfile profile);

    void onCancelled();
  }

  public static void updateProfile(UserProfile profile,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference userProfileRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId());
    if (completionListener == null) {
      userProfileRef.setValue(profile);
    } else {
      userProfileRef.setValue(profile, completionListener);
    }
  }

  public static void updateAboutMe(String aboutMe,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference aboutMeRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("aboutMe");
    if (completionListener == null) {
      aboutMeRef.setValue(aboutMe);
    } else {
      aboutMeRef.setValue(aboutMe, completionListener);
    }
  }

  public static void updateSkill(String skill,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference skillRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("skill");
    if (completionListener == null) {
      skillRef.setValue(skill);
    } else {
      skillRef.setValue(skill, completionListener);
    }
  }

  public static void updateLookingFor(ArrayList<String> lookingFor,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference aboutMeRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("looking_for");
    if (completionListener == null) {
      aboutMeRef.setValue(lookingFor);
    } else {
      aboutMeRef.setValue(lookingFor, completionListener);
    }
  }

  public static void updateTags(ArrayList<String> tags,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference aboutMeRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("tags");
    if (completionListener == null) {
      aboutMeRef.setValue(tags);
    } else {
      aboutMeRef.setValue(tags, completionListener);
    }
  }

  public static void updateLocationModeToggle(Boolean locationModeEnabled,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference locationModeRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("location_mode");
    if (completionListener == null) {
      locationModeRef.setValue(locationModeEnabled);
    } else {
      locationModeRef.setValue(locationModeEnabled, completionListener);
    }
  }

  public static void updateRealLocation(Boolean locationModeEnabled,
      @Nullable DatabaseReference.CompletionListener completionListener) {
    DatabaseReference locationModeRef = getDatabaseReference().child(USER_PROFILE_NODE)
        .child(getCurrentUserId()).child("real_location");
    if (completionListener == null) {
      locationModeRef.setValue(locationModeEnabled);
    } else {
      locationModeRef.setValue(locationModeEnabled, completionListener);
    }
  }

  public static void markNotificationAsRead(String matchUid, Boolean conversationRemoved) {
    // Clear the notification
    getDatabaseReference().child(NOTIFICATION_NODE).child(getCurrentUserId()).child(matchUid)
        .removeValue();
    // Mark as read
    if (!conversationRemoved) {
      getDatabaseReference().child(USER_CONVERSATIONS_NODE).child(getCurrentUserId())
          .child(matchUid).child("isRead").setValue(true);
    }
  }

  public static void swipeNo(String seenUserId) {
    // Clear the notification
    getDatabaseReference().child(USER_SEEN_NODE).child(getCurrentUserId()).child(seenUserId)
        .setValue(true);
    // Mark as read
    getDatabaseReference().child(USER_REQUESTS_NODE).child(getCurrentUserId()).child(seenUserId)
        .removeValue();
    getDatabaseReference().child(USER_FEED_NODE).child(getCurrentUserId()).child(seenUserId)
        .removeValue();
  }

  public static void sendMessage(String userId, String text) {
    HashMap<String, Object> chatMessage = new HashMap<>();
    chatMessage.put("time", ServerValue.TIMESTAMP);
    chatMessage.put("content", text);
    chatMessage.put("from_user", userId);
    chatMessage.put("is_anon", true);
    getDatabaseReference().child("messages").push().setValue(chatMessage);
  }
}
