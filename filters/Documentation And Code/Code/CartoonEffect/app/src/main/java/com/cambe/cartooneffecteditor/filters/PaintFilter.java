package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.effect.FastKuwaharaFilter;
import project.android.imageprocessing.filter.processing.UnsharpMaskFilter;

public class PaintFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public PaintFilter(Context ctx, int texture, int tiling) {
        UnsharpMaskFilter sharp = new UnsharpMaskFilter(1, 6.0f);
        TextureMixFilter four = new TextureMixFilter(ctx, texture, tiling);
        FastKuwaharaFilter color = new FastKuwaharaFilter(2);
        this.adjustments.addTarget(sharp);
        sharp.addTarget(color);
        color.addTarget(four);
        four.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(color);
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
