package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class PaintEffect extends MyMainEffect {
    public PaintEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new PaintFilter(ctx, R.drawable.brshes1, 1);
        return this.filter;
    }
}
