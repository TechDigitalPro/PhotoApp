package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.OverlayBlendFilter;
import project.android.imageprocessing.filter.colour.BrightnessFilter;

public class LStregthFilter extends GroupFilter {
    public LStregthFilter() {
        FireColorFilter sketch = new FireColorFilter(true);
        BrightnessFilter blur = new BrightnessFilter(-0.1f);
        OverlayBlendFilter multiInput = new OverlayBlendFilter();
        sketch.addTarget(multiInput);
        blur.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(blur, 0);
        multiInput.registerFilterLocation(sketch, 1);
        registerInitialFilter(blur);
        registerInitialFilter(sketch);
        registerTerminalFilter(multiInput);
    }
}
