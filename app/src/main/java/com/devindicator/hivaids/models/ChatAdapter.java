package com.devindicator.hivaids.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devindicator.hivaids.R;

import java.util.ArrayList;

/**
 * Created by bangujo on 20/01/2017.
 */

public class ChatAdapter extends BaseAdapter {
    private Context context;
    ArrayList<ChatGroup> chatGroups;
    private static LayoutInflater layoutInflater = null;

    public ChatAdapter(Context context1, ArrayList<ChatGroup> chatGroups1) {
        chatGroups = chatGroups1;
        context = context1;
        layoutInflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatGroups.size();
    }

    @Override
    public Object getItem(int i) {
        return chatGroups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        if (convertView == null)
            vi = layoutInflater.inflate(R.layout.list_row, null);
        TextView name = (TextView) vi.findViewById(R.id.title);
        TextView conv = (TextView) vi.findViewById(R.id.last_conversation);
        ChatGroup chatGroup = chatGroups.get(i);

        name.setText(chatGroup.getName());
        conv.setText(chatGroup.getLastConversation());
        return vi;
    }
}
