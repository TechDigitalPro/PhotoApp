package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class SwirlFilter extends BasicFilter {
    protected static final String UNIFORM_ANGLE = "u_Angle";
    protected static final String UNIFORM_CENTER = "u_Center";
    protected static final String UNIFORM_RADIUS = "u_Radius";
    private float angle;
    private int angleHandle;
    private PointF center;
    private int centerHandle;
    private float radius;
    private int radiusHandle;

    public SwirlFilter(PointF center, float radius, float angle) {
        this.center = center;
        this.radius = radius;
        this.angle = angle;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nuniform float u_Radius;\nuniform float u_Angle;\nvoid main(){\n   highp vec2 textureCoordinateToUse = v_TexCoord;\n   highp float dist = distance(u_Center, v_TexCoord);\n   if (dist < u_Radius) {\n     textureCoordinateToUse -= u_Center;\n     highp float percent = (u_Radius - dist) / u_Radius;\n     highp float theta = percent * percent * u_Angle * 8.0;\n\t   highp float s = sin(theta);\n     highp float c = cos(theta);\n     textureCoordinateToUse = vec2(dot(textureCoordinateToUse, vec2(c, -s)), dot(textureCoordinateToUse, vec2(s, c)));\n     textureCoordinateToUse += u_Center;\n   }\n   gl_FragColor =  texture2D(u_Texture0, textureCoordinateToUse);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.centerHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CENTER);
        this.radiusHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_RADIUS);
        this.angleHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_ANGLE);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.centerHandle, this.center.x, this.center.y);
        GLES20.glUniform1f(this.radiusHandle, this.radius);
        GLES20.glUniform1f(this.angleHandle, this.angle);
    }
}
