package com.artear.app_library_android_cdnthumbnailkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.artear.app_library_android_cdnthumbnailkit.recycler.RecyclerAdapter;
import com.artear.thumbnailkit.image.CDNImage;
import com.artear.thumbnailkit.image.CDNThumbnail;
import com.artear.thumbnailkit.strategy.CDNStrategyConnection;
import com.artear.thumbnailkit.strategy.StrategyInterface;
import com.artear.tools.media.Size;
import com.artear.tools.network.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Default Template */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.thumbnail_recycler);
        TextView reload = findViewById(R.id.reload);

        reload.setClickable(true);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoad();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reLoad();

    }

    void reLoad() {

        /* // Default Template // */
        StrategyInterface cdnStrategy = new CDNStrategyConnection(this);


        cdnStrategy.addAspectRatio(16, 9);
        cdnStrategy.addAspectRatio(4, 3);
        cdnStrategy.addAspectRatio(1, 1);

        //Aspect 1:1
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(96, 96));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(460, 460));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(960, 960));

        //Aspect 16/9
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(160, 90));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(320, 180));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(640, 360));


        //Aspect 4:3
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(160, 120));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(200, 150));
        cdnStrategy.addCDNThumbnail(new CDNThumbnail(400, 300));

        List<ItemData> imagesList = new ArrayList<>();

        String connection = "";
        switch (ConnectionUtil.INSTANCE.connectionType(this)) {
            case UNKNOWN:
                connection = "UNKNOWN";
                break;
            case WIFI:
                connection = "WIFI";
                break;
            case _2G:
                connection = "2G";
                break;
            case _3G:
                connection = "3G";
                break;
            case _4G:
                connection = "4G";
                break;
        }

        CDNThumbnail square = cdnStrategy.getCDNThumbnail(800, 800);
        CDNThumbnail almostSQ = cdnStrategy.getCDNThumbnail(800, 600);
        CDNThumbnail wide = cdnStrategy.getCDNThumbnail(640, 360);
        CDNImage imageOne = new CDNImage("http://vodgc.com/p/107/sp/10700/thumbnail/entry_id/0_4ucbsbhf/width/375/height/212/type/2/bgcolor/000000");

        imagesList.add(new ItemData(imageOne.getURL(), "Original", new Size(375, 212)));
        imagesList.add(new ItemData(imageOne.getURL(wide), "Wide - " + connection, new Size(wide.width, wide.height)));
        imagesList.add(new ItemData(imageOne.getURL(square), "Square - " + connection, new Size(square.width, square.height)));
        imagesList.add(new ItemData(imageOne.getURL(almostSQ), "almostSQ - " + connection, new Size(almostSQ.width, almostSQ.height)));

        adapter = new RecyclerAdapter(imagesList);
        recyclerView.setAdapter(adapter);
    }

}
