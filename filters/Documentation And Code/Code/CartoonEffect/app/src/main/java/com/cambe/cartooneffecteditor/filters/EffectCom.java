package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class EffectCom extends MyMainEffect {
    public EffectCom(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new PrismaFilterComuting(ctx);
        return this.filter;
    }
}
