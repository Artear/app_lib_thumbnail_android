package com.artear.thumbnailkit;

import android.util.Log;

import java.util.HashMap;


public class CDNThumbnailKit {
    private static CDNThumbnailKit mInstance = null;
    private HashMap<String, CDNThumbnailInterface> cdns = new HashMap<>();

    private CDNThumbnailKit() {
    }

    /**
     * @return Singleton instance
     */
    public static CDNThumbnailKit getInstance() {
        if (mInstance == null) {
            Class clazz = CDNThumbnailKit.class;
            synchronized (clazz) {
                mInstance = new CDNThumbnailKit();
            }
        }
        return mInstance;
    }

    /**
     * @param cdn add new Thumbnail Generator
     */
    public void register(CDNThumbnailInterface cdn) {
        String cdnType = cdn.getClass().getName();
        if (this.cdns.containsKey(cdnType)) {
            log("CDN currently exists: " + cdnType);
            return;
        }
        this.cdns.put(cdnType, cdn);
    }

    /**
     * @param cdns add new Thumbnail Generators
     */
    public void register(CDNThumbnailInterface[] cdns) {
        for (CDNThumbnailInterface cdn : cdns) {
            register(cdn);
        }
    }

    /**
     * @param imageUrl base image Url
     * @return Thumbnail Generator able to handle the imageUrl
     */
    public CDNThumbnailInterface getCDN(String imageUrl) {
        for (CDNThumbnailInterface cdn : cdns.values()) {
            if (cdn.validate(imageUrl)) {
                return cdn;
            }
        }
        return new CDNThumbnailDefault();
    }

    private void log(String message) {
        Log.d("ThumbNailKit", "" + message);
    }
}
