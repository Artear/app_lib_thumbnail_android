package com.artear.app_library_android_cdnthumbnailkit.CDNThumbnail.kaltura;

import android.net.Uri;

import com.artear.thumbnailkit.CDNThumbnailInterface;
import com.artear.thumbnailkit.image.CDNThumbnail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CDNKalturaThumbnail implements CDNThumbnailInterface {


    private final Pattern sPattern
            = Pattern.compile("([a-z]+:\\/\\/)vodgc.com\\/p\\/([0-9]+)\\/sp\\/([0-9]+)\\/thumbnail\\/entry_id\\/([0-9a-zA-Z_]+)");

    @Override
    public boolean validate(String imageUrl) {
        boolean returns = false;
        try {
            Uri imageUri = Uri.parse(imageUrl);
            if (imageUri.getHost().toLowerCase().contains("vodgc.com")) {
                returns = true;
            }
        } catch (Exception ignored) {
        }
        return returns;
    }

    @Override
    public String thumbnail(String imageUrl, CDNThumbnail thumbnail) {
        return replaceThumbnail(imageUrl, getThumbnailPath(thumbnail));
    }

    private String replaceThumbnail(String url, String thumbnail) {

        String returns = url;
        try {
            Matcher m = sPattern.matcher(url);
            if (m.find()) {

                returns = m.group(1) +
                        "vodgc.com/p/" +
                        m.group(2) +
                        "/sp/" +
                        m.group(3) +
                        "/thumbnail/entry_id/" +
                        m.group(4) +

                        ((thumbnail != null && !thumbnail.equals("")) ? "/" + thumbnail : "");
            }

        } catch (Exception ignored) {

        }

        return returns;
    }


    private String getThumbnailPath(CDNThumbnail thumbnail) {
        if (thumbnail != null) {
            return "width/" + thumbnail.width + "/height/" + thumbnail.height + "/type/2/bgcolor/000000";
        }
        return "";
    }
}
