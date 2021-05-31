package project.android.imageprocessing.filter.colour;

public class ExposureFilter extends RGBFilter {
    public ExposureFilter(float exposure) {
        super((float) Math.pow(2.0d, (double) exposure), (float) Math.pow(2.0d, (double) exposure), (float) Math.pow(2.0d, (double) exposure));
    }
}
