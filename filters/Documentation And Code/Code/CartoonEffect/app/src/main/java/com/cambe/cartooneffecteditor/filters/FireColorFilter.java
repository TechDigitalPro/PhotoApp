package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBurnBlendFilter;
import project.android.imageprocessing.filter.blend.LinearBurnBlendFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.SketchFilter;

public class FireColorFilter extends GroupFilter {
    public FireColorFilter(boolean burn) {
        MultiInputFilter multiInput;
        SketchFilter sketch = new SketchFilter();
        SaturationFilter blur = new SaturationFilter(1.4f);
        if (burn) {
            multiInput = new ColourBurnBlendFilter();
        } else {
            multiInput = new LinearBurnBlendFilter();
        }
        sketch.addTarget(multiInput);
        blur.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(sketch, 0);
        multiInput.registerFilterLocation(blur, 1);
        registerInitialFilter(sketch);
        registerInitialFilter(blur);
        registerTerminalFilter(multiInput);
    }
}
