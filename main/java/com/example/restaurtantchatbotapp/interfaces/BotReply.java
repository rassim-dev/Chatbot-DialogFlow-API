package com.example.restaurtantchatbotapp.interfaces;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;

//detects the intent of the dialogflow (provided by Google)
// Takes the Bot reply aka returnResponse to the MainActivity2
public interface BotReply {

  void callback(DetectIntentResponse returnResponse);
}
