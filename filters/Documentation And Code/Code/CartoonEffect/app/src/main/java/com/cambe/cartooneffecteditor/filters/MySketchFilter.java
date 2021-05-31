package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.MonochromeFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class MySketchFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();
    MonochromeFilter tint = new MonochromeFilter(new float[]{0.5f, 0.5f, 0.5f}, 0.4f);

    public MySketchFilter() {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 1.0f);
        this.adjustments.addTarget(sharp);
        sharp.addTarget(this.tint);
        this.tint.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(sharp);
        registerTerminalFilter(this.tint);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }

    public void setTint(float r, float g, float b) {
        this.tint.setColorls(r / 255.0f, g / 255.0f, b / 255.0f);
    }
}
