package com.cambe.cartooneffecteditor.filters;

import android.content.Context;

import project.android.imageprocessing.filter.BasicFilter;
import project.android.imageprocessing.filter.processing.SobelEdgeDetectionFilter;

public class EdgeStyleEffect extends MyMainEffect {
    public EdgeStyleEffect() {
        this.filter = getNewFilter(null);
    }

    public BasicFilter getNewFilter(Context ctx) {
        this.filter = new SobelEdgeDetectionFilter();
        return this.filter;
    }
}
