package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class PolarPixellateFilter extends BasicFilter {
    private static final String UNIFORM_CENTER = "u_Center";
    private static final String UNIFORM_FRACTIONAL_SIZE = "u_FractionalSize";
    private PointF center;
    private int centerHandle;
    private PointF fractionalSize;
    private int fractionalSizeHandle;

    public PolarPixellateFilter(PointF center, PointF fractionalSize) {
        this.center = center;
        this.fractionalSize = fractionalSize;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_FractionalSize;\nuniform vec2 u_Center;\nvoid main(){\n  highp vec2 normCoord = 2.0 * v_TexCoord - 1.0;\n  highp vec2 normCenter = 2.0 * u_Center - 1.0;\n  normCoord -= normCenter;\n  highp float r = length(normCoord);\n  highp float phi = atan(normCoord.y, normCoord.x);\n  r = r - mod(r, u_FractionalSize.x) + 0.03;\n  phi = phi - mod(phi, u_FractionalSize.y);\n  normCoord.x = r * cos(phi);\n  normCoord.y = r * sin(phi);\n  normCoord += normCenter;\n  mediump vec2 textureCoordinateToUse = normCoord / 2.0 + 0.5;\n  gl_FragColor = texture2D(u_Texture0, textureCoordinateToUse);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.fractionalSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_FRACTIONAL_SIZE);
        this.centerHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CENTER);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.fractionalSizeHandle, this.fractionalSize.x, this.fractionalSize.y);
        GLES20.glUniform2f(this.centerHandle, this.center.x, this.center.y);
    }
}
