package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;

public class MoviePosterEffect extends MyMainEffect {
    public MoviePosterEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new MoviePosterFilter(ctx);
        this.filter.setParameter(MyMainEffect.SATURATION, 6.0f);
        this.filter.setParameter(MyMainEffect.BRIGHTNESS, 5.0f);
        return this.filter;
    }
}
