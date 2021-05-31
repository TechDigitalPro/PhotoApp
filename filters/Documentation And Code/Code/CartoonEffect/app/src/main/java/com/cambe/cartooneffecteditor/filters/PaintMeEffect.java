package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class PaintMeEffect extends MyMainEffect {
    public PaintMeEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new PaintMeFilter();
        this.filter.setParameter(MyMainEffect.SATURATION, 8.0f);
        return this.filter;
    }
}
