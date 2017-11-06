package com.zhuwb.uikit.view.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuWB on 2017/10/13.
 */

public class BannerAdapter extends PagerAdapter {

    //存储图片地址
    private List<String> mImgUrl;
    //上下文对象
    private Context mContext;
    //ImageView集合，图片的容器
    private List<ImageView> imageViews=new ArrayList<>();

    public BannerAdapter(List<String> mImgUrl, Context mContext) {
        this.mImgUrl = mImgUrl;
        this.mContext = mContext;

        for (int i = 0; i < mImgUrl.size(); i++) {
            ImageView imageView = new ImageView(mContext);

            //Glide加载图片
            Glide.with(mContext)
                    .load(mImgUrl.get(i))
                    .into(imageView);
            imageViews.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return mImgUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }
}
