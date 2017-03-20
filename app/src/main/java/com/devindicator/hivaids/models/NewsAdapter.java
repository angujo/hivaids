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
 * Created by bangujo on 22/01/2017.
 */

public class NewsAdapter extends BaseAdapter {
    private ArrayList<NewsItem> newsItemArrayList;
    private Context context;
    private LayoutInflater inflater;

    public NewsAdapter(Context context, ArrayList<NewsItem> newsItems) {
        this.context = context;
        newsItemArrayList = newsItems;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        NewsItem newsItem = newsItemArrayList.get(i);
        if (v == null) {
            v = inflater.inflate(R.layout.news_row, null);
        }
        ImageView thumb = (ImageView) v.findViewById(R.id.nrImage);
        TextView title = (TextView) v.findViewById(R.id.nrTitle);
        TextView dated = (TextView) v.findViewById(R.id.nrDated);
        TextView source = (TextView) v.findViewById(R.id.nrSource);

        if (0 >= newsItem.getThumbUrl().length() || newsItem.getThumbUrl().isEmpty())
            thumb.setImageResource(R.drawable.img_holder);
        else
            Picasso.with(context).load(newsItem.getThumbUrl()).placeholder(R.drawable.img_holder).error(R.drawable.img_holder).into(thumb);
        title.setText(newsItem.getTitle());
        dated.setText(newsItem.getDateTime());
        source.setText(newsItem.getSource());
        return v;
    }
}
