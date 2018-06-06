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
package com.artear.thumbnailkit.strategy;

import com.artear.thumbnailkit.image.CDNThumbnail;

/**
 * Created by sergiobanares.
 */

public interface StrategyInterface {

    /**
     * @param width  width of container in px
     * @param height width of container in px
     * @return ThumbNail Selected
     */
    CDNThumbnail getCDNThumbnail(int width, int height);

    void addCDNThumbnail(CDNThumbnail cdnThumbnail);

    void removeCDNThumbnail(CDNThumbnail cdnThumbnail);
}
