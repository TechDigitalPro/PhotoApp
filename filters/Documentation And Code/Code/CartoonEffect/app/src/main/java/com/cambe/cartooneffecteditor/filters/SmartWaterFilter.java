package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class SmartWaterFilter extends GroupFilter {
    SaturationFilter color;
    float intensity = 4.0f;
    float saturation = 1.0f;
    MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(2, this.intensity);

    public SmartWaterFilter(Context ctx) {
        Texture4FilterNullB four = new Texture4FilterNullB(ctx, R.drawable.aquapaint, 1);
        SmoothToonFilter toon = new SmoothToonFilter(0.01f, 1.0f, 25.0f);
        this.color = new SaturationFilter(this.saturation);
        this.sharp.addTarget(toon);
        toon.addTarget(four);
        ColourBlendFilter darken = new ColourBlendFilter();
        four.addTarget(darken);
        this.color.addTarget(darken);
        darken.registerFilterLocation(four, 0);
        darken.registerFilterLocation(this.color, 1);
        darken.addTarget(this);
        registerInitialFilter(this.sharp);
        registerInitialFilter(this.color);
        registerFilter(toon);
        registerFilter(four);
        registerTerminalFilter(darken);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
        } else if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.intensity = value;
            this.sharp.setIntensity(value);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.intensity;
        }
        return 0.0f;
    }
}
