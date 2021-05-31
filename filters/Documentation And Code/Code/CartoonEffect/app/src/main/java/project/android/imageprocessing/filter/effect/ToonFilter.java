package project.android.imageprocessing.filter.effect;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiPixelRenderer;

public class ToonFilter extends MultiPixelRenderer {
    private static final String UNIFORM_QUANTIZATION = "u_Quantization";
    private static final String UNIFORM_THRESHOLD = "u_Threshold";
    private float quantizationLevels;
    private int quantizationLevelsHandle;
    private float threshold;
    private int thresholdHandle;

    public ToonFilter(float threshold, float quantizationLevels) {
        this.threshold = threshold;
        this.quantizationLevels = quantizationLevels;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Threshold;\nuniform float u_Quantization;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec4 color = texture2D(u_Texture0, v_TexCoord);\n   vec2 up = vec2(0.0, u_TexelHeight);\n   vec2 right = vec2(u_TexelWidth, 0.0);\n   float bottomLeftIntensity = texture2D(u_Texture0, v_TexCoord - up - right).r;\n   float topRightIntensity = texture2D(u_Texture0, v_TexCoord + up + right).r;\n   float topLeftIntensity = texture2D(u_Texture0, v_TexCoord + up - right).r;\n   float bottomRightIntensity = texture2D(u_Texture0, v_TexCoord - up + right).r;\n   float leftIntensity = texture2D(u_Texture0, v_TexCoord - right).r;\n   float rightIntensity = texture2D(u_Texture0, v_TexCoord + right).r;\n   float bottomIntensity = texture2D(u_Texture0, v_TexCoord - up).r;\n   float topIntensity = texture2D(u_Texture0, v_TexCoord + up).r;\n   float h = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n   float v = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n   float mag = length(vec2(h, v));\n   vec3 posterizedImageColor = floor((color.rgb * u_Quantization) + 0.5) / u_Quantization;\n   float thresholdTest = 1.0 - step(u_Threshold, mag);\n   gl_FragColor = vec4(posterizedImageColor * thresholdTest, color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.thresholdHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_THRESHOLD);
        this.quantizationLevelsHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_QUANTIZATION);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.thresholdHandle, this.threshold);
        GLES20.glUniform1f(this.quantizationLevelsHandle, this.quantizationLevels);
    }

    public void setLevels(float levels) {
        this.quantizationLevels = levels;
    }

    public void setThreshold(float thres) {
        this.threshold = thres;
    }
}
