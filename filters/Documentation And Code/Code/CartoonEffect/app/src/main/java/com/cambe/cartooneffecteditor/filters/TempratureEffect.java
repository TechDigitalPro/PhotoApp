package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;
import project.android.imageprocessing.filter.colour.LookupFilter;

public class TempratureEffect extends MyMainEffect {
    public TempratureEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new LookupFilter(ctx, R.drawable.lookup_thermal);
        return this.filter;
    }
}
