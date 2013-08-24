package com.sparc.BoozeChoose.View;


import android.content.Context;
import android.view.MotionEvent;
import android.support.v4.view.MotionEventCompat;
import android.view.ViewConfiguration;

/**
 * User: stephen quick
 * Date: 8/24/13
 * Time: 8:11 AM
 */
public class SwipableListView extends android.widget.ListView
{
    private int mTouchSlop;
    private boolean mIsScrolling;


    public SwipableListView(Context context)
    {
        super(context);

        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
    /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onTouchEvent will be called and we do the actual
         * scrolling there.
         */

        final int action = MotionEventCompat.getActionMasked(event);

        // Always handle the case of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP)
        {
            // Release the scroll.
            mIsScrolling = false;
            return false; // Do not intercept touch event, let the child handle it
        }

        switch (action)
        {
            case MotionEvent.ACTION_MOVE:
            {
                if (mIsScrolling)
                {
                    // We're currently scrolling, so yes, intercept the
                    // touch event!
                    return true;
                }

                // If the user has dragged her finger horizontally more than
                // the touch slop, start the scroll

                // left as an exercise for the reader
                final int xDiff = calculateDistanceX(event);

                // Touch slop should be calculated using ViewConfiguration
                // constants.
                if (xDiff > mTouchSlop)
                {
                    // Start scrolling!
                    mIsScrolling = true;
                    return true;
                }
                break;
            }
        }

        // In general, we don't want to intercept touch events. They should be
        // handled by the child view.
        return false;
    }

    private int calculateDistanceX(MotionEvent event)
    {
        float x1 = 0f;
        float x2 = 0f;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getRawX();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getRawX();
                break;
        }

        return Math.round(x1 - x2);
    }

}