package com.artear.app_library_android_cdnthumbnailkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.artear.thumbnailkit.image.CDNImage;
import com.artear.thumbnailkit.image.CDNThumbnail;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* Default Template */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /* // Default Template // */



        String[] images = new String[3];
        images[0] = "http://vodgc.com/p/107/sp/10700/thumbnail/entry_id/0_4ucbsbhf/width/375/height/212/type/2/bgcolor/000000";
        images[1] = "http://vodgc.com/p/107/sp/10700/thumbnail/entry_id/0_cp9nzqzy";
        images[2] = "https://vignette.wikia.nocookie.net/rockosmodernlife/images/5/5e/Rocko_Wallaby.png";

        int count = 0;
        for (String image:images ){
            count++;
            Log.e("MainActivity","Image: "+ count);
            CDNImage imageOne = new CDNImage(image);

            Log.e("MainActivity","[   IMAGE   ]> "+ imageOne.getURL());
            Log.e("MainActivity","[  ORIGINAL  ]> "+ imageOne.getURL(true));
            Log.e("MainActivity","[   96X96   ]> "+ imageOne.getURL(new CDNThumbnail(96, 96, CDNThumbnail.Types.center) ));

            Log.e("MainActivity"," ------ ");
        }


    }

}
