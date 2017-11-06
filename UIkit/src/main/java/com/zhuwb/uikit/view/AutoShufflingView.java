package com.zhuwb.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhuwb.uikit.R;
import com.zhuwb.uikit.view.adpter.AutoShufflingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZhuWB on 2017/10/16.
 */

public class AutoShufflingView extends LinearLayout implements ViewPager.OnPageChangeListener, AutoShufflingAdapter.CallBack {
    private static final String TAG = "AutoShufflingView";
    /**
     * ViewPager轮播图
     */
    private ViewPager vpShuffling;
    /**
     * 指示器LinearLayout布局
     */
    private LinearLayout layIndicator;
    /**
     * ViewPager适配器
     */
    private AutoShufflingAdapter adapter;
    /**
     * 图片地址的集合
     */
    private List<String> imagesUrl;
    /**
     * 指示器图标
     */
    private int[] imgIndicator;

    /**
     * 上下文对象
     */
    private Context mContext;

    /**
     * 指示器图片的ImageView集合
     */
    private List<ImageView> ivIndicator;
    private int[] icIndicator;
    /**
     * 是否自动轮播
     */
    private boolean isAuto;
    /**
     * 自动轮播间隔
     */
    private long autoTime;
    /**
     * 指示器位置
     */
    private int gravity;
    /**
     * 指示器选中图标
     */
    private int indicatorSelected;
    /**
     * 指示器未选中图标
     */
    private int indicatorUnSelected;

    /**
     * 判断位置的产量
     */
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;

    private OnItemOnClickListener onItemOnClickListener;

    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
        adapter.setCallBack(this);
    }

    public AutoShufflingView(Context context) {
        super(context);
        initView(context);
    }

    public AutoShufflingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //从XML文件中读取参数
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoShufflingView);
        this.isAuto = typedArray.getBoolean(R.styleable.AutoShufflingView_isAuto, false);
        this.autoTime = typedArray.getInteger(R.styleable.AutoShufflingView_autoTime, 5000);
        this.gravity = typedArray.getInteger(R.styleable.AutoShufflingView_gravity, CENTER);
        this.indicatorSelected = typedArray.getResourceId(R.styleable.AutoShufflingView_indicatorSelected, R.drawable.selected);
        this.indicatorUnSelected = typedArray.getResourceId(R.styleable.AutoShufflingView_indicatorUnSelected, R.drawable.unselected);

        initView(context);
    }


    private void initView(Context context) {
        this.mContext = context;
        FrameLayout flay = new FrameLayout(mContext);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flay.setLayoutParams(params1);
        //创建ViewPager
        vpShuffling = new ViewPager(mContext);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vpShuffling.setLayoutParams(params2);
        //创建指示器
        layIndicator = new LinearLayout(mContext);
        FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layIndicator.setLayoutParams(params3);
        //设置水平布局
        layIndicator.setOrientation(HORIZONTAL);

        //设置指示器位置
        switch (gravity) {
            case LEFT:
                params3.gravity = Gravity.BOTTOM | Gravity.LEFT;
                break;
            case CENTER:
                params3.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                break;
            case RIGHT:
                params3.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                break;
            default:
                break;
        }
        // params3.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //添加到布局中
        this.addView(flay);
        flay.addView(vpShuffling);
        flay.addView(layIndicator);
        //初始化参数
        icIndicator = new int[2];
        ivIndicator = new ArrayList<>();
        icIndicator[0] = indicatorSelected;
        icIndicator[1] = indicatorUnSelected;

        vpShuffling.addOnPageChangeListener(this);
    }

    public void setImages(List<String> images) {
        this.imagesUrl = images;
        if (adapter == null) {
            adapter = new AutoShufflingAdapter(mContext, imagesUrl);
        }
        vpShuffling.setAdapter(adapter);
        //初始化指示器图标
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(30, 30);
        params.setMargins(10, 10, 10, 10);
        for (int i = 0; i < imagesUrl.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            ivIndicator.add(imageView);
            layIndicator.addView(imageView);
        }
        //从0开始，设置当前页为第二页
        vpShuffling.setCurrentItem(1);
        autoShuffling(true);
    }

    private void autoShuffling(boolean isAuto) {
        //是否开启自动轮播
        if (isAuto) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            vpShuffling.setCurrentItem(vpShuffling.getCurrentItem() + 1);
                        }
                    });
                }
            }, autoTime, autoTime);

        } else {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ivIndicator.size(); i++) {
            //被选中
            if (i == position - 1) {
                ivIndicator.get(i).setBackgroundResource(icIndicator[0]);
            } else {
                //未选中
                ivIndicator.get(i).setBackgroundResource(icIndicator[1]);
            }
        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (vpShuffling.getCurrentItem() == 0) {
                vpShuffling.setCurrentItem(imagesUrl.size(), false);
            } else if (vpShuffling.getCurrentItem() == imagesUrl.size() + 1) {
                vpShuffling.setCurrentItem(1, false);
            }
        }
    }

    @Override
    public void onClick(int position) {
        if (onItemOnClickListener != null) {
            onItemOnClickListener.onClick(position);
        }
    }

    public interface OnItemOnClickListener {
        void onClick(int position);
    }
}
