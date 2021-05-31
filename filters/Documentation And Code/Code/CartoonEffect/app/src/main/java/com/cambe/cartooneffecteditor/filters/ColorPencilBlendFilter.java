package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.OverlayBlendFilter;
import project.android.imageprocessing.filter.colour.BrightnessFilter;

public class ColorPencilBlendFilter extends GroupFilter {
    float br = 0.2f;
    BrightnessFilter lighter;

    public ColorPencilBlendFilter() {
        FireColorFilter sketch = new FireColorFilter(true);
        this.lighter = new BrightnessFilter(this.br);
        OverlayBlendFilter multiInput = new OverlayBlendFilter();
        sketch.addTarget(multiInput);
        this.lighter.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(this.lighter, 0);
        multiInput.registerFilterLocation(sketch, 1);
        registerInitialFilter(this.lighter);
        registerInitialFilter(sketch);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.br = value / 10.0f;
            this.lighter.setBrightness(this.br);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return this.br * 10.0f;
        }
        return 0.0f;
    }
}
