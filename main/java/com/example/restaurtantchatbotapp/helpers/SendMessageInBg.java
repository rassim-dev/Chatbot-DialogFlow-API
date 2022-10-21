package com.example.restaurtantchatbotapp.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.restaurtantchatbotapp.interfaces.BotReply;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
//reference https://developer.android.com/reference/android/os/AsyncTask
//That is for the class file packages from for dialog flow that we are using aka overriding and inputting our code DetectIntentResponse,doinBackground
//AsyncTask is used mostly as a handler and thread helper class to help make sure there isn't any crashes and makes sures message is sent and received
public class SendMessageInBg extends AsyncTask<Void, Void, DetectIntentResponse> {
// class AsyncTask has 3 parameters , we are not using 1st two, but the last one, result)

  private SessionName session;
  private SessionsClient sessionsClient;
  private QueryInput queryInput;
  private String TAG = "async";
  private BotReply botReply;
//listens for messages in the background so dialogflow responds faster. To make sure the message is being sent properly to the bot
  public SendMessageInBg(BotReply botReply,SessionName session, SessionsClient sessionsClient,
      QueryInput queryInput) {
    this.botReply = botReply;
    this.session = session;
    this.sessionsClient = sessionsClient;
    this.queryInput = queryInput;
  }
// in the background it keeps trying to connect to the bot till the correct information is passed. And fetch the response from the bot.
  @Override // overrides from the class Async,DetectIntentResponse
  protected DetectIntentResponse doInBackground(Void... voids) {
    try {
      DetectIntentRequest detectIntentRequest =
          DetectIntentRequest.newBuilder()
              .setSession(session.toString())
              .setQueryInput(queryInput)
              .build();
      return sessionsClient.detectIntent(detectIntentRequest);
    }
    catch (Exception e) {
      Log.d(TAG, "doInBackground: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override

  //DetectIntentResponse is a class file from the dialog flow package
  // so we are calling it by overriding then calling back the response interfact class botReply
  // it using response we get from the DetectIntentResponse result/response from doInBackGround
  protected void onPostExecute(DetectIntentResponse response) {
    //handle return bot response here
    botReply.callback(response);
  }
}