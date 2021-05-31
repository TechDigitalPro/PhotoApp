package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class SubPencilEffect extends MyMainEffect {
    public SubPencilEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new SimpleCartoonFilter(ctx);
        return this.filter;
    }
}
