package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;

public class C3Filter extends GroupFilter {
    MyBasicFilter basic = new MyBasicFilter();
    SaturationFilter color;
    float saturation = 1.2f;

    public C3Filter(Context ctx, int texture, int tiles) {
        StrengthSketchFilter sketch = new StrengthSketchFilter(ctx, texture, tiles);
        this.color = new SaturationFilter(this.saturation);
        MultiInputFilter multiInput = new ColourBlendFilter();
        this.basic.addTarget(sketch);
        sketch.addTarget(multiInput);
        this.color.addTarget(multiInput);
        multiInput.addTarget(this);
        multiInput.registerFilterLocation(sketch, 0);
        multiInput.registerFilterLocation(this.color, 1);
        registerInitialFilter(this.basic);
        registerInitialFilter(this.color);
        registerFilter(sketch);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.basic.setSaturation(this.saturation);
        } else if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.basic.setParameter(parameter, value);
        } else if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.basic.setParameter(parameter, value);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return this.basic.getParameter(parameter);
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.basic.getParameter(parameter);
        }
        return 0.0f;
    }
}
