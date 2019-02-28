package com.artear.app_library_android_cdnthumbnailkit.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artear.app_library_android_cdnthumbnailkit.R;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_image);
        textView = itemView.findViewById(R.id.item_text);
    }
}
