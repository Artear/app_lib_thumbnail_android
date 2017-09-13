package com.artear.thumbnailkit.image;


import java.util.HashMap;

/** Created by sergiobanares on 13/9/17. */
public class CDNThumbnail {
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

    public int width= 0;
    public int height = 0;
    public Types type = Types.scaleToFill;
    public HashMap<String,String> params;

    public CDNThumbnail(int width, int height, Types type){
        this(width,height,type,null);
    }

    public CDNThumbnail(int width, int height, Types type, HashMap<String,String> params){
        this.width = width;
        this.height = height;
        if (type != null) {
            this.type = type;
        }
        this.params = params;

    }
}
