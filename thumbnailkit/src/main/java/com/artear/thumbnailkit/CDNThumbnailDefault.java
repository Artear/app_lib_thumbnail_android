package com.artear.thumbnailkit;

import com.artear.thumbnailkit.image.CDNThumbnail;

/**
 * Created by sergiobanares on 13/9/17.
 */

public class CDNThumbnailDefault implements CDNThumbnailInterface {
    @Override
    public boolean validate(String imageUrl) {
        return true;
    }

    @Override
    public String thumbnail(String imageUrl, CDNThumbnail thumbnail) {
        return imageUrl;
    }
}
