package com.artear.thumbnailkit;

import android.media.Image;

import com.artear.thumbnailkit.image.CDNThumbnail;

/**
 * Created by sergiobanares on 13/9/17.
 */

public interface CDNThumbnailInterface {
    boolean validate(String imageUrl);
    String thumbnail(String imageUrl, CDNThumbnail thumbnail);

}
