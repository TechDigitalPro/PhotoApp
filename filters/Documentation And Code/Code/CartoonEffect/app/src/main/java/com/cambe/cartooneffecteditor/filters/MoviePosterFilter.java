package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.graphics.Point;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.SaturationFilter;
import project.android.imageprocessing.filter.colour.ToneCurveFilter;
import project.android.imageprocessing.filter.processing.WideBluFilter;

public class MoviePosterFilter extends GroupFilter {
    MyBasicFilter adjustments;
    WideBluFilter blur = new WideBluFilter(this.blursize);
    float blursize = 0.003f;
    SaturationFilter color;
    float saturation = 0.8f;

    public MoviePosterFilter(Context ctx) {
        Point[] s = new Point[]{new Point(122, 0), new Point(123, 255), new Point(255, 255)};
        ToneCurveFilter curve = new ToneCurveFilter(s, s, s, s);
        this.adjustments = new MyBasicFilter();
        this.color = new SaturationFilter(this.saturation);
        this.adjustments.addTarget(this.blur);
        this.blur.addTarget(curve);
        curve.addTarget(this.color);
        this.color.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerFilter(this.blur);
        registerFilter(curve);
        registerTerminalFilter(this.color);
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.LEVELS)) {
            this.blursize = value / 1000.0f;
            this.blur.setBlur(this.blursize);
        } else if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.adjustments.setParameter(MyMainEffect.SATURATION, value);
        } else if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.adjustments.setParameter(MyMainEffect.BRIGHTNESS, value);
        } else if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = value / 10.0f;
            this.color.setSaturation(this.saturation);
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.LEVELS)) {
            return this.blursize * 1000.0f;
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return this.adjustments.getParameter(MyMainEffect.SATURATION);
        }
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return this.adjustments.getParameter(MyMainEffect.BRIGHTNESS);
        }
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation * 10.0f;
        }
        return 0.0f;
    }
}
