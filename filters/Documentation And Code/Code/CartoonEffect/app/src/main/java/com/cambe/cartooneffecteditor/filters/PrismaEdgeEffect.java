package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class PrismaEdgeEffect extends MyMainEffect {
    public PrismaEdgeEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MyCartoonFilter(ctx, true);
        return this.filter;
    }
}
