package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class Prisma4Effect extends MyMainEffect {
    public Prisma4Effect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new Prisma4Filter(ctx, R.drawable.blackstroes, 8);
        return this.filter;
    }
}
