package com.cambe.cartooneffecteditor.filters;

import java.util.Random;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.effect.BulgeDistortionFilter;
import project.android.imageprocessing.filter.effect.PinchDistortionFilter;
import project.android.imageprocessing.filter.effect.SketchFilter;

public class StoneMaskFilter extends GroupFilter {
    public StoneMaskFilter(FaceFeatureMarks[] faces) {
        SketchFilter toon = new SketchFilter();
        BulgeDistortionFilter[] filters = new BulgeDistortionFilter[(faces.length * 2)];
        Random rand = new Random();
        for (int i = 0; i < faces.length; i++) {
            if (rand.nextInt(2) == 0) {
                filters[i * 2] = new BulgeDistortionFilter(faces[i].getCenter(), faces[i].getStrength(), faces[i].getRadius(), 1.0f);
                filters[(i * 2) + 1] = new BulgeDistortionFilter(faces[i].getCenter(), faces[i].getStrength(), faces[i].getRadius(), 1.0f);
            } else {
                filters[i * 2] = new PinchDistortionFilter(faces[i].getCenter(), faces[i].getStrength(), faces[i].getRadius(), 1.0f);
                filters[(i * 2) + 1] = new PinchDistortionFilter(faces[i].getCenter(), faces[i].getStrength(), faces[i].getRadius(), 1.0f);
            }
            if (i == 0) {
                toon.addTarget(filters[0]);
                filters[0].addTarget(filters[1]);
            } else {
                filters[(i * 2) - 1].addTarget(filters[i * 2]);
                filters[i * 2].addTarget(filters[(i * 2) + 1]);
            }
        }
        filters[filters.length - 1].addTarget(this);
        registerInitialFilter(toon);
        registerTerminalFilter(filters[filters.length - 1]);
    }
}
