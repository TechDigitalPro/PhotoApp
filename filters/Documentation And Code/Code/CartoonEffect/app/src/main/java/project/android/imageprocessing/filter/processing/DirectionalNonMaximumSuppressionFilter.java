package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiPixelRenderer;

public class DirectionalNonMaximumSuppressionFilter extends MultiPixelRenderer {
    private static final String UNIFORM_LOWER_THRESHOLD = "u_LowerThreshold";
    private static final String UNIFORM_UPPER_THRESHOLD = "u_UpperThreshold";
    private float lowerThreshold;
    private int lowerThresholdHandle;
    private float upperThreshold;
    private int upperThresholdHandle;

    public DirectionalNonMaximumSuppressionFilter(float upperThreshold, float lowerThreshold) {
        this.upperThreshold = upperThreshold;
        this.lowerThreshold = lowerThreshold;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nuniform float u_UpperThreshold;\nuniform float u_LowerThreshold;\nvoid main(){\n   vec3 currentGradientAndDirection = texture2D(u_Texture0,v_TexCoord).rgb;\n   vec2 gradientDirection = ((currentGradientAndDirection.gb * 2.0) - 1.0) * vec2(u_TexelWidth, u_TexelHeight);\n   float firstSampledGradientMagnitude = texture2D(u_Texture0,v_TexCoord + gradientDirection).r;\n   float secondSampledGradientMagnitude = texture2D(u_Texture0,v_TexCoord - gradientDirection).r;\n   float multiplier = step(firstSampledGradientMagnitude, currentGradientAndDirection.r);\n   multiplier = multiplier * step(secondSampledGradientMagnitude, currentGradientAndDirection.r);\n   float thresholdCompliance = smoothstep(u_LowerThreshold, u_UpperThreshold, currentGradientAndDirection.r);\n   multiplier = multiplier * thresholdCompliance;\n   gl_FragColor = vec4(vec3(multiplier), 1.0);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.upperThresholdHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_UPPER_THRESHOLD);
        this.lowerThresholdHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_LOWER_THRESHOLD);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.upperThresholdHandle, this.upperThreshold);
        GLES20.glUniform1f(this.lowerThresholdHandle, this.lowerThreshold);
    }
}
