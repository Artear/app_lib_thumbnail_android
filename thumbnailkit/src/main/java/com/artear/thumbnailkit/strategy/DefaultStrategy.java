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

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by sergiobanares.
 */

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
