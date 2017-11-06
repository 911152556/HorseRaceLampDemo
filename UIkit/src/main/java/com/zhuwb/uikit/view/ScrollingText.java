package com.zhuwb.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhuwb.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhuWB on 2017/10/12.
 */

public class ScrollingText extends View {
    private static final String TAG = "ScrollingText";

    //绘制文本X坐标起点
    private int startDrawX = 0;
    //设置文本Y坐标起点
    private int startDrawY = 0;
    //需要绘制文本
    private String drawTextOne;
    //垂直绘制中的第二条文本
    private String drawTextTwo;
    //画笔
    private Paint drawPaint;
    //文本高度和宽度
    private int textWidth, textHeight;
    //是否超过文本框
    private boolean isOutSide;

    //滑动速度
    private int scrollingSpeed;
    //字体颜色
    private int textColor;
    //字体大小
    private int textSize;
    //文本内容
    private String text;
    //滚动方向
    private String orientation;

    private Paint.FontMetrics fm;

    private int leaveNote = sp2px(100);
    private int offX = 1;
    private float centerHeight;
    //滚动文字集合
    private List<String> textList = new ArrayList<>();
    //文字集合计数器
    private int listCount = 0;
    //中间等待时间
    private int waitTime = 20;

    /**
     * java文件初始化
     *
     * @param context
     */
    public ScrollingText(Context context) {
        super(context);

        //从XML文件中读取
        initView();
    }

    /**
     * XML 文件初始化
     *
     * @param context
     * @param attrs
     */
    public ScrollingText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollingText);
        textColor = typedArray.getColor(R.styleable.ScrollingText_textColor, Color.BLACK);
        textSize = (int) typedArray.getDimension(R.styleable.ScrollingText_textSize, sp2px(10));
        scrollingSpeed = typedArray.getInteger(R.styleable.ScrollingText_scrollingSpeed, 15);
        drawTextOne = typedArray.getString(R.styleable.ScrollingText_text);
        orientation = typedArray.getString(R.styleable.ScrollingText_orientation);

        initView();
    }

    private void initView() {
        //drawText = "富强 民主 文明 和谐 公正 友善 爱国 敬业 诚信 友善";
        Paint p = new Paint();
        p.setColor(textColor);
        p.setTextSize(sp2px(textSize));
        drawPaint = p;


        Rect rect = new Rect();
        //通过画一个包裹文本的矩形，从而获取文本的宽高
        textList = getTextList();
        Log.d(TAG, "initView() called" + textList.isEmpty());
//        if (textList.size() >= 1) {
//
//            drawTextOne = textList.get(0);
//        }
        drawPaint.getTextBounds(drawTextOne, 0, drawTextOne.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
        fm = drawPaint.getFontMetrics();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量宽度
        int width = getMySize(textWidth, widthMeasureSpec);
        //测量高度
        int height = getMySize(textHeight, heightMeasureSpec);
        //设置宽高，未设置则无效果
        setMeasuredDimension(width, height);
        //判断文本是否超过布局的宽度
        if (textWidth > getMeasuredWidth()) {
            isOutSide = true;
        } else {
            isOutSide = false;
        }
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: " + isOutSide);
        //屏幕中间位置
        centerHeight = (-fm.ascent - fm.descent) / 2 + getMeasuredHeight() / 2;

        //当设置滚动方式为水平时
        if (orientation.trim().equals("horizontal")) {
            if (isOutSide) {
                //判断文本是否超出区域
                if (startDrawX < -(textWidth)) {
                    startDrawX = leaveNote;
                }
                //文本超出距离
                int outSide = startDrawX;
                //绘制超出区域的文本
                if (outSide < -(textWidth - getMeasuredWidth())) {
                    canvas.drawText(drawTextOne, textWidth + outSide + leaveNote,
                            centerHeight,
                            drawPaint);
                }
                canvas.drawText(drawTextOne, startDrawX,
                        centerHeight,
                        drawPaint);
                //刷新时间
                postInvalidateDelayed(10);
                //X坐标向左移动的效果
                startDrawX -= scrollingSpeed;

            } else {

                if (startDrawX < -textWidth) {
                    startDrawX = getMeasuredWidth() - textWidth;
                }
                int outSide = startDrawX;
                //超出区域，在右边绘制的部分
                if (outSide < 0) {
                    canvas.drawText(drawTextOne, getMeasuredWidth() + outSide, textHeight, drawPaint);
                }
                canvas.drawText(drawTextOne, startDrawX, textHeight, drawPaint);

                //设置刷新时间为10ms
                postInvalidateDelayed(10);
                startDrawX -= scrollingSpeed;
            }

        } else if (orientation.trim().equals("vertical")) {
            //滚动方式为垂直
            //判断文字集合是否为空
            if (textList.size() >= 2) {
                if (listCount + 1 == textList.size()) {
                    listCount = 0;
                }
                drawTextOne = textList.get(listCount);
                drawTextTwo = textList.get(listCount + 1);

                if (startDrawY > getMeasuredHeight() + textHeight) {
                    startDrawY = textHeight;
                }
                int outSide = startDrawY;
                //超出区域，在右边绘制的部分

                if (outSide > getMeasuredHeight()) {
                    canvas.drawText(drawTextOne, startDrawX, outSide - getMeasuredHeight(), drawPaint);
                    canvas.drawText(drawTextOne, startDrawX, outSide - getMeasuredHeight() + textHeight, drawPaint);
                }
                //判断是否到达中间区域
                if (outSide < centerHeight + scrollingSpeed && outSide > centerHeight - scrollingSpeed) {
                    if (offX <= waitTime) {
                        startDrawY -= scrollingSpeed;
                        offX++;
                    } else {
                        //重置循环参数
                        offX = 0;
                        startDrawY += scrollingSpeed;
                    }
                    canvas.drawText(drawTextOne, startDrawX, outSide, drawPaint);
                    canvas.drawText(drawTextOne, startDrawX, outSide + textHeight, drawPaint);

                } else {
                    canvas.drawText(drawTextOne, startDrawX, outSide, drawPaint);
                    canvas.drawText(drawTextOne, startDrawX, outSide + textHeight, drawPaint);
                }
                //设置刷新时间为10ms
                postInvalidateDelayed(10);
                //计数器自加
                listCount++;
                startDrawY += scrollingSpeed;

            } else {
                //一条信息的垂直滚动
                drawTextOne = textList.get(0);

                if (startDrawY > getMeasuredHeight() + textHeight) {
                    startDrawY = textHeight;
                }

                int outSide = startDrawY;
                //超出区域，在右边绘制的部分

                if (outSide > getMeasuredHeight()) {
                    canvas.drawText(drawTextOne, startDrawX, outSide - getMeasuredHeight(), drawPaint);
                }
                //判断是否到达中间区域
                if (outSide < centerHeight + scrollingSpeed && outSide > centerHeight - scrollingSpeed) {
                    if (offX <= waitTime) {
                        startDrawY -= scrollingSpeed;
                        offX++;
                    } else {
                        //重置循环参数
                        offX = 0;
                        startDrawY += scrollingSpeed;
                    }
                    canvas.drawText(drawTextOne, startDrawX, outSide, drawPaint);

                } else {
                    canvas.drawText(drawTextOne, startDrawX, outSide, drawPaint);
                }
                //设置刷新时间为10ms
                postInvalidateDelayed(10);
                //计数器自加
                listCount++;
                startDrawY += scrollingSpeed;
            }
        }

    }

    /**
     * 设置布局
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //测量
    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                Log.i(TAG, "getMySize: 未指定");
                break;

            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //WRAP_CONTENT
                //我们将大小取最小值,也可以取其他值
                mySize = Math.min(size, defaultSize);
                Log.i(TAG, "getMySize: 最大值");
                break;

            case MeasureSpec.EXACTLY:
                //如果是固定的大小，那就不要去改变它,MatchParent或者明确的数值
                mySize = size;
                Log.i(TAG, "getMySize: 固定大小");
                break;
            default:
                break;
        }
        return mySize;
    }

    //sp转px
    private int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int getScrollingSpeed() {
        return scrollingSpeed;
    }

    public void setScrollingSpeed(int scrollingSpeed) {
        this.scrollingSpeed = scrollingSpeed;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        drawPaint.setColor(textColor);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        drawPaint.setTextSize(textSize);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }
}
