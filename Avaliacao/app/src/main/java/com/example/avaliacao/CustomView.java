package com.example.avaliacao;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    public CustomView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int mXc, mYc;
    private int mRadius;
    private int mCircleColor;
    private int mTextColor;
    private String mText;

    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);
        try {
            mXc=a.getInt(R.styleable.CustomViewActivity_xc,0);
            mYc=a.getInt(R.styleable.CustomView_yc,0);
            mRadius=a.getInt(R.styleable.CustomView_radius,0);
            mCircleColor=a.getInt(R.styleable.CustomView_circle_color,0);
            mTextColor=a.getInt(R.styleable.CustomView_text_color,0);
            mText=a.getString(R.styleable.CustomView_text);
    }
    finally {
        a.recycle();
    }

}
