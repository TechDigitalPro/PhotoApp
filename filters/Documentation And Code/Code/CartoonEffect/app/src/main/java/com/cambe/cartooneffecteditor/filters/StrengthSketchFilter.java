package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class StrengthSketchFilter extends GroupFilter {
    public StrengthSketchFilter(Context ctx, int texture, int tiling) {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 2.0f);
        PTexture4Filter blue = new PTexture4Filter(ctx, texture, tiling);
        sharp.addTarget(blue);
        blue.addTarget(this);
        registerInitialFilter(sharp);
        registerTerminalFilter(blue);
    }
}
