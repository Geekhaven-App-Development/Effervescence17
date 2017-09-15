package org.effervescence.app17.ui.widget;

/**
 * Created by betterclever on 15/09/17.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import org.effervescence.app17.R;


/**
 * A (rough and ready) extension to {@link BottomNavigationView} which shows a badge over a menu
 * item to indicate new content therein.
 */
public class BadgedBottomNavigationView extends BottomNavigationView {

    private static final int NO_BADGE_POSITION = -1;

    private int badgePosition = NO_BADGE_POSITION;
    private Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius;
    private @Nullable
    OnNavigationItemSelectedListener listener;
    private ViewTreeObserver.OnDrawListener drawListener = new ViewTreeObserver.OnDrawListener() {
        @Override
        public void onDraw() {
            if (badgePosition > NO_BADGE_POSITION) {
                postInvalidateOnAnimation();
            }
        }
    };

    public BadgedBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.BadgedBottomNavigationView);
        badgePaint.setColor(
                a.getColor(R.styleable.BadgedBottomNavigationView_badgeColor, Color.TRANSPARENT));
        radius = a.getDimension(R.styleable.BadgedBottomNavigationView_badgeRadius, 0f);
        a.recycle();

        // we need to listen to tab selection to re-position the badge so set our own listener
        // wrapping any provided listener.
        super.setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (badgePosition > NO_BADGE_POSITION) {
                    // use a pre-draw listener to invalidate ourselves when the badged item moves
                    getViewTreeObserver().addOnDrawListener(drawListener);
                }
                if (listener != null) return listener.onNavigationItemSelected(item);
                return false;
            }
        });
    }

    public void showBadge(@IntRange(from = 0) int itemPosition) {
        badgePosition = itemPosition;
        invalidate();
    }

    public void clearBadge() {
        badgePosition = NO_BADGE_POSITION;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (badgePosition > NO_BADGE_POSITION) {
            ViewGroup menuView = (ViewGroup) getChildAt(0);
            if (menuView == null) return;
            ViewGroup menuItem = (ViewGroup) menuView.getChildAt(badgePosition);
            if (menuItem == null) return;
            View icon = menuItem.getChildAt(0);
            if (icon == null) return;

            float cx = menuView.getLeft() + menuItem.getRight() - icon.getLeft();
            canvas.drawCircle(cx, icon.getTop(), radius, badgePaint);
        }
    }

    @Override
    public void setOnNavigationItemSelectedListener(
            @Nullable OnNavigationItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnDrawListener(drawListener);
        super.onDetachedFromWindow();
    }
}