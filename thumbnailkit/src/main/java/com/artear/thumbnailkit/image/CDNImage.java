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
package com.artear.thumbnailkit.image;

import com.artear.thumbnailkit.CDNThumbnailInterface;
import com.artear.thumbnailkit.CDNThumbnailKit;

/**
 * Created by sergiobanares.
 */

public class CDNImage {
    private String url;
    private CDNThumbnailInterface cdn;

    public CDNImage(String url) {
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
        if (thumbnail == null) {
            return getURL(false);
        }
        return this.cdn.thumbnail(this.url, thumbnail);
    }
}
