package com.artear.thumbnailkit;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by sergiobanares on 13/9/17.
 */
public class CDNThumbnailKit {
    private static CDNThumbnailKit mInstance = null;
    private HashMap<String,CDNThumbnailInterface> cdns = new HashMap<>();

    private CDNThumbnailKit() {
    }

    public static CDNThumbnailKit getInstance() {
        if (mInstance == null) {
            Class clazz = CDNThumbnailKit.class;
            synchronized (clazz) {
                mInstance = new CDNThumbnailKit();
            }
        }
        return mInstance;
    }

    public void register(CDNThumbnailInterface cdn) {
        String cdnType = cdn.getClass().getName();
        if (this.cdns.containsKey(cdnType)) {
            log("CDN currently exists: "+cdnType);
            return;
        }
        this.cdns.put(cdnType,cdn);
    }

    public void register(CDNThumbnailInterface[] cdns) {
        for (CDNThumbnailInterface cdn: cdns) {
            register(cdn);
        }
    }

    public CDNThumbnailInterface getCDN(String imageUrl){
        for (CDNThumbnailInterface cdn: cdns.values()) {
            if (cdn.validate(imageUrl)) {
                return cdn;
            }
        }
        return new CDNThumbnailDefault();
    }

    private void log(String message){
        Log.d("ThumbNailKit", "" + message);
    }
}
