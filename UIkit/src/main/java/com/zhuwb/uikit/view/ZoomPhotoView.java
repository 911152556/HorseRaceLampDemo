package com.zhuwb.uikit.view;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhuwb.uikit.view.adpter.ZoomPhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuWB on 2017/10/13.
 */

public class ZoomPhotoView extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = "ZoomPhotoView";
    /*
    *图片集合
    */
    private List<String> mImages = new ArrayList<>();
    /*
    *图片位置指示器
    */
    private TextView tvIndicator;
    /*
    *Viewpager
    */
    private MyViewPager vpImages;
    /*
    *上下文对象
    */
    private Context mContext;
    /*
    *适配器
    */
    private ZoomPhotoAdapter adapter;

    private Application mApplication;


    private onPageSelectedAddImage onPageSelectedAddImage;

    public void setOnPageSelectedAddImage(onPageSelectedAddImage onPageSelectedAddImage) {
        this.onPageSelectedAddImage = onPageSelectedAddImage;
    }

    public Application getmApplication() {
        return mApplication;
    }

    public void setmApplication(Application mApplication) {
        this.mApplication = mApplication;
    }

    public ZoomPhotoView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public ZoomPhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mApplication = getmApplication();
        Log.d(TAG, "initView: " + mApplication);

        FrameLayout lay = new FrameLayout(mContext);
        LinearLayout.LayoutParams Linearparams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        lay.setLayoutParams(Linearparams);
        //创建ViewPager
        vpImages = new MyViewPager(mContext);
        FrameLayout.LayoutParams Framparams1 = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vpImages.setLayoutParams(Framparams1);
        //创建指示器
        tvIndicator = new TextView(mContext);
        FrameLayout.LayoutParams Framparams2 = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvIndicator.setLayoutParams(Framparams2);
        Framparams2.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        adapter = new ZoomPhotoAdapter(mImages, mContext);
        vpImages.setAdapter(adapter);
        //加入到布局中
        this.addView(lay);
        lay.addView(vpImages);
        lay.addView(tvIndicator);
    }

    public void setImage(List<String> images) {
        mImages.addAll(images);

        adapter.notifyDataSetChanged();
        //设置vp滑动监听时间
        vpImages.addOnPageChangeListener(this);
        //设置指示器初始样式
        tvIndicator.setTextColor(Color.WHITE);
        tvIndicator.setText("1/" + images.size());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tvIndicator.setText((1 + position) + "/" + mImages.size());


    }

    @Override
    public void onPageSelected(int position) {
        if (onPageSelectedAddImage != null) {
            onPageSelectedAddImage.onPageSelected(position);
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * onPageSelected回调函数
     */
    public interface onPageSelectedAddImage {
        void onPageSelected(int positon);

    }
}
