package com.csegsahack.friendinneed.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by puneet on 11/11/17.
 */

public class Conversation {

  private String thumbnailUri;
  private String matchUid;
  private String matchName;
  private String matchMessage;
  private String chatId;
  private Boolean isRead;
  private Boolean isExpiring;
  private Double updateTime;


  public String getThumbnailUri() {
    return thumbnailUri;
  }

  public String getMatchUid() {
    return matchUid;
  }

  public String getMatchName() {
    return matchName;
  }

  public String getMatchMessage() {
    return matchMessage;
  }

  public String getChatId() {
    return chatId;
  }

  public Boolean getIsRead() {
    return isRead;
  }

  public Boolean getIsExpiring() {
    return isExpiring;
  }

  public Double getUpdateTime() {
    return updateTime;
  }

  public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("thumbnailUri", thumbnailUri);
    result.put("matchUid", matchUid);
    result.put("matchName", matchName);
    result.put("matchMessage", matchMessage);
    result.put("chatId", chatId);
    result.put("isRead", isRead);
    return result;
  }

}
