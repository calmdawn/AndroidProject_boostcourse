package com.example.boostcoursemoblieproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewItemsView extends LinearLayout {

    CircleImageView profileImgView;
    TextView nameTextView;
    TextView dateTextView;
    TextView commentTextView;
    RatingBar scoreRatingBar;


    public ReviewItemsView(Context context) {
        super(context);
        init(context);
    }

    public ReviewItemsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_review_items_view, this, true);

        profileImgView = findViewById(R.id.view_reviewitems_profile_iv);
        dateTextView = findViewById(R.id.view_reviewitems_date);
        scoreRatingBar = findViewById(R.id.view_reviewitems_star_score_ratingbar);
        nameTextView = findViewById(R.id.view_reviewitems_name_tv);
        commentTextView = findViewById(R.id.view_reviewitems_comment_tv);

    }

    public void setProfileImgView(int imgRes) {
        profileImgView.setImageResource(imgRes);
    }

    public void setScoreRatingBar(float score) {
        scoreRatingBar.setRating(score);
    }

    public void setNameTextView(String name) {
        nameTextView.setText(name);
    }

    public void setCommentTextView(String comment) {
        commentTextView.setText(comment);
    }

    public void setDateTextView(String date) {
        dateTextView.setText(date);
    }

}
