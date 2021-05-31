package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class OpacityFilter extends BasicFilter {
    private static final String UNIFORM_OPACITY = "u_Opacity";
    private float opacity;
    private int opacityHandle;

    public OpacityFilter(float opacity) {
        this.opacity = opacity;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Opacity;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   gl_FragColor = vec4(color.rgb, color.a * u_Opacity);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.opacityHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_OPACITY);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.opacityHandle, this.opacity);
    }
}
