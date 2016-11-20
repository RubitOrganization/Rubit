package com.coderschool.android2.rubit.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.coderschool.android2.rubit.R;

/**
 * Created by vinay on 2016/11/19.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Returns the default layout params parameter
     *
     * @return
     */
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Gets the size that the parent container sets for the ViewGroup control
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //The width and height of the ViewGroup control, along with the child view is constantly being added,
        // width and height are also constantly changing
        int lineWidth = 0; // getPaddingLeft()
        int lineHeight = 0;
        // child view The total number of rows
        int rows = 1;

        for (int index = 0; index < getChildCount(); index++) {

            View child = getChildAt(index);
            // Measure the size of the child view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            LayoutParams params = (LayoutParams) child.getLayoutParams();
            // child view The actual width and height (including margin)
            int childActualW = width + params.leftMargin + params.rightMargin;
            int childActualH = height + params.topMargin + params.bottomMargin;

            /*
             * * LineWidth + child view width + ViewGroup padding value
             *
             * * If the maximum width is exceeded, wrap the line
             */
            if (lineWidth + childActualW + getPaddingRight() + getPaddingLeft() > specWidth) {
                lineWidth = childActualW;
                rows++;
            } else {
                lineWidth += childActualW;
            }
            lineHeight = rows * childActualH + getPaddingTop() + getPaddingBottom();

        }

        int measureHeight = modeHeight == MeasureSpec.EXACTLY ? specHeight : lineHeight;
        setMeasuredDimension(specWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Add child view to keep incrementing width && height The starting coordinate point is padding
        int lineWidth = getPaddingLeft();
        int lineHeight = getPaddingTop();
        int rows = 1;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.setBackgroundResource(R.drawable.layout_bg_black);

            int mWidth = childView.getMeasuredWidth();
            int mHeight = childView.getMeasuredHeight();

            FlowLayout.LayoutParams params = (LayoutParams) childView.getLayoutParams();
            int childActualWidth = mWidth + params.leftMargin + params.rightMargin;
            int childActualHeight = mHeight + params.topMargin + params.bottomMargin;


            if (lineWidth + childActualWidth > r - l) {
                lineWidth = childActualWidth + getPaddingLeft();
                rows++;
            } else {
                lineWidth += childActualWidth;
            }
            lineHeight += rows * childActualHeight;

            childView.layout(
                    lineWidth - childActualWidth + params.leftMargin,
                    lineHeight - childActualHeight + params.topMargin,
                    lineWidth - params.rightMargin,
                    lineHeight - params.bottomMargin);

            lineHeight = getPaddingTop();
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
