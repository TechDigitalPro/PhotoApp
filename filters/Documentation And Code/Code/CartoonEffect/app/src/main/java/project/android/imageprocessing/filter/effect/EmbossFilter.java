package project.android.imageprocessing.filter.effect;

import project.android.imageprocessing.filter.processing.ConvolutionFilter;

public class EmbossFilter extends ConvolutionFilter {
    public EmbossFilter(float intensity) {
        super(new float[]{-2.0f * intensity, -intensity, 0.0f, -intensity, 1.0f, intensity, 0.0f, intensity, 2.0f * intensity}, 3, 3);
    }
}
