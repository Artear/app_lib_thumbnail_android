/*
 * Copyright 2017 Artear S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artear.thumbnailkit;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by sergiobanares.
 */

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
