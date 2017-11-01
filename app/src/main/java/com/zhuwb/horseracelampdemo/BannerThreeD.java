package com.zhuwb.horseracelampdemo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhuwb.uikit.view.Banner3DView;

import java.util.ArrayList;
import java.util.Arrays;

public class BannerThreeD extends AppCompatActivity {

    private Banner3DView banner3DView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_three_d);
        banner3DView = (Banner3DView) findViewById(R.id.banner);
        banner3DView.setImages(Arrays.asList(images.imageThumbUrls));
    }
}
