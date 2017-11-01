package com.zhuwb.horseracelampdemo;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhuwb.uikit.view.AutoShufflingView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhuWB
 */
public class AutoShufflingActivity extends AppCompatActivity implements AutoShufflingView.OnItemOnClickListener {

    private AutoShufflingView autoShufflingView;

    /**
     * 隐藏状态栏只有在5.0以上版本有用
     */
    private int VERSION = 21;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_shuffling);

        //设置状态栏透明
        if (Build.VERSION.SDK_INT >= VERSION) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        autoShufflingView = (AutoShufflingView) findViewById(R.id.auotoShufflingView);
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            images.add(com.zhuwb.horseracelampdemo.images.imageThumbUrls[i]);
        }
        autoShufflingView.setImages(images);
        autoShufflingView.setOnItemOnClickListener(this);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "当前点击的第" + position + "个", Toast.LENGTH_SHORT).show();
    }
}
