package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class DrawingEffect1 extends MyMainEffect {
    public DrawingEffect1(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new C3Filter(ctx, R.drawable.brushes2, 1);
        return this.filter;
    }
}
