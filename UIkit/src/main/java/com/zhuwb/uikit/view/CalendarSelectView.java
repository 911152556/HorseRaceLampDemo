package com.zhuwb.uikit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhuwb.uikit.R;

import java.util.List;

/**
 * @author ZhuWenBin
 *         创建时间 :2017/10/19 19:29
 */

public class CalendarSelectView extends View implements View.OnTouchListener {
    private Context mContext;

    /**
     * 列数
     */
    private final int TOTAL_COLUMS = 7;
    /**
     * 行数
     */
    private int TOTAL_ROW = 0;
    /**
     * 默认红色
     */
    private int mSelectColor;
    /**
     * 定义可以选择的天数
     */
    private int num;
    /**
     * 列宽
     */
    private int mColumWidth;
    /**
     * 列高
     */
    private int mColumHeigh;
    /**
     * 字体大小
     */
    private int textSize = 28;
    /**
     * 可编辑字体颜色
     */
    private int enableColor;
    /**
     * 不可编辑字体颜色
     */
    private int unEnableColor;
    /**
     * 色带颜色
     */
    private int mBgSelectColor;
    private int MONTH = 0;

    /**
     * 定义画笔
     */
    private Paint mSelectPain;
    private Paint mContiuosPaint;
    private Paint mMonthPaint;
    private Paint mWeekPaint;
    private Paint mTextPaint;
    /**
     * 视图宽度
     */
    private int mViewWidth;
    /**
     * 视图高度
     */
    private int mViewHeigh;

    /**
     * 其他属性
     */
    private int curYear;
    private int curMonth;
    private int MaxSize = 0;
    private boolean isFirst = true;
    private boolean canClick = true;
    private boolean needClear = true;
    private boolean canEdit = true;

    public CalendarSelectView(Context context) {
        this(context, null);
    }

    public CalendarSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarSelectView);
        mSelectColor = typedArray.getColor(R.styleable.CalendarSelectView_selectColor, Color.parseColor("#1FCD6D"));
        textSize = typedArray.getDimensionPixelOffset(R.styleable.CalendarSelectView_CalendartextSize, sp2px(14));
        enableColor = typedArray.getColor(R.styleable.CalendarSelectView_enableColor, Color.BLACK);
        unEnableColor = typedArray.getColor(R.styleable.CalendarSelectView_unenableColor, Color.LTGRAY);
        mBgSelectColor = typedArray.getColor(R.styleable.CalendarSelectView_bgSelectColor, Color.parseColor("#D6FFE9"));
        typedArray.recycle();
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);

        mMonthPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMonthPaint.setTextSize(textSize);
        mMonthPaint.setColor(Color.GRAY);

        mWeekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWeekPaint.setColor(Color.BLACK);
        mWeekPaint.setStyle(Paint.Style.FILL);
        mWeekPaint.setTextSize(sp2px(14));

        mSelectPain = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPain.setStyle(Paint.Style.FILL);
        mSelectPain.setColor(mSelectColor);

        mContiuosPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContiuosPaint.setStyle(Paint.Style.FILL);
        mContiuosPaint.setColor(mBgSelectColor);

        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        mViewWidth = widthSize;
        mColumWidth = mViewWidth / TOTAL_COLUMS;
        mColumHeigh = mColumWidth - dip2px(5);

    }

    //dip转px
    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //sp转px
    private int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
