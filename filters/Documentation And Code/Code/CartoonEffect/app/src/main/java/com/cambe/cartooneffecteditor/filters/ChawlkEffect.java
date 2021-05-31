package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class ChawlkEffect extends MyMainEffect {
    public ChawlkEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new Chawlk2Filter(ctx, R.drawable.blakchawkboard, 2);
        this.filter.setParameter(MyMainEffect.BRIGHTNESS, 7.0f);
        return this.filter;
    }
}
