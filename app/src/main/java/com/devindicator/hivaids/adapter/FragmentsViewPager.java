package com.devindicator.hivaids.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by bangujo on 04/03/2017.
 */

public class FragmentsViewPager extends ViewPager {
    private boolean swipeable = false;
    private Context context;

    public FragmentsViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public FragmentsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return (this.swipeable) && super.onInterceptTouchEvent(arg0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return swipeable && super.onTouchEvent(event);
    }
}
