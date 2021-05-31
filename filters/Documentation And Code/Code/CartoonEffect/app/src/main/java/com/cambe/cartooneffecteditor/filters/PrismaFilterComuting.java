package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import com.cambe.cartooneffecteditor.R;

import java.io.IOException;

import project.android.imageprocessing.filter.GenericFilter;
import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.processing.MonoUnsharpMaskFilter;

public class PrismaFilterComuting extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public PrismaFilterComuting(Context ctx) {
        MonoUnsharpMaskFilter sharp = new MonoUnsharpMaskFilter(1, 1.0f);
        GenericFilter four = new GenericFilter();
        try {
            four.setFragmentShader(ReadRawResources.readTextFileFromRawResource(ctx, R.raw.ctxprisma));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.adjustments.addTarget(sharp);
        sharp.addTarget(four);
        four.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(sharp);
        registerTerminalFilter(four);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
