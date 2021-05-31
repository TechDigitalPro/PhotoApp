package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.chooser.BaseViewTransformer;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.blend.DarkenBlendFilter;
import project.android.imageprocessing.filter.blend.SoftLightBlendFilter;
import project.android.imageprocessing.filter.colour.AdaptiveThresholdFilter;
import project.android.imageprocessing.filter.colour.MonochromeFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;

public class MyCartoonFilter extends GroupFilter {
    float br;
    SaturationFilter color;
    int levels = 5;
    MonochromeFilter mono;
    float saturation = 1.3f;
    SmoothToonFilter toon;

    public MyCartoonFilter(Context ctx, boolean edges) {
        if (edges) {
            this.br = 0.6f;
            this.mono = new MonochromeFilter(new float[]{this.br, this.br, this.br}, 1.0f);
            ColorPencilBlendFilter sketch = new ColorPencilBlendFilter();
            this.toon = new SmoothToonFilter(0.17f, BaseViewTransformer.MAX_DIM_ALPHA, (float) this.levels);
            DarkenBlendFilter darken = new DarkenBlendFilter();
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
            return;
        }
        this.br = 0.35f;
        this.mono = new MonochromeFilter(new float[]{this.br, this.br, this.br}, 1.0f);
        AdaptiveThresholdFilter sketch2 = new AdaptiveThresholdFilter();
        this.toon = new SmoothToonFilter(0.27f, 0.9f, (float) this.levels);
        SoftLightBlendFilter darken2 = new SoftLightBlendFilter();
        this.mono.addTarget(this.toon);
        this.toon.addTarget(darken2);
        sketch2.addTarget(darken2);
        darken2.registerFilterLocation(this.toon, 0);
        darken2.registerFilterLocation(sketch2, 1);
        this.color = new SaturationFilter(this.saturation);
        ColourBlendFilter multiInput = new ColourBlendFilter();
        darken2.addTarget(multiInput);
        this.color.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(darken2, 0);
        multiInput.registerFilterLocation(this.color, 1);
        registerInitialFilter(sketch2);
        registerInitialFilter(this.mono);
        registerInitialFilter(this.color);
        registerFilter(this.toon);
        registerFilter(darken2);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
        } else if (parameter.equals(MyMainEffect.LEVELS)) {
            this.levels = (int) (value + 3.0f);
            this.toon.setLevels((float) this.levels);
        } else if (parameter.equals(MyMainEffect.LEVELS)) {
            this.levels = (int) (value + 3.0f);
            this.toon.setLevels((float) this.levels);
        } else if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.br = value / 10.0f;
            this.mono.setColorls(this.br, this.br, this.br);
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
            return this.br * 10.0f;
        }
        return 0.0f;
    }
}
