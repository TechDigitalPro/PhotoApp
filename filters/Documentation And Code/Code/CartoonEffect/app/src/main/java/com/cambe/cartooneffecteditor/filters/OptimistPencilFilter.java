package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;
import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.filter.blend.ColourBlendFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class OptimistPencilFilter extends GroupFilter {
    MyBasicFilter adjustments;
    SaturationFilter color;
    float con = 1.0f;
    float saturation = 1.0f;

    public OptimistPencilFilter(Context ctx, int texture, int tiles, int type, boolean bw) {
        BasicFilter four;
        if (type == MyMainEffect.SPARSE) {
            four = new Texture4CoolFilter(ctx, texture, tiles);
        } else if (type == MyMainEffect.NOBLACK) {
            four = new Texture4FilterNullB(ctx, texture, tiles);
        } else {
            four = new PTexture4Filter(ctx, texture, tiles);
        }
        if (bw) {
            this.adjustments = new MyBasicFilter();
            MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 2.0f);
            this.adjustments.addTarget(sharp);
            sharp.addTarget(four);
            four.addTarget(this);
            registerInitialFilter(this.adjustments);
            registerFilter(sharp);
            registerTerminalFilter(four);
            return;
        }
        this.adjustments = new MyBasicFilter();
        MonoUnsharpMaskFilter  sharp = new MonoUnsharpMaskFilter(1, 2.0f);
        MultiInputFilter multiInput = new ColourBlendFilter();
        this.color = new SaturationFilter(this.saturation);
        four.addTarget(multiInput);
        this.color.addTarget(multiInput);
        multiInput.registerFilterLocation(four, 0);
        multiInput.registerFilterLocation(this.color, 1);
        this.adjustments.addTarget(sharp);
        sharp.addTarget(four);
        multiInput.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerInitialFilter(this.color);
        registerFilter(sharp);
        registerFilter(four);
        registerTerminalFilter(multiInput);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
            return;
        }
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        return this.adjustments.getParameter(parameter);
    }
}
