package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.FalseColourFilter;
import project.android.imageprocessing.filter.effect.ThresholdSketchFilter;

public class PenSketchFilter extends GroupFilter {
    public PenSketchFilter() {
        ThresholdSketchFilter sketch = new ThresholdSketchFilter(0.32f);
        FalseColourFilter blue = new FalseColourFilter(new float[]{0.0f, 0.0f, 0.9f}, new float[]{0.9f, 0.9f, 0.9f});
        sketch.addTarget(blue);
        blue.addTarget(this);
        registerInitialFilter(sketch);
        registerTerminalFilter(blue);
    }
}
