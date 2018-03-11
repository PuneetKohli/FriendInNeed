package com.csegsahack.friendinneed.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.csegsahack.friendinneed.Activity.BaseActivity;
import com.csegsahack.friendinneed.R;
import com.csegsahack.friendinneed.service.FirebaseClient;
import com.google.firebase.auth.UserInfo;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by puneet on 11/11/17.
 */

public class AddMessageFragment extends Fragment {

  EditText editText;
  Button   button;
  private ProgressBar mSpinner;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    //fragment_chat_list is the view that's used for the conversations
    View rootView = inflater.inflate(R.layout.fragment_add_message, container, false);
    button = (Button) rootView.findViewById(R.id.button_send_message);
    editText = (EditText) rootView.findViewById(R.id.edittext_message);

    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (editText.getText() != null) {
          sendMessage(editText.getText().toString());
        }
      }
    });
    return rootView;
  }

  void sendMessage(String text) {

    try {
      text = URLEncoder.encode(text, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    String url = "https://us-central1-friendinneed369.cloudfunctions.net/addMessage?";

    String params = ("from_user=" + ((BaseActivity) getActivity()).getInitData()
        .getUserProfile().id);
    params += ("&type=" + "message");
    params += ("&content=" + text);
    params += ("&is_anon=" + true);

    String userId = ((BaseActivity) getActivity()).getInitData().getUserProfile().id;

    ((BaseActivity) getActivity()).showProgressDialog();
    JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url + params,
        new JSONObject(),
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.d("Send message", "" + response.toString());
            ((BaseActivity) getActivity()).hideProgressDialog();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage(
                "We've notified your network. Stay strong - there are people who care about you that will do their best to help you out!");
            builder1.setPositiveButton(
                "Thanks!",
                new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                  }
                });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            editText.setText("");
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.d("Send message", "Error" + error);
        ((BaseActivity) getActivity()).hideProgressDialog();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(
            "We've notified your network. Stay strong - there are people who care about you that will do their best to help you out!");
        builder1.setPositiveButton(
            "Thanks!",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        editText.setText("");
      }
    });
    Volley.newRequestQueue(getContext()).add(req);
  }

  private void getSentiment() throws JSONException {
    String url = "http://text-processing.com/api/sentiment/";
    final JSONObject jsonBody = new JSONObject();
    jsonBody.put("text", "I want to commit suicide i am so lonely. please help.");
    final JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {

            Log.d("TAG", "Got response" + response);
          }
        }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.d("TAG", "Got error" + error);
      }
    });
    Log.d("TAG", "My req is " + req);
    Volley.newRequestQueue(getContext()).add(req);
  }
}
