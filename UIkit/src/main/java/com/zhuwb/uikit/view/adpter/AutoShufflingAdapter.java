package com.zhuwb.uikit.view.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuWB on 2017/10/16.
 * 自动轮播图适配器
 */

public class AutoShufflingAdapter extends PagerAdapter implements View.OnClickListener {

    //上下文对象
    private Context mcontext;
    //图片地址
    private List<String> mImagesUrl;
    //
    private List<ImageView> mImageViews;

    private CallBack callBack;

    /**
     * 设置监听器供外部使用
     *
     * @param callBack
     */
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public AutoShufflingAdapter(Context mcontext, List<String> mImagesUrl) {
        this.mcontext = mcontext;
        this.mImagesUrl = mImagesUrl;
        //初始化ImageView
        mImageViews = new ArrayList<>();
        for (int i = 0; i < mImagesUrl.size() + 2; i++) {
            ImageView imageView = new ImageView(mcontext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(this);
            mImageViews.add(imageView);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView v = mImageViews.get(position);
        //如果是第0张则加载图片地址集合里的最后一个图片
        if (position == 0) {
            Glide.with(mcontext).load(mImagesUrl.get(mImagesUrl.size() - 1)).into(v);
        }//如果是最后一个，则加载图片地址集合里面的第一张
        else if (position == mImagesUrl.size() + 1) {
            Glide.with(mcontext).load(mImagesUrl.get(0)).into(v);

        }
        //否则的话正常加载
        else {
            Glide.with(mcontext).load(mImagesUrl.get(position - 1)).into(v);
        }
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViews.get(position));
    }

    @Override
    public int getCount() {
        //前后加一位
        return mImagesUrl.size() + 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private static final String TAG = "AutoShufflingAdapter";

    @Override
    public void onClick(View v) {
        for (int i = 0; i < mImageViews.size(); i++) {
            //单击了某一个
            if (v==mImageViews.get(i)){
                //如果回调不为null则调用onClick
                if (callBack!=null){
                    callBack.onClick(i);
                }
                Log.i(TAG, "onClick: "+i);
            }
        }
    }



    public interface CallBack {
        public void onClick(int position);
    }
}
