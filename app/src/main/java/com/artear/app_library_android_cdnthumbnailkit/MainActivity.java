package com.artear.app_library_android_cdnthumbnailkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.artear.app_library_android_cdnthumbnailkit.recycler.RecyclerAdapter;
import com.artear.thumbnailkit.image.CDNImage;
import com.artear.thumbnailkit.image.CDNThumbnail;
import com.artear.thumbnailkit.strategy.DefaultStrategy;
import com.artear.thumbnailkit.strategy.StrategyInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Default Template */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.thumbnail_recycler);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));




        /* // Default Template // */

        StrategyInterface cdnStrategy = new DefaultStrategy();


        cdnStrategy.addAspectRatio(16, 9);
        cdnStrategy.addAspectRatio(4, 3);
        cdnStrategy.addAspectRatio(1, 1);

        //Aspect 1:1
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(96, 96));

        //Aspect 16/9
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(320, 180));

        //Aspect 4:3
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(80, 60));

        String[] images = new String[3];
        images[0] = "http://vodgc.com/p/107/sp/10700/thumbnail/entry_id/0_4ucbsbhf/width/375/height/212/type/2/bgcolor/000000";
        images[1] = "http://vodgc.com/p/107/sp/10700/thumbnail/entry_id/0_cp9nzqzy";
        images[2] = "https://vignette.wikia.nocookie.net/rockosmodernlife/images/5/5e/Rocko_Wallaby.png";

        List<String> imagesList = new ArrayList<>();
        for (String image : images) {
            CDNImage imageOne = new CDNImage(image);

            Log.e("MainActivity", "[   IMAGE   ]> " + imageOne.getURL());
            Log.e("MainActivity", "[  ORIGINAL  ]> " + imageOne.getURL(true));
            Log.e("MainActivity", "[   96X96   ]> " + imageOne.getURL(cdnStrategy.getCDNThumbnail(250, 250)));
            Log.e("MainActivity", "[   80X60   ]> " + imageOne.getURL(cdnStrategy.getCDNThumbnail(4, 3)));
            Log.e("MainActivity", "[   320X180   ]> " + imageOne.getURL(cdnStrategy.getCDNThumbnail(16, 9)));

            Log.e("MainActivity", " ------ ");

            imagesList.add(imageOne.getURL());
            imagesList.add(imageOne.getURL(true));
            imagesList.add(imageOne.getURL(cdnStrategy.getCDNThumbnail(250, 250)));
            imagesList.add(imageOne.getURL(cdnStrategy.getCDNThumbnail(4, 3)));
            imagesList.add(imageOne.getURL(cdnStrategy.getCDNThumbnail(16, 9)));
        }

        recyclerView.setAdapter(new RecyclerAdapter(imagesList));

    }

}
