package com.artear.thumbnailkit;

import com.artear.thumbnailkit.image.CDNThumbnail;

public interface CDNThumbnailInterface {

    /**
     * @param imageUrl base image url
     * @return true if can be handled
     */
    boolean validate(String imageUrl);

    /**
     *
     * @param imageUrl base image url
     * @param thumbnail Strategy to reformat url
     * @return image url modified
     */
    String thumbnail(String imageUrl, CDNThumbnail thumbnail);

}
