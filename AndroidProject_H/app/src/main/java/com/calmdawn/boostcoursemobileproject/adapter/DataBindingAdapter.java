package com.calmdawn.boostcoursemobileproject.adapter;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DataBindingAdapter {

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url){   //영화 상세페이지 포스터 불러오기
        Glide.with(view.getContext()).load(url).into(view);
    }

}
