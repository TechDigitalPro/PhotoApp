package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class GammaFilter extends BasicFilter {
    private static final String UNIFORM_GAMMA = "u_Gamma";
    private float gamma;
    private int gammaHandle;

    public GammaFilter(float gamma) {
        this.gamma = gamma;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Gamma;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   gl_FragColor = vec4(pow(color.rgb, vec3(u_Gamma)), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.gammaHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_GAMMA);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.gammaHandle, this.gamma);
    }
}
