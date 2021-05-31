package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBurnBlendFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class MyCrossFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public MyCrossFilter(Context ctx, int texture, int tiling) {
        StylizedSketchFilter sketch = new StylizedSketchFilter();
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 2.0f);
        MultiInputFilter multiInput = new ColourBurnBlendFilter();
        Texture4CoolFilter blue = new Texture4CoolFilter(ctx, texture, tiling);
        this.adjustments.addTarget(sharp);
        sharp.addTarget(multiInput);
        sketch.addTarget(multiInput);
        multiInput.addTarget(blue);
        multiInput.registerFilterLocation(sharp, 0);
        multiInput.registerFilterLocation(sketch, 1);
        blue.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerInitialFilter(sketch);
        registerFilter(sharp);
        registerFilter(multiInput);
        registerTerminalFilter(blue);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
