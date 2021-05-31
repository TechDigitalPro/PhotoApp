package project.android.imageprocessing.filter.effect;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class PixellateFilter extends BasicFilter {
    protected static final String UNIFORM_ASPECT_RATIO = "u_AspectRatio";
    protected static final String UNIFORM_FRACTIONAL_WIDTH = "u_FractionalWidth";
    private float aspectRatio;
    private int aspectRatioHandle;
    private float fractionalWidth;
    private int fractionalWidthHandle;

    public PixellateFilter(float fractionalWidth, float aspectRatio) {
        this.fractionalWidth = fractionalWidth;
        this.aspectRatio = aspectRatio;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_FractionalWidth;\nuniform float u_AspectRatio;\nvoid main(){\n   highp vec2 sampleDivisor = vec2(u_FractionalWidth, u_FractionalWidth / u_AspectRatio);\n   highp vec2 samplePos = v_TexCoord - mod(v_TexCoord, sampleDivisor) + 0.5 * sampleDivisor;\n   gl_FragColor = texture2D(u_Texture0, samplePos);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.fractionalWidthHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_FRACTIONAL_WIDTH);
        this.aspectRatioHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_ASPECT_RATIO);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.fractionalWidthHandle, this.fractionalWidth);
        GLES20.glUniform1f(this.aspectRatioHandle, this.aspectRatio);
    }
}
