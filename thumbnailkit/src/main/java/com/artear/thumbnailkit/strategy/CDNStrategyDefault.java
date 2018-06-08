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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by sergiobanares.
 */

public class CDNStrategyDefault implements StrategyInterface {

    private HashMap<Float, List<CDNThumbnail>> cdnList;

    private List<Float> aspectRatios;

    public CDNStrategyDefault() {
        aspectRatios = new ArrayList<>();
        this.cdnList = new HashMap<>();
    }


    @Override
    public void addAspectRatio(int width, int height) {
        addAspectRatio((float) height / (float) width);
    }

    private void addAspectRatio(float aspectRatio) {
        if (!aspectRatios.contains(aspectRatio)) {
            aspectRatios.add(aspectRatio);
            cdnList.put(aspectRatio, new ArrayList<CDNThumbnail>());
        }
    }

    private float getAspectRatio(@NotNull CDNThumbnail cdnThumbnail) {

        float returns = 1;
        float aspectRatio = 1;
        if (cdnThumbnail.width != 0) {
            aspectRatio = (float) cdnThumbnail.height / (float) cdnThumbnail.width;
        }

        if (aspectRatios.size() != 0) {

            float bestWidthDiff = Float.MAX_VALUE;

            for (float ar : aspectRatios) {
                float diff = abs(ar - aspectRatio);
                if (diff < bestWidthDiff) {
                    bestWidthDiff = diff;
                    returns = ar;
                }
            }
        }
        return returns;
    }

    @Override
    public void addCDNThumbnail(CDNThumbnail cdnThumbnail) {
        float aspectRatio = getAspectRatio(cdnThumbnail);
        this.cdnList.get(aspectRatio).add(cdnThumbnail);
    }

    @Override
    public void removeCDNThumbnail(CDNThumbnail cdnThumbnail) {
        cdnList.get(getAspectRatio(cdnThumbnail)).remove(cdnThumbnail);
    }

    @Override
    public CDNThumbnail getCDNThumbnail(int width, int height) {
        CDNThumbnail returns = null;
        if (width != 0) {
            float aspectRatio = getAspectRatio(new CDNThumbnail(width, height));
            List<CDNThumbnail> cdnThumbnails = cdnList.get(aspectRatio);
            if (cdnThumbnails.size() != 0) {
                int bestWidthDiff = Integer.MAX_VALUE;
                for (CDNThumbnail cdn : cdnThumbnails) {
                    int diff = abs(cdn.width - width);
                    if (diff < bestWidthDiff) {
                        bestWidthDiff = diff;
                        returns = cdn;
                    }
                }
            }
        }
        return returns;
    }

}
