package com.ramotion.garlandview.inner;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class InnerRecyclerView extends RecyclerView {

    public InnerRecyclerView(Context context) {
        super(context);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof InnerAdapter)) {
            throw new IllegalArgumentException("Adapter must be instance of InnerAdapter class");
        }
        super.setAdapter(adapter);
    }

    @Override
    public void setLayoutManager(LayoutManager lm) {
        if (!(lm instanceof InnerLayoutManager)) {
            throw new IllegalArgumentException("LayoutManager must be instance of InnerLayoutManager class");
        }
        super.setLayoutManager(lm);
    }

}
