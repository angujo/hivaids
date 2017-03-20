package com.devindicator.hivaids.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devindicator.hivaids.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by bangujo on 20/01/2017.
 */

public class ArticlesAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Article> articleArrayList;
    private static LayoutInflater layoutInflater = null;

    public ArticlesAdapter(Context context1, ArrayList<Article> articles) {
        articleArrayList = articles;
        context = context1;
        layoutInflater = (LayoutInflater) context1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return articleArrayList.get(i);
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
        TextView dur = (TextView) vi.findViewById(R.id.duration);
        ImageView imageView = (ImageView) vi.findViewById(R.id.list_image);
        Article article = articleArrayList.get(i);

        if (0 >= article.getThumbnailURL().length() || null == article.getThumbnailURL())
            imageView.setImageResource(R.drawable.img_holder);
        else
            Picasso.with(context).load(article.getThumbnailURL()).placeholder(R.drawable.img_holder)
                    .error(R.drawable.img_holder).into(imageView);

        dur.setText(article.getDated());
        name.setText(article.getTitle());
        conv.setText(article.getSummary());
        return vi;
    }
}
