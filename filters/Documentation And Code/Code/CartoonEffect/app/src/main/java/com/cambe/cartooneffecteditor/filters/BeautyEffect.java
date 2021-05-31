package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class BeautyEffect extends MyMainEffect {
    public BeautyEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new OptimistPencilFilter(ctx, R.drawable.blackstroes, 3, MyMainEffect.NORMAL, false);
        return this.filter;
    }
}
