package com.example.restaurtantchatbotapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurtantchatbotapp.adapters.ChatAdapter;
import com.example.restaurtantchatbotapp.helpers.SendMessageInBg;
import com.example.restaurtantchatbotapp.interfaces.BotReply;
import com.example.restaurtantchatbotapp.models.Message;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatbotClass extends AppCompatActivity implements BotReply {

    RecyclerView chatView;
    ChatAdapter chatAdapter;
    List<Message> messageList = new ArrayList<>();
    EditText editMessage;
    ImageButton btnSend;

    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private String uuid = UUID.randomUUID().toString();
    private String TAG = "Chatbot_Activity_Main";

    //onCreate is where we are connecting the app to the dialogflow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_chatbot);
        chatView = findViewById(R.id.chatView);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);

        chatAdapter = new ChatAdapter(messageList, this);
        chatView.setAdapter(chatAdapter);
        //listens to the send button. take the message, check if its empty and gives a toast. Put it in messageList.
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                String message = editMessage.getText().toString();
                if (!message.isEmpty()) {
                    messageList.add(new Message(message, false));
                    editMessage.setText("");
                    //sends the message to bot when not empty and notify for changes
                    sendMessageToBot(message);
                    Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
                    Objects.requireNonNull(chatView.getLayoutManager())
                            .scrollToPosition(messageList.size() - 1);
                    //makes a scroll, automatically we set it to size -1 so it goes to adapter position
                } else {
                    Toast.makeText(ChatbotClass.this, "Please enter text!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setUpBot();
    }
    //setting up the bot with a try catch. Catch the exception or connect to the bot if no errors are found
    private void setUpBot() {
        try {
            //refer to credential file.
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            //Add google credentials
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            //get project id
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            //create sessionClient to build the actual session using the credentials above
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);
            //logcat to make sure the session is created correctly
            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }
    //called to send the actual user message to the bot. Gets passed the message of the user
    private void sendMessageToBot(String message) {
        //QueryInput is a function from google to make it easier to send over the internet.
        //Query can be in the form of speech, event but we have it as text
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        //calls the class SendMessageInBg, passes the sessionName, sessionClient and the message of the user to be executed.
        new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
    }

    //takes the response of the bot
    @Override //overriding from the class "BotReply" inside it method callback
    public void callback(DetectIntentResponse returnResponse) {
        //if the response isn't empty, put it in botReply
        if(returnResponse!=null) {
            String botReply = returnResponse.getQueryResult().getFulfillmentText();
            //if not null or not empty, put it in the messageList. Set isReceived to true. Update the chatAdapter (view) and put the reply to the bottom
            if(!botReply.isEmpty()){
                messageList.add(new Message(botReply, true));
                chatAdapter.notifyDataSetChanged();
                Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
            }else {
                //check for error
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
        }
    }
}
