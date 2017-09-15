package com.artear.thumbnailkit.strategy;


import com.artear.thumbnailkit.image.CDNThumbnail;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class DefaultStrategy implements StrategyInterface {

    private List<CDNThumbnail> cdnList;

    public DefaultStrategy() {
        this.cdnList = new ArrayList<>();
    }

    @Override
    public void add(CDNThumbnail cdnThumbnail) {
        this.cdnList.add(cdnThumbnail);
    }

    @Override
    public void removeCDNThumbnail(CDNThumbnail cdnThumbnail) {
        this.cdnList.remove(cdnThumbnail);
    }

    @Override
    public CDNThumbnail getCDNThumbnail(int width, int height) {
        CDNThumbnail returns = null;
        if (cdnList.size() != 0) {

            int bestWidthDiff = Integer.MAX_VALUE;

            for (CDNThumbnail cdn : cdnList) {
                int diff = abs(cdn.width - width);
                if (diff < bestWidthDiff) {
                    bestWidthDiff = diff;
                    returns = cdn;
                }
            }
        }
        return returns;

    }
}
