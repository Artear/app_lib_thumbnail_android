package com.artear.app_library_android_cdnthumbnailkit.recycler;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.artear.app_library_android_cdnthumbnailkit.ItemData;
import com.artear.app_library_android_cdnthumbnailkit.R;

import java.io.InputStream;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemData> data;

    public RecyclerAdapter(List<ItemData> images) {
        data = images;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder _holder = (RecyclerViewHolder) holder;
        _holder.imageView.setImageDrawable(null);

        String size = "Size: width: " + data.get(position).getSize().getWidth() +
                " height: " + data.get(position).getSize().getHeight();

        String type = "Type: " + data.get(position).getConnectionType();

        String imageInfo = type + "\n" + size;

        _holder.textView.setText(imageInfo);
        new DownloadImageTask(_holder.imageView).execute(data.get(position).getUrl());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


