package com.cambe.cartooneffecteditor.filters;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.effect.KuwaharaFilter;

public class PaintMeFilter extends GroupFilter {
    MyBasicFilter adjustments = new MyBasicFilter();

    public PaintMeFilter() {
        KuwaharaFilter oil2 = new KuwaharaFilter(5);
        this.adjustments.addTarget(oil2);
        oil2.addTarget(this);
        registerInitialFilter(this.adjustments);
        registerTerminalFilter(oil2);
    }

    public void setParameter(String parameter, float value) {
        this.adjustments.setParameter(parameter, value);
    }

    public float getParameter(String parameter) {
        return this.adjustments.getParameter(parameter);
    }
}
