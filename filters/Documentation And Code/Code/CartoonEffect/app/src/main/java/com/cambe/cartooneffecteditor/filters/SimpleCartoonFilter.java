package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.effect.FastKuwaharaFilter;
import project.android.imageprocessing.filter.effect.SmoothToonFilter;
import project.android.imageprocessing.filter.processing.UnsharpMaskFilter;

public class SimpleCartoonFilter extends GroupFilter {
    SaturationFilter color;
    float saturation = 1.0f;

    public SimpleCartoonFilter(Context ctx) {
        UnsharpMaskFilter sharp = new UnsharpMaskFilter(1, 5.0f);
        OptimistPencilFilter four = new OptimistPencilFilter(ctx, R.drawable.drawingtool1, 2, MyMainEffect.NORMAL, true);
        SmoothToonFilter toon = new SmoothToonFilter(0.3f, 1.0f, 45.0f);
        FastKuwaharaFilter oil = new FastKuwaharaFilter(2);
        sharp.addTarget(toon);
        toon.addTarget(oil);
        oil.addTarget(four);
        four.addTarget(this);
        registerInitialFilter(sharp);
        registerFilter(oil);
        registerFilter(toon);
        registerTerminalFilter(four);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
            return;
        }
        parameter.equals(MyMainEffect.LEVELS);
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        parameter.equals(MyMainEffect.LEVELS);
        return 0.0f;
    }
}
