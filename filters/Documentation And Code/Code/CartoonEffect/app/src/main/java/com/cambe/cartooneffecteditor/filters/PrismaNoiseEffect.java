package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class PrismaNoiseEffect extends MyMainEffect {
    public PrismaNoiseEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new PrismaNoiseFilter(ctx);
        return this.filter;
    }
}
