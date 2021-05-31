package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class PrismaEffect extends MyMainEffect {
    public PrismaEffect() {
        this.filter = getNewFilter(null);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MyCartoonFilter(ctx, false);
        return this.filter;
    }
}
