package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.blend.DarkenBlendFilter;
import project.android.imageprocessing.filter.colour.AdaptiveThresholdFilter;
import project.android.imageprocessing.filter.processing.UnsharpMaskFilter;

public class MarkFilter extends GroupFilter {
    UnsharpMaskFilter sharp = new UnsharpMaskFilter(1, this.sharpness);
    float sharpness = 8.0f;

    public MarkFilter(Context ctx) {
        AdaptiveThresholdFilter sketch = new AdaptiveThresholdFilter();
        Texture4BasicFilter blue = new Texture4BasicFilter(ctx, R.drawable.drawingtool1);
        DarkenBlendFilter darken = new DarkenBlendFilter();
        this.sharp.addTarget(blue);
        sketch.addTarget(darken);
        blue.addTarget(darken);
        darken.registerFilterLocation(blue, 0);
        darken.registerFilterLocation(sketch, 1);
        darken.addTarget(this);
        registerInitialFilter(this.sharp);
        registerInitialFilter(sketch);
        registerFilter(blue);
        registerTerminalFilter(darken);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.sharpness = value;
            this.sharp.setIntensity(this.sharpness);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.sharpness;
        }
        return 0.0f;
    }
}
