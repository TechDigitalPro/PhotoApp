package project.android.imageprocessing.filter.blend;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiInputFilter;

public class DissolveBlendFilter extends MultiInputFilter {
    private static final String UNIFORM_MIX_PERCENT = "u_MixPercent";
    private float mixPercent;
    private int mixPercentHandle;

    public DissolveBlendFilter(float mixPercent) {
        super(2);
        this.mixPercent = mixPercent;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_MixPercent;\nvoid main(){\n   vec4 color1 = texture2D(u_Texture0,v_TexCoord);\n   vec4 color2 = texture2D(u_Texture1,v_TexCoord);\n   gl_FragColor = mix(color1, color2, u_MixPercent);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.mixPercentHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MIX_PERCENT);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.mixPercentHandle, this.mixPercent);
    }
}
