package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class SmartWaterEffect extends MyMainEffect {
    public SmartWaterEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new SmartWaterFilter(ctx);
        return this.filter;
    }
}
