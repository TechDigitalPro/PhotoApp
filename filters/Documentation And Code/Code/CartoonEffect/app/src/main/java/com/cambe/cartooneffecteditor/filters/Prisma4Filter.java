package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.blend.SoftLightBlendFilter;
import project.android.imageprocessing.filter.colour.AdaptiveThresholdFilter;
import project.android.imageprocessing.filter.colour.MonochromeFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;
import project.android.imageprocessing.filter.processing.UnsharpMaskFilter;

public class Prisma4Filter extends GroupFilter {
    SaturationFilter color;
    float dark = 0.35f;
    int levels = 5;
    MonochromeFilter mono = new MonochromeFilter(new float[]{this.dark, this.dark, this.dark}, 1.0f);
    float saturation = 1.3f;
    UnsharpMaskFilter sharp = new UnsharpMaskFilter(1, this.sharpness);
    float sharpness = 2.0f;
    SmoothToonFilter toon;

    public Prisma4Filter(Context ctx, int texture, int tiles) {
        AdaptiveThresholdFilter sketch = new AdaptiveThresholdFilter();
        this.toon = new SmoothToonFilter(0.27f, 0.9f, (float) this.levels);
        SoftLightBlendFilter darken = new SoftLightBlendFilter();
        this.sharp.addTarget(this.mono);
        this.mono.addTarget(this.toon);
        this.toon.addTarget(darken);
        sketch.addTarget(darken);
        darken.registerFilterLocation(this.toon, 0);
        darken.registerFilterLocation(sketch, 1);
        PTexture4Filter blue = new PTexture4Filter(ctx, texture, tiles);
        this.color = new SaturationFilter(this.saturation);
        darken.addTarget(blue);
        MultiInputFilter multiInput = new ColourBlendFilter();
        blue.addTarget(multiInput);
        this.color.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(blue, 0);
        multiInput.registerFilterLocation(this.color, 1);
        registerInitialFilter(this.sharp);
        registerInitialFilter(sketch);
        registerFilter(this.mono);
        registerInitialFilter(this.color);
        registerFilter(darken);
        registerFilter(blue);
        registerFilter(this.toon);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
        } else if (parameter.equals(MyMainEffect.LEVELS)) {
            this.levels = (int) (3.0f + value);
        } else if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.dark = 0.2f + (value / 40.0f);
        } else if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.sharpness = value;
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
            return (this.dark - 0.2f) * 40.0f;
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.sharpness;
        }
        return 0.0f;
    }
}
