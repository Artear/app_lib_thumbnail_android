package com.artear.thumbnailkit.strategy;

import com.artear.thumbnailkit.image.CDNThumbnail;

public interface StrategyInterface {

    /**
     * @param width  width of container in px
     * @param height width of container in px
     * @return ThumbNail Selected
     */
    CDNThumbnail getCDNThumbnail(int width, int height);

    void add(CDNThumbnail cdnThumbnail);

    void removeCDNThumbnail(CDNThumbnail cdnThumbnail);
}
