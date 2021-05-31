package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class MarkEffect extends MyMainEffect {
    public MarkEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MarkFilter(ctx);
        return this.filter;
    }
}
