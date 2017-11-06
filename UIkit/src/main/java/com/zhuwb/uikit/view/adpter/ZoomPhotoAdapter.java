package com.zhuwb.uikit.view.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by ZhuWB on 2017/10/13.
 */

public class ZoomPhotoAdapter extends PagerAdapter {
    private static final String TAG = "ZoomPhotoAdapter";
    /**
     * 图片地址
     */
    private List<String> mImages;
    /**
     * 上下文参数
     */
    private Context mContext;

    private List<PhotoView> mPhoto;
    /**
     * 设定用于加载的PhotoView空间个数
     */
    private static int KEY_PHOTOVIEW = 5;

    public ZoomPhotoAdapter(List<String> mImages, Context mContext) {
        this.mImages = mImages;
        this.mContext = mContext;

        mPhoto = new ArrayList<>();
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        params.height = ViewPager.LayoutParams.WRAP_CONTENT;

        //初始化PhotoView
        for (int i = 0; i < KEY_PHOTOVIEW; i++) {
            PhotoView photoView = new PhotoView(mContext);
            photoView.setLayoutParams(params);
            mPhoto.add(photoView);

        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView v = mPhoto.get(position % KEY_PHOTOVIEW);
        Log.i(TAG, "instantiateItem: " + position + "position:" + position);
        container.addView(mPhoto.get(position % KEY_PHOTOVIEW));
        Glide.with(mContext).load(mImages.get(position)).into(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPhoto.get(position % KEY_PHOTOVIEW));
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
