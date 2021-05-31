package project.android.imageprocessing.filter.colour;

public class BrightnessFilter extends ColourMatrixFilter {
    public BrightnessFilter(float brightness) {
        super(new float[]{1.0f, 0.0f, 0.0f, brightness, 0.0f, 1.0f, 0.0f, brightness, 0.0f, 0.0f, 1.0f, brightness, 0.0f, 0.0f, 0.0f, 1.0f}, 1.0f);
    }

    public void setBrightness(float brightness) {
        super.setMatrix(new float[]{1.0f, 0.0f, 0.0f, brightness, 0.0f, 1.0f, 0.0f, brightness, 0.0f, 0.0f, 1.0f, brightness, 0.0f, 0.0f, 0.0f, 1.0f});
    }
}
