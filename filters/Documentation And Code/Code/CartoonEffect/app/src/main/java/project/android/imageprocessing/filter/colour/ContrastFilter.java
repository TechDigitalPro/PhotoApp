package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class ContrastFilter extends BasicFilter {
    static final String UNIFORM_CONTRAST = "u_Contrast";
    private float contrast;
    private int contrastHandle;

    public ContrastFilter(float contrast) {
        this.contrast = contrast;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Contrast;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   gl_FragColor = vec4(((color.rgb - vec3(0.5)) * u_Contrast + vec3(0.5)), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.contrastHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CONTRAST);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.contrastHandle, this.contrast);
    }
}
