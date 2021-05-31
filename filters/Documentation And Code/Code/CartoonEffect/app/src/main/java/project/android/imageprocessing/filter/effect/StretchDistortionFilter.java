package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class StretchDistortionFilter extends BasicFilter {
    protected static final String UNIFORM_CENTER = "u_Center";
    private PointF center;
    private int centerHandle;

    public StretchDistortionFilter(PointF center) {
        this.center = center;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nvoid main(){\n\thighp vec2 normCoord = 2.0 * v_TexCoord - 1.0;\n\thighp vec2 normCenter = 2.0 * u_Center - 1.0;\n\tnormCoord -= normCenter;\n\tmediump vec2 s = sign(normCoord);\n\tnormCoord = abs(normCoord);\n\tnormCoord = 0.5 * normCoord + 0.5 * smoothstep(0.25, 0.5, normCoord) * normCoord;\n\tnormCoord = s * normCoord;\n\tnormCoord += normCenter;\n\tmediump vec2 textureCoordinateToUse = normCoord / 2.0 + 0.5;\n\tgl_FragColor = texture2D(u_Texture0, textureCoordinateToUse );\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.centerHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CENTER);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.centerHandle, this.center.x, this.center.y);
    }
}
