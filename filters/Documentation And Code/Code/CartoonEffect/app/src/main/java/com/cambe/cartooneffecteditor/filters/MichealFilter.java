package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.effect.EmbossFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;

public class MichealFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public MichealFilter(Context ctx) {
        SmoothToonFilter blur = new SmoothToonFilter(0.35f, 0.5f, 4.0f);
        EmbossFilter toon = new EmbossFilter(2.0f);
        this.adjustments.addTarget(blur);
        blur.addTarget(toon);
        toon.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(blur);
        registerTerminalFilter(toon);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
