package com.example.boostcoursemoblieproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Users> reviewItems = new ArrayList<>();
    Context context;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviewItems.size();
    }

    public void addItem(Users item) {
        reviewItems.add(item);
    }

    @Override
    public Object getItem(int position) {
        return reviewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ReviewItemsView reviewItemsView = new ReviewItemsView(context);

        Users item = reviewItems.get(position);
        reviewItemsView.setProfileImgView(item.imgRes);
        reviewItemsView.setScoreRatingBar(item.starScore);
        reviewItemsView.setNameTextView(item.name);
        reviewItemsView.setCommentTextView(item.comment);
        return reviewItemsView;
    }
}
