package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.LuminanceThresholdFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class PrismaNoiseFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public PrismaNoiseFilter(Context ctx) {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 2.0f);
        LuminanceThresholdFilter four = new LuminanceThresholdFilter(0.4f);
        this.adjustments.addTarget(sharp);
        sharp.addTarget(four);
        four.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(sharp);
        registerTerminalFilter(four);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
