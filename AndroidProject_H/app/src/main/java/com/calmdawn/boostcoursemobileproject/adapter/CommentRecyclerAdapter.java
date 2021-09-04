package com.calmdawn.boostcoursemobileproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.calmdawn.boostcoursemobileproject.R;
import com.calmdawn.boostcoursemobileproject.databinding.CommonLayoutItemCommentBinding;
import com.calmdawn.boostcoursemobileproject.model.MovieCommentListItem;

/**
 * 한줄펑 리스트 리사이클러뷰 어댑터
 */
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {

    Context context;
    MovieCommentListItem item = new MovieCommentListItem();

    public CommentRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return item.getResult().size();
    }

    public void setItem(MovieCommentListItem item) {
        this.item = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CommonLayoutItemCommentBinding itemCommentBinding = DataBindingUtil.inflate(
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                R.layout.common_layout_item_comment, parent, false
        );
        return new ViewHolder(itemCommentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieCommentListItem movieCommentListItem = item;
        holder.setItem(movieCommentListItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CommonLayoutItemCommentBinding itemCommentBinding;

        public ViewHolder(@NonNull CommonLayoutItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());
            this.itemCommentBinding = itemCommentBinding;
        }

        public void setItem(MovieCommentListItem item) {
            if (item.getResult().get(getAdapterPosition()).getWriter_image() != null) {
                Glide.with(context).load(item.getResult().get(getAdapterPosition()).getWriter_image()).into(itemCommentBinding.commonLayoutItemCommentIv);
            }
            itemCommentBinding.commonLayoutItemCommentWriterTv.setText(item.getResult().get(getAdapterPosition()).getWriter());
            itemCommentBinding.commonLayoutItemCommentDateTv.setText(item.getResult().get(getAdapterPosition()).getTime());
            itemCommentBinding.commonLayoutItemCommentRatingbar.setRating(item.getResult().get(getAdapterPosition()).getRating());
            itemCommentBinding.commonLayoutItemCommentsTv.setText(item.getResult().get(getAdapterPosition()).getContents());
            itemCommentBinding.commonLayoutItemCommentRecommendTv.setText("추천 " + item.getResult().get(getAdapterPosition()).getRecommend() + "  |");

        }
    }


}
