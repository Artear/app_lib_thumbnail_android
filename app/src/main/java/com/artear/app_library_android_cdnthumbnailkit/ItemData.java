package com.artear.app_library_android_cdnthumbnailkit;


import com.artear.tools.media.Size;

public class ItemData {

    private String url = "";
    private String connectionType = "";
    private Size size;

    public ItemData(String url, String connectionType, Size size) {

        this.url = url;
        this.connectionType = connectionType;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
