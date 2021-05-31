package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class WefiveEffect2 extends MyMainEffect {
    public WefiveEffect2(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MyLineFilter(ctx, R.drawable.oldstyletexture);
        return this.filter;
    }
}
