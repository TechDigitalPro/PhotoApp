package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.SepiaFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class OldStyleFilter extends GroupFilter {
    MyBasicFilter basic = new MyBasicFilter();
    SepiaFilter sepia;

    public OldStyleFilter(Context ctx, int texture, int tiles) {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 1.0f);
        Texture4FilterNM blue = new Texture4FilterNM(ctx, texture, tiles);
        this.sepia = new SepiaFilter();
        this.basic.addTarget(sharp);
        sharp.addTarget(blue);
        blue.addTarget(this.sepia);
        this.sepia.addTarget(this);
        registerInitialFilter(this.basic);
        registerFilter(sharp);
        registerFilter(blue);
        registerTerminalFilter(this.sepia);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.basic.setParameter(parameter, value);
        } else if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.basic.setParameter(parameter, value);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return this.basic.getParameter(parameter);
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.basic.getParameter(parameter);
        }
        return 0.0f;
    }
}
