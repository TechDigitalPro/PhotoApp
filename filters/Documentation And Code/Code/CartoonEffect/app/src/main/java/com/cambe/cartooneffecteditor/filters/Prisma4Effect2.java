package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class Prisma4Effect2 extends MyMainEffect {
    public Prisma4Effect2(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new Prisma4Filter(ctx, R.drawable.drawingtool1, 4);
        this.filter.setParameter(MyMainEffect.LEVELS, 6.0f);
        return this.filter;
    }
}
