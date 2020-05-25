package com.example.appexperto2020.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appexperto2020.R;
import com.example.appexperto2020.model.Message;

import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter {
    private ArrayList<Message> messages;

    private String userId = "";

    public MessagesAdapter(){
        messages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root;
        if(userId.equals(messages.get(position).getUserid())) {
             root =inflater.inflate(R.layout.messages_row_mine, null);
        }
        else{
            root = inflater.inflate(R.layout.messages_row_others,null);
        }
        TextView messagesRow = root.findViewById(R.id.message_row);
        messagesRow.setText(messages.get(position).getBody());
        return root;
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyDataSetChanged();
    }
}
