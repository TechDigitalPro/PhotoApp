package project.android.imageprocessing.filter.colour;

public class RGBFilter extends ColourMatrixFilter {
    public RGBFilter(float red, float green, float blue) {
        super(new float[]{red, 0.0f, 0.0f, 0.0f, 0.0f, green, 0.0f, 0.0f, 0.0f, 0.0f, blue, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}, 1.0f);
    }
}
