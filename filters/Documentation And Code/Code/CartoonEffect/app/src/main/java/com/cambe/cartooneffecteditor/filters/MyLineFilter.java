package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class MyLineFilter extends GroupFilter {
    MyBasicFilter basic = new MyBasicFilter();

    public MyLineFilter(Context ctx, int texture) {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 2.0f);
        PTexture4Filter blue = new PTexture4Filter(ctx, texture, 1);
        this.basic.addTarget(sharp);
        sharp.addTarget(blue);
        blue.addTarget(this);
        registerInitialFilter(this.basic);
        registerFilter(sharp);
        registerTerminalFilter(blue);
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
