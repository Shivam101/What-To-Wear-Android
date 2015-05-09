package com.example.shivam.clothes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Shivam on 09/05/15 at 11:18 PM.
 */
public class SpecialImageView extends ImageView
{
    public SpecialImageView(Context context)
    {
        super(context);
    }

    public SpecialImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SpecialImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}

