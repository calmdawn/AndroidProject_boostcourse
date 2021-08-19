package com.calmdawn.boostcoursemobileproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.common.CustomToast;
import com.calmdawn.boostcoursemobileproject.databinding.FragmentMovieDetailInfoLayoutItemGalleryBinding;
import com.calmdawn.boostcoursemobileproject.model.MovieGalleryItem;
import com.calmdawn.boostcoursemobileproject.ui.activity.MoviePhotoActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * 갤러리 사진, 동영상 목록 리사이클러뷰 어댑터
 */
public class MovieDetailInfoGalleryRecyclerAdapter extends RecyclerView.Adapter<MovieDetailInfoGalleryRecyclerAdapter.ViewHolder> {

    List<MovieGalleryItem> items = new ArrayList<>();
    Context context;
    CustomToast customToast;

    public MovieDetailInfoGalleryRecyclerAdapter(Context context) {
        this.context = context;
        this.customToast = new CustomToast(context);
    }

    @NonNull
    @Override
    public MovieDetailInfoGalleryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentMovieDetailInfoLayoutItemGalleryBinding galleryBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_movie_detail_info_layout_item_gallery, parent, false);

        return new ViewHolder(galleryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailInfoGalleryRecyclerAdapter.ViewHolder holder, int position) {
        MovieGalleryItem item = items.get(position);
        holder.setItem(item);
    }

    public void setItem(List<MovieGalleryItem> galleryItems) {
        items.clear();
        items.addAll(galleryItems);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentMovieDetailInfoLayoutItemGalleryBinding galleryBinding;

        public ViewHolder(@NonNull FragmentMovieDetailInfoLayoutItemGalleryBinding galleryBinding) {
            super(galleryBinding.getRoot());
            this.galleryBinding = galleryBinding;

            galleryBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (items.get(getAdapterPosition()).getViewType() == 1) {   //사진 클릭의 경우 사진보기 화면으로 넘어감
                        Intent intent = new Intent(context, MoviePhotoActivity.class);

                        BitmapDrawable bitmapDrawable = (BitmapDrawable) galleryBinding.fragmentMovieDetailInfoLayoutItemGalleryIv.getDrawable();   //byte[] 값으로 넘겨주지 않으면, 이미지 크기가 큰 경우 앱이 강제 종료됨
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] photoBitmapByteArr = stream.toByteArray();

                        customToast.makeText("아이템 클릭됨 : " + items.get(getAdapterPosition()).getUrl(), Toast.LENGTH_SHORT);

                        intent.putExtra("photoBitmapByteArr", photoBitmapByteArr);
                        context.startActivity(intent);
                    } else {        //동영상 클릭의 경우 암시적 인텐트로 동영상 실행함
                        customToast.makeText("아이템 클릭됨 : " + items.get(getAdapterPosition()).getUrl(), Toast.LENGTH_SHORT);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(items.get(getAdapterPosition()).getUrl()));
                        context.startActivity(intent);
                    }

                }
            });
        }

        public void setItem(MovieGalleryItem item) {
            if (item.getViewType() == 1) {  //영화 이미지 url인 경우
                Glide.with(context).load(item.getUrl()).into(galleryBinding.fragmentMovieDetailInfoLayoutItemGalleryIv);
                galleryBinding.fragmentMovieDetailInfoLayoutItemGalleryPlayIv.setVisibility(View.GONE);
            } else {    //영화 동영상 url인 경우
                String baseUrl = "https://img.youtube.com/vi/";
                String id = item.getUrl().substring(item.getUrl().lastIndexOf("/") + 1);
                String img = "/default.jpg";
                Glide.with(context).asBitmap().load(baseUrl + id + img).into(galleryBinding.fragmentMovieDetailInfoLayoutItemGalleryIv);
                galleryBinding.fragmentMovieDetailInfoLayoutItemGalleryPlayIv.setVisibility(View.VISIBLE);
            }
        }
    }
}
