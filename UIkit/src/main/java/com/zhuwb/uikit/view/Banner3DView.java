package com.zhuwb.uikit.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhuwb.uikit.view.adpter.BannerAdapter;
import com.zhuwb.uikit.view.anim.RotationPageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuWB on 2017/10/13.
 */

public class Banner3DView extends FrameLayout {
    private static final String TAG = "Banner3DView";

    private ViewPager viewpager;
    private Context mContext;
    //图片集合
    private List<String> mImagesUrl = new ArrayList<>();
    //适配器
    private BannerAdapter mAdpter;

    public Banner3DView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public Banner3DView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ViewPager vp = new ViewPager(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                500, ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.CENTER;
        vp.setLayoutParams(params);
        //显示超过组件的view
        vp.setClipChildren(false);
        //设置动画
        vp.setPageTransformer(true, new RotationPageTransformer());
        vp.setOffscreenPageLimit(3);
        viewpager = vp;
        //显示超过组件的子view
        this.setClipChildren(false);
        //把ViewPager添加到Framelayout中
        this.addView(viewpager);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return viewpager.dispatchTouchEvent(event);
    }

    public void setImages(List<String> images) {
        Log.d(TAG, "setImages() called with: images = [" + images + "]");
        this.mImagesUrl = images;
        mAdpter = new BannerAdapter(mImagesUrl, mContext);
        viewpager.setAdapter(mAdpter);
    }

}
