package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.TwoPassMultiPixelFilter;

public class BilateralBlurFilter extends TwoPassMultiPixelFilter {
    private static final String UNIFORM_DISTANCE_NORMALIZATION = "u_DistanceNormalization";
    private float distanceNormalization;
    private int distanceNormalizationHandle;

    public BilateralBlurFilter(float distanceNormalizationFactor) {
        this.distanceNormalization = distanceNormalizationFactor;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_DistanceNormalization;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 singleStepOffset = vec2(u_TexelWidth, u_TexelHeight);\n   int multiplier = 0;\n   vec2 blurStep = vec2(0,0);\n   vec2 blurCoordinates[9];   for(int i = 0; i < 9; i++) {\n     multiplier = (i - 4);\n     blurStep = float(multiplier) * singleStepOffset;\n     blurCoordinates[i] = v_TexCoord.xy + blurStep;\n   }\n   vec4 centralColor;\n   float gaussianWeightTotal;\n   vec4 sum;\n   vec4 sampleColor;\n   float distanceFromCentralColor;\n   float gaussianWeight;\n   centralColor = texture2D(u_Texture0, blurCoordinates[4]);\n   gaussianWeightTotal = 0.18;\n   sum = centralColor * 0.18;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[0]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[1]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[2]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[3]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[5]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[6]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[7]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   sampleColor = texture2D(u_Texture0, blurCoordinates[8]);\n   distanceFromCentralColor = min(distance(centralColor, sampleColor) * u_DistanceNormalization, 1.0);\n   gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\n   gaussianWeightTotal += gaussianWeight;\n   sum += sampleColor * gaussianWeight;\n   gl_FragColor = sum / gaussianWeightTotal;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.distanceNormalizationHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DISTANCE_NORMALIZATION);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.distanceNormalizationHandle, this.distanceNormalization);
    }
}
