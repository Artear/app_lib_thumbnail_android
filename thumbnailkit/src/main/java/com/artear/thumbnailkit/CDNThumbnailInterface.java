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

import com.artear.thumbnailkit.image.CDNThumbnail;

/**
 * Created by sergiobanares.
 */

public interface CDNThumbnailInterface {

    /**
     * @param imageUrl base image url
     * @return true if can be handled
     */
    boolean validate(String imageUrl);

    /**
     * @param imageUrl  base image url
     * @param thumbnail Strategy to reformat url
     * @return image url modified
     */
    String thumbnail(String imageUrl, CDNThumbnail thumbnail);

}
