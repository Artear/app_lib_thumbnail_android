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

import java.util.HashMap;

/**
 * Created by sergiobanares.
 */

public class CDNThumbnail {
    public int width = 0;
    public int height = 0;
    public Types type = Types.scaleToFill;
    public HashMap<String, String> params;


    public CDNThumbnail(int width, int height) {
        this(width, height, Types.center);
    }

    public CDNThumbnail(int width, int height, Types type) {
        this(width, height, type, null);
    }

    public CDNThumbnail(int width, int height, Types type, HashMap<String, String> params) {
        this.width = width;
        this.height = height;
        if (type != null) {
            this.type = type;
        }
        this.params = params;

    }

    public enum Types {
        scaleToFill,
        scaleAspectFit,
        scaleAspectFill,
        redraw,
        center,
        top,
        bottom,
        left,
        right,
        topLeft,
        topRight,
        bottomLeft,
        bottomRight
    }
}
