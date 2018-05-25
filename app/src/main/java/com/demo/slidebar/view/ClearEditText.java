package com.demo.slidebar.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.demo.slidebar.R;

/**
 * 带删除的EditText
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mDrawable;
    private boolean focus;
    private float eventX;

    public ClearEditText(Context context) {
        super(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        // getCompoundDrawables() Returns drawables for the left(0), top(1), right(2) and bottom(3)
        mDrawable = getCompoundDrawables()[2]; // 获取drawableRight
        if (mDrawable == null) {
            // 如果为空，即没有设置drawableRight，则使用R.mipmap.close这张图片
            mDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear);
        }
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
        // 默认隐藏图标
        setDrawableVisible(false);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            eventX = event.getX();
            performClick();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mDrawable != null) {
            int start = getWidth() - getTotalPaddingRight() + getPaddingRight(); // 起始位置
            int end = getWidth(); // 结束位置
            boolean available = (eventX > start) && (eventX < end);
            if (available) {
                this.setText("");
            }
        }
        return super.performClick();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (focus) {
            setDrawableVisible(s.length() > 0);
        } else {
            setDrawableVisible(false);
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        focus = hasFocus;
        if (hasFocus && getText().length() > 0) {
            setDrawableVisible(true); // 有焦点且有文字时显示图标
        } else {
            setDrawableVisible(false);
        }
    }

    protected void setDrawableVisible(boolean visible) {
        Drawable right = visible ? mDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }
}
