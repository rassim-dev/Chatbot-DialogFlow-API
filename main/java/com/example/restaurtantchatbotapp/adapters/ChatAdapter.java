package com.example.restaurtantchatbotapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurtantchatbotapp.R;
import com.example.restaurtantchatbotapp.models.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

  private List<Message> messageList;
  private Activity activity;

  public ChatAdapter(List<Message> messageList, Activity activity) {
    this.messageList = messageList;
    this.activity = activity;
  }

  @NonNull @Override
  //RecyclerView adapter class method, recycler needs new holder in order to show the data
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity).inflate(R.layout.fragment_adapter_chat, parent, false);
    return new MyViewHolder(view);
  }
//RecyclerView adapter class method, displays the message that we currently have (response or sent message)
  @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    String message = messageList.get(position).getMessage();
    boolean isReceived = messageList.get(position).getIsReceived();
     if(!isReceived){

       holder.messageSend.setVisibility(View.VISIBLE);
       holder.messageReceive.setVisibility(View.GONE);
       holder.messageSend.setText(message);
     }else {
       holder.messageReceive.setVisibility(View.VISIBLE);
       holder.messageSend.setVisibility(View.GONE);
       holder.messageReceive.setText(message);
     }
  }

  @Override public int getItemCount() {
    return messageList.size();
  }

  static class MyViewHolder extends RecyclerView.ViewHolder{

    TextView messageSend;
    TextView messageReceive;

    MyViewHolder(@NonNull View itemView) {
      super(itemView);
      messageSend = itemView.findViewById(R.id.message_send);
      messageReceive = itemView.findViewById(R.id.message_receive);
    }
  }

}
