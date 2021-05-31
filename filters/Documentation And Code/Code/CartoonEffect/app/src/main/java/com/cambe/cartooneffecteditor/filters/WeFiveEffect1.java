package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.widget.LinearLayout;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public class WeFiveEffect1 extends MyMainEffect {
    public WeFiveEffect1(Context ctx) {
        this.filter = getNewFilter(ctx);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new C3Filter(ctx, R.drawable.oldstyletexture, 1);
        return this.filter;
    }

    public void addSettingViews(Context ctx, LinearLayout linearl) {
    }
}
