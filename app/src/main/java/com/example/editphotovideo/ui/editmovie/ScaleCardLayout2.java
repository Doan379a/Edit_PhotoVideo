package com.example.editphotovideo.ui.editmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.cardview.widget.CardView;

import com.example.editphotovideo.MyApplication;
import com.example.editphotovideo.R;


public class ScaleCardLayout2 extends CardView {
    public int mAspectRatioHeight = 360;
    public int mAspectRatioWidth = 1040;

    public ScaleCardLayout2(Context context) {
        super(context);
    }

    public ScaleCardLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context, attrs);
    }

    public ScaleCardLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context, attrs);
    }

    @SuppressLint({"ResourceType"})
    private void Init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleCardLayout);
        this.mAspectRatioWidth = a.getInt(0, MyApplication.VIDEO_WIDTH);
        this.mAspectRatioHeight = a.getInt(1, MyApplication.VIDEO_HEIGHT);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // Chiều cao giữ nguyên (cho tự do), không tính theo tỉ lệ nữa
        // int height = MeasureSpec.getSize(heightMeasureSpec);

        // Nếu muốn ép chiều cao cố định (VD: 200dp), dùng:
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
        );
    }

}
