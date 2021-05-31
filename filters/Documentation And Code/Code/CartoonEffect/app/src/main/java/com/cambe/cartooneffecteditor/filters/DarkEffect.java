package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class DarkEffect extends MyMainEffect {
    public DarkEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new OptimistPencilFilter(ctx, R.drawable.darklines, 1, MyMainEffect.NOBLACK, false);
        this.filter.setParameter(MyMainEffect.CONTRAST, 7.0f);
        this.filter.setParameter(MyMainEffect.SATURATION, 0.0f);
        return this.filter;
    }
}
