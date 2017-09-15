package com.artear.thumbnailkit.image;


import java.util.HashMap;

public class CDNThumbnail {
    public int width = 0;
    public int height = 0;
    public Types type = Types.scaleToFill;
    public HashMap<String, String> params;


    public CDNThumbnail(int width, int height, Types type) {
        this(width, height, type, null);
    }

    /**
     * @param width
     * @param height
     * @param type
     * @param params
     */
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
