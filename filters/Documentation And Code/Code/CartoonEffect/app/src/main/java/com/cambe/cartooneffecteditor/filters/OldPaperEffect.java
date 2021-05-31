package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class OldPaperEffect extends MyMainEffect {
    public OldPaperEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new OldStyleFilter(ctx, R.drawable.kaghaz, 4);
        return this.filter;
    }
}
