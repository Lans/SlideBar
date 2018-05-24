package com.demo.slidebar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.demo.slidebar.R;

public class SlideBar extends View {
    private Paint mPaint;
    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z", "#"};
    //选中时的颜色
    private int touchColor = Color.parseColor("#0084FD");
    //未选中的颜色
    private int normalColor = Color.parseColor("#515151");
    private int width;
    private int height;
    //选中的字母的索引
    private int chooseIndex = -1;
    //字母是否被选中
    private boolean LetterTouched;
    //测量出的字母的高度
    private int letterHeight;
    //默认字体的大小
    private int fontSize = 30;
    private OnTouchChangeListener onTouchChangeListener;


    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideBar);
        int nColor = typedArray.getColor(R.styleable.SlideBar_normalColor, normalColor);
        int tColor = typedArray.getColor(R.styleable.SlideBar_touchColor, touchColor);
        int dimension = (int) typedArray.getDimension(R.styleable.SlideBar_fontSize, fontSize);
        this.normalColor = nColor;
        this.touchColor = tColor;
        this.fontSize = dimension;
        typedArray.recycle();
    }

    public SlideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTouchChangeListener(OnTouchChangeListener onTouchChangeListener) {
        this.onTouchChangeListener = onTouchChangeListener;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //绘制字母text的颜色
        mPaint.setColor(normalColor);
        //绘制字母text的字体大小
        mPaint.setTextSize(fontSize);
        //绘制字母text的字体样式
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        //字母所占高度
        letterHeight = height / letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < letters.length; i++) {
            if (chooseIndex == i) {
                mPaint.setColor(touchColor);
                mPaint.setTextSize(fontSize + 10);
            } else {
                mPaint.setColor(normalColor);
                mPaint.setTextSize(fontSize);
            }
            /*
              注意:canvas在绘制text的时候,他绘制的起点不是text的左上角而是它的左下角
              (xPos,yPos)表示每个字母左下角的位置的坐标
             */
            float xPos = (width - mPaint.measureText(letters[i])) / 2;
            float yPos = letterHeight + letterHeight * i;
            canvas.drawText(letters[i], xPos, yPos, mPaint);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:       //手指按下
                LetterTouched = true;
                LetterTouched(event.getY());
                break;
            case MotionEvent.ACTION_MOVE:       //手指移动
                LetterTouched(event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:         //手指的抬起
                LetterTouchedCancel();
                break;
        }
        //重新绘制一遍
        invalidate();
        return true;
    }

    /**
     * 将选中的字母通过接口回调的方式传出去
     *
     * @param y 获取触摸点的高度
     */
    private void LetterTouched(float y) {
        if (LetterTouched && onTouchChangeListener != null) {
            //判断所在的高度在字母的第几个
            int index = (int) (y / height * letters.length);
            //大于0
            index = Math.max(index, 0);
            //小于字母表的长度
            index = Math.min(index, letters.length - 1);
            chooseIndex = index;
            String letter = letters[index];
            onTouchChangeListener.onLetterTouched(letter);
        }
    }

    /**
     * 手指离开的时候改变相应的状态
     */
    private void LetterTouchedCancel() {
        mPaint.setColor(normalColor);
        chooseIndex = -1;
        LetterTouched = false;
        if (onTouchChangeListener != null) {
            onTouchChangeListener.onLetterTouchedCancel();
        }
    }

    //监听字母选中状态
    public interface OnTouchChangeListener {
        void onLetterTouched(String touchLetter);

        void onLetterTouchedCancel();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //因为想把最小宽度设为30dp 也就是wrap_content，所以自己测量
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取测量的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //获取测量的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //期望的宽高
        int measureWidth = 0;
        int measureHeight = 0;

        switch (widthMode) {
            //父View没有对自定义View的大小做任何限制,自定义View想多大就多大,但是不能超过父View的大小
            case MeasureSpec.EXACTLY:
                measureWidth = widthSize;
                break;
            //父View给自定义View确定了一个范围,在这个范围内,自定义view的大小是给出的具体的值,
            // 比如 width =100dp,height=200dp,但是如果给出的任何一个数值超过了父View的限制值,他最大是父View的限制值
            case MeasureSpec.AT_MOST:
                //将px转化为dp
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 30, getResources().getDisplayMetrics());
                measureWidth = i;
                break;
            //父View没有对自定义控件做任何限制,想多大就多大,可以超过父View的大小,
            case MeasureSpec.UNSPECIFIED:
                measureWidth = widthSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                measureHeight = heightSize;
                break;
        }

        //通过resolveSize方法返回符合父类修正后的大小
        measureWidth = resolveSize(measureWidth, widthMeasureSpec);
        measureHeight = resolveSize(measureHeight, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }
}
