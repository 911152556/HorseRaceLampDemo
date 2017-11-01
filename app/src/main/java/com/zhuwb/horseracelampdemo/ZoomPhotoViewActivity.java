package com.zhuwb.horseracelampdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zhuwb.uikit.view.ZoomPhotoView;

import java.util.ArrayList;
import java.util.List;

import static com.zhuwb.horseracelampdemo.images.imageThumbUrls;



/**
 * @author ZhuWB
 */
public class ZoomPhotoViewActivity extends AppCompatActivity {
    private static final String TAG = "ZoomPhotoViewActivity";
    private ZoomPhotoView zoomView;
    int count = 0;
    /**
     * 自定义每次从服务器加载几张图片
     */
    private int ImgCount;
    /**
     * 记录当前的位置
     */
    private int nowCount = 20;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoomphotoview);
        zoomView = (ZoomPhotoView) findViewById(R.id.zoomView);

        //设置每次加载几张图片
        ImgCount = 20;
        final List<String> images = new ArrayList<>();
        for (int i = 0; i < ImgCount; i++) {
            images.add(imageThumbUrls[i]);
        }
        nowCount = ImgCount;
        zoomView.setImage(images);

        /**
         * 动态添加图片
         */
        zoomView.setOnPageSelectedAddImage(new ZoomPhotoView.onPageSelectedAddImage() {

            @Override
            public void onPageSelected(int positon) {
                if (positon == nowCount - 2) {

                    images.clear();
                    count = (positon + 2) + ImgCount;
                    for (int i = nowCount; i < count; i++) {
                        images.add(imageThumbUrls[i]);
                    }

                    nowCount = count;
                    Log.d(TAG, "onPageSelected() called with: positon = [" + positon + "]" + "count" + count + "nowCount" + nowCount + "imageSize=" + images.size());
                    zoomView.setImage(images);
                }
            }
        });

    }
}
