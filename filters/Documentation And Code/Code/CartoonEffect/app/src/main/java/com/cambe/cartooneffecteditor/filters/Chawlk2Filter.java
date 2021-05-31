package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBurnBlendFilter;
import project.android.imageprocessing.filter.effect.ThresholdSketchFilter;

public class Chawlk2Filter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public Chawlk2Filter(Context ctx, int texture, int tiling) {
        ThresholdSketchFilter sketch = new ThresholdSketchFilter(0.2f);
        MultiInputFilter blend = new ColourBurnBlendFilter();
        this.adjustments.addTarget(blend);
        sketch.addTarget(blend);
        blend.registerFilterLocation(this.adjustments, 0);
        blend.registerFilterLocation(sketch, 1);
        Texture4FilterNullB blue = new Texture4FilterNullB(ctx, texture, tiling);
        blend.addTarget(blue);
        blue.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerInitialFilter(sketch);
        registerFilter(blend);
        registerTerminalFilter(blue);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
