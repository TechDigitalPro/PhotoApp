package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class SketchColorEffect extends MyMainEffect {
    public SketchColorEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new OptimistPencilFilter(ctx, R.drawable.drawingtool2, 1, MyMainEffect.NOBLACK, false);
        this.filter.setParameter(MyMainEffect.SATURATION, 0.0f);
        this.filter.setParameter(MyMainEffect.CONTRAST, 7.0f);
        this.filter.setParameter(MyMainEffect.BRIGHTNESS, 8.0f);
        return this.filter;
    }
}
