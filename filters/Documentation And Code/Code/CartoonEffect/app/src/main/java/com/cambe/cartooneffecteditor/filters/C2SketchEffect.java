package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class C2SketchEffect extends MyMainEffect {
    public C2SketchEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new OptimistPencilFilter(ctx, R.drawable.drawingtool1, 1, MyMainEffect.NORMAL, false);
        return this.filter;
    }
}
