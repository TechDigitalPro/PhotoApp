package project.android.imageprocessing.filter.effect;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.processing.GaussianBlurFilter;

public class SmoothToonFilter extends GroupFilter {
    GaussianBlurFilter blur;
    ToonFilter toon;

    public SmoothToonFilter(float blurSize, float threshold, float quantizationLevels) {
        this.blur = new GaussianBlurFilter(blurSize);
        this.toon = new ToonFilter(threshold, quantizationLevels);
        this.blur.addTarget(this.toon);
        this.toon.addTarget(this);
        registerInitialFilter(this.blur);
        registerTerminalFilter(this.toon);
    }

    public void setBlur(float bl) {
        this.blur.setBlur(bl);
    }

    public void setLevels(float levels) {
        this.toon.setLevels(levels);
    }

    public void setThreshold(float thres) {
        this.toon.setThreshold(thres);
    }
}
