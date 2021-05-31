package project.android.imageprocessing.filter.effect;

import project.android.imageprocessing.filter.processing.ThresholdEdgeDetectionFilter;

public class ThresholdSketchFilter extends ThresholdEdgeDetectionFilter {
    public ThresholdSketchFilter(float threshold) {
        super(threshold);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Threshold;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 up = vec2(0.0, u_TexelHeight);\n   vec2 right = vec2(u_TexelWidth, 0.0);\n   float bottomLeftIntensity = texture2D(u_Texture0, v_TexCoord - up - right).r;\n   float topRightIntensity = texture2D(u_Texture0, v_TexCoord + up + right).r;\n   float topLeftIntensity = texture2D(u_Texture0, v_TexCoord + up - right).r;\n   float bottomRightIntensity = texture2D(u_Texture0, v_TexCoord - up + right).r;\n   float leftIntensity = texture2D(u_Texture0, v_TexCoord - right).r;\n   float rightIntensity = texture2D(u_Texture0, v_TexCoord + right).r;\n   float bottomIntensity = texture2D(u_Texture0, v_TexCoord - up).r;\n   float topIntensity = texture2D(u_Texture0, v_TexCoord + up).r;\n   float h = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n   float v = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n   float mag = length(vec2(h, v));\n   mag = 1.0 - step(u_Threshold, mag);\n   gl_FragColor = vec4(vec3(mag), 1.0);\n}\n";
    }
}
