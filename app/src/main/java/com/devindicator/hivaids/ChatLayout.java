package com.devindicator.hivaids;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.devindicator.hivaids.models.ChatMessage;
import com.devindicator.hivaids.models.ChatMessageAdapter;
import com.devindicator.hivaids.models.Commons;

import java.util.ArrayList;

public class ChatLayout extends AppCompatActivity {
    private EditText msgEdit;
    public static ArrayList<ChatMessage> chatMessages;
    public static ChatMessageAdapter chatMessageAdapter;
    private ListView chatLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Smith Junior");

        msgEdit = (EditText) findViewById(R.id.messageEditText);
        chatLv = (ListView) findViewById(R.id.msgListView);
        ImageButton sendBtn = (ImageButton) findViewById(R.id.sendMessageButton);

        chatLv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatLv.setStackFromBottom(true);

        chatMessages = new ArrayList<>();
        chatMessageAdapter = new ChatMessageAdapter(getApplicationContext(), chatMessages);
        chatLv.setAdapter(chatMessageAdapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        String message = msgEdit.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final ChatMessage chatMessage = new ChatMessage(message, "use1", 12, 34, true);
            chatMessage.setId("");
            chatMessage.setDate(Commons.getCurrentDate());
            chatMessage.setTime(Commons.getCurrentTime());
            msgEdit.setText("");
            chatMessageAdapter.add(chatMessage);
            chatMessageAdapter.notifyDataSetChanged();
        }
    }

    public void onBackPressed() {
        finish();
    }
}
