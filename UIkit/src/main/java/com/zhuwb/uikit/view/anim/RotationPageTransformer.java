package com.zhuwb.uikit.view.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;



public class RotationPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE=0.85f;
    @Override
    public void transformPage(View page, float position) {
        Log.i("TAG", "transformPage: "+position);
//        page.setAnimation();
        float scaleFactor = Math.max(MIN_SCALE,1 - Math.abs(position));
        float rotate =20* Math.abs(position);
        //position小于等于1的时候，代表page已经位于中心item的最左边，
        //此时设置为最小的缩放率以及最大的旋转度数
        if (position <= -1){
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setRotationY(rotate);
        }//position从0变化到-1，page逐渐向左滑动
        else if (position < 0){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(rotate);
        }//position从0变化到1，page逐渐向右滑动
        else if (position >0 && position < 1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }//position大于等于1的时候，代表page已经位于中心item的最右边
        else if (position >= 1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }

    }

}
