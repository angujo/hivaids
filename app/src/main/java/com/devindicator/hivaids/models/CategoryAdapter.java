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
 * Created by bangujo on 22/01/2017.
 */

public class CategoryAdapter extends BaseAdapter {
    private ArrayList<Category> categories;
    private Context context;
    private LayoutInflater inflater;

    public CategoryAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
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
        Category category = categories.get(i);
        if (v == null) v = inflater.inflate(R.layout.category_row, null);
        TextView number = (TextView) v.findViewById(R.id.tvNumber);
        TextView title = (TextView) v.findViewById(R.id.ctvTitle);

        number.setText(category.getPosition()+"");
        title.setText(category.getTitle());
        return v;
    }
}
