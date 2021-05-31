package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class MichealEffect extends MyMainEffect {
    public MichealEffect() {
        this.filter = getNewFilter(null);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MichealFilter(ctx);
        return this.filter;
    }
}
