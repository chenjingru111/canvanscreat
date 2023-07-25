package com.dfgadgh.iv_move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CustomImageView extends ImageView implements View.OnTouchListener {
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    public CustomImageView(Context context) {
        super(context);
        init(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // 处理手势拉伸事件
                float scaleFactor = 1.0f + (distanceY / getHeight());
                setScaleX(getScaleX() * scaleFactor);
                setScaleY(getScaleY() * scaleFactor);
                return true;
            }
        });

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                // 处理手势缩放事件
                float scaleFactor = detector.getScaleFactor();
                setScaleX(getScaleX() * scaleFactor);
                setScaleY(getScaleY() * scaleFactor);
                return true;
            }
        });

        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }
}
