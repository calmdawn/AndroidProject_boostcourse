package com.example.boostcoursemoblieproject.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.boostcoursemoblieproject.item.ReviewItemsView;
import com.example.boostcoursemoblieproject.item.Users;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Users> reviewItems = new ArrayList<>();
    Context context;

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Users> getReviewItems() {
        return reviewItems;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ReviewItemsView reviewItemsView;
        if (convertView == null) {
            reviewItemsView = new ReviewItemsView(context);
        } else {
            reviewItemsView = (ReviewItemsView) convertView;
        }
        Users item = reviewItems.get(position);
        reviewItemsView.setProfileImgView(item.getImgRes());
        reviewItemsView.setDateTextView(item.getDate());
        reviewItemsView.setScoreRatingBar(item.getStarScore());
        reviewItemsView.setNameTextView(item.getName());
        reviewItemsView.setCommentTextView(item.getComment());
        return reviewItemsView;
    }
}
