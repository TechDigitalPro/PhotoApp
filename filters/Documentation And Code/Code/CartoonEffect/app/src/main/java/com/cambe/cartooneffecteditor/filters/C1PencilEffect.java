package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.widget.LinearLayout;

import project.android.imageprocessing.filter.BasicFilter;

public class C1PencilEffect extends MyMainEffect {
    public C1PencilEffect(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new ColorPencilBlendFilter();
        return this.filter;
    }

    public void addSettingViews(Context ctx, LinearLayout linearl) {
    }
}
