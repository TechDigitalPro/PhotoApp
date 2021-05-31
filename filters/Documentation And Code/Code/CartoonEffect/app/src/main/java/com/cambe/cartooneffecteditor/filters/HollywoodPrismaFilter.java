package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.blend.SoftLightBlendFilter;
import project.android.imageprocessing.filter.colour.MonochromeFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;

public class HollywoodPrismaFilter extends GroupFilter {
    SaturationFilter color;
    float gray = 0.4f;
    int levels = 6;
    MonochromeFilter mono = new MonochromeFilter(new float[]{0.4f, 0.4f, 0.4f}, 1.0f);
    float saturation = 1.2f;
    SmoothToonFilter toon;

    public HollywoodPrismaFilter() {
        ColorPencilBlendFilter sketch = new ColorPencilBlendFilter();
        this.toon = new SmoothToonFilter(0.27f, 1.0f, (float) this.levels);
        SoftLightBlendFilter darken = new SoftLightBlendFilter();
        this.mono.addTarget(this.toon);
        this.toon.addTarget(darken);
        sketch.addTarget(darken);
        darken.registerFilterLocation(this.toon, 0);
        darken.registerFilterLocation(sketch, 1);
        this.color = new SaturationFilter(this.saturation);
        ColourBlendFilter multiInput = new ColourBlendFilter();
        darken.addTarget(multiInput);
        this.color.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(darken, 0);
        multiInput.registerFilterLocation(this.color, 1);
        registerInitialFilter(sketch);
        registerInitialFilter(this.mono);
        registerInitialFilter(this.color);
        registerFilter(this.toon);
        registerFilter(darken);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
        } else if (parameter.equals(MyMainEffect.LEVELS)) {
            this.levels = (int) (3.0f + value);
            this.toon.setLevels((float) this.levels);
        } else if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.gray = value / 10.0f;
            this.mono.setColorls(this.gray, this.gray, this.gray);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        if (parameter.equals(MyMainEffect.LEVELS)) {
            return (float) (this.levels - 3);
        }
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return this.gray * 10.0f;
        }
        return 0.0f;
    }
}
