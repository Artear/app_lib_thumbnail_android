package com.artear.thumbnailkit.image;

import android.util.Log;

import com.artear.thumbnailkit.CDNThumbnailInterface;
import com.artear.thumbnailkit.CDNThumbnailKit;

/**
 * Created by sergiobanares on 13/9/17.
 */

public class CDNImage {
    protected String url;
    protected CDNThumbnailInterface cdn;

    public CDNImage(String url){
        this.url = url;
        this.cdn = CDNThumbnailKit.getInstance().getCDN(url);
    }

    public String getURL() {
        return getURL(false);
    }

    public String getURL(boolean original) {
        if (original) {
            return this.cdn.thumbnail(this.url, null);
        }
        return this.url;
    }

    public String getURL(CDNThumbnail thumbnail) {
        return this.cdn.thumbnail(this.url, thumbnail);
    }
}
