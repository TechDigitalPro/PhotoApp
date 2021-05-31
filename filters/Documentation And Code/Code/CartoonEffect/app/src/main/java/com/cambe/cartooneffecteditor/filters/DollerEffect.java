package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class DollerEffect extends MyMainEffect {
    public DollerEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new DollerFilter(ctx, R.drawable.dollertexture, 1);
        return this.filter;
    }
}
