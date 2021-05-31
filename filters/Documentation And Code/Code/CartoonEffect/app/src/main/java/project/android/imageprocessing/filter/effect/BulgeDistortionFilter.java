package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class BulgeDistortionFilter extends BasicFilter {
    protected static final String UNIFORM_ASPECT_RATIO = "u_AspectRatio";
    protected static final String UNIFORM_CENTER = "u_Center";
    protected static final String UNIFORM_DISTORTION_AMOUNT = "u_DistortionAmount";
    protected static final String UNIFORM_RADIUS = "u_Radius";
    private float aspectRatio;
    private int aspectRatioHandle;
    private PointF center;
    private int centerHandle;
    private float distortionAmount;
    private int distortionAmountHandle;
    private float radius;
    private int radiusHandle;

    public BulgeDistortionFilter(PointF center, float radius, float distortionAmount, float aspectRatio) {
        this.center = center;
        this.radius = radius;
        this.distortionAmount = distortionAmount;
        this.aspectRatio = aspectRatio;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nuniform float u_Radius;\nuniform float u_DistortionAmount;\nuniform float u_AspectRatio;\nvoid main(){\n   highp vec2 textureCoordinateToUse = vec2(v_TexCoord.x, (v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio));\n   highp float dist = distance(u_Center, textureCoordinateToUse);\n   textureCoordinateToUse = v_TexCoord;\n   if (dist < u_Radius) {\n     textureCoordinateToUse -= u_Center;\n     highp float percent = 1.0 - (u_Radius - dist) / u_Radius * u_DistortionAmount;\n     percent = percent * percent;\n     textureCoordinateToUse = textureCoordinateToUse * percent;\n     textureCoordinateToUse += u_Center;\n   }\n   gl_FragColor = texture2D(u_Texture0, textureCoordinateToUse);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.centerHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CENTER);
        this.radiusHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_RADIUS);
        this.distortionAmountHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DISTORTION_AMOUNT);
        this.aspectRatioHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_ASPECT_RATIO);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.centerHandle, this.center.x, this.center.y);
        GLES20.glUniform1f(this.radiusHandle, this.radius);
        GLES20.glUniform1f(this.distortionAmountHandle, this.distortionAmount);
        GLES20.glUniform1f(this.aspectRatioHandle, this.aspectRatio);
    }
}
