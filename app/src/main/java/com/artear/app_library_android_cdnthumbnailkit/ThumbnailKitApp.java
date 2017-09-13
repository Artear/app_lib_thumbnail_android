package com.artear.app_library_android_cdnthumbnailkit;

import android.app.Application;

import com.artear.app_library_android_cdnthumbnailkit.CDNThumbnail.kaltura.CDNKalturaThumbnail;
import com.artear.thumbnailkit.CDNThumbnailKit;

/**
 * Created by sergiobanares on 13/9/17.
 */

public class ThumbnailKitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CDNThumbnailKit.getInstance().register(new CDNKalturaThumbnail());
    }
}
