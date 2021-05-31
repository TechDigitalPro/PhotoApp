package project.android.imageprocessing.filter.processing;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class ZoomBlurFilter extends BasicFilter {
    private static final String UNIFORM_BLUR_LOCATION = "u_BlurLocation";
    private static final String UNIFORM_BLUR_SIZE = "u_BlurSize";
    PointF blurLocation;
    private int blurLocationHandle;
    float blurSize;
    private int blurSizeHandle;

    public ZoomBlurFilter(float blurSize, PointF blurLocation) {
        this.blurSize = blurSize;
        this.blurLocation = blurLocation;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_BlurSize;\nuniform vec2 u_BlurLocation;\nvoid main(){\n   vec2 samplingOffset = 1.0/100.0 * (u_BlurLocation - v_TexCoord) * u_BlurSize;\n   vec4 fragColour = texture2D(u_Texture0, v_TexCoord) * 0.18;\n   fragColour += texture2D(u_Texture0, v_TexCoord +  samplingOffset) * 0.15;\n   fragColour += texture2D(u_Texture0, v_TexCoord -  samplingOffset) * 0.15;\n   fragColour += texture2D(u_Texture0, v_TexCoord +  (2.0 * samplingOffset)) * 0.12;\n   fragColour += texture2D(u_Texture0, v_TexCoord -  (2.0 * samplingOffset)) * 0.12;\n   fragColour += texture2D(u_Texture0, v_TexCoord +  (3.0 * samplingOffset)) * 0.09;\n   fragColour += texture2D(u_Texture0, v_TexCoord -  (3.0 * samplingOffset)) * 0.09;\n   fragColour += texture2D(u_Texture0, v_TexCoord +  (4.0 * samplingOffset)) * 0.05;\n   fragColour += texture2D(u_Texture0, v_TexCoord -  (4.0 * samplingOffset)) * 0.05;\n   gl_FragColor = fragColour;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.blurSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BLUR_SIZE);
        this.blurLocationHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BLUR_LOCATION);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.blurSizeHandle, this.blurSize);
        GLES20.glUniform2f(this.blurLocationHandle, this.blurLocation.x, this.blurLocation.y);
    }
}
