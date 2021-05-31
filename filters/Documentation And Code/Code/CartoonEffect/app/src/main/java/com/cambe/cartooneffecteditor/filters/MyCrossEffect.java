package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class MyCrossEffect extends MyMainEffect {
    public MyCrossEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MyCrossFilter(ctx, R.drawable.carbontexture, 3);
        return this.filter;
    }
}
