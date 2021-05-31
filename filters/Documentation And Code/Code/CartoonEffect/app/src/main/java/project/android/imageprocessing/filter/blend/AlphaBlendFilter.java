package project.android.imageprocessing.filter.blend;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiInputFilter;

public class AlphaBlendFilter extends MultiInputFilter {
    private static final String UNIFORM_MIX_PERCENT = "u_MixPercent";
    private float mixPercent;
    private int mixPercentHandle;

    public AlphaBlendFilter(float mixPercent) {
        super(2);
        this.mixPercent = mixPercent;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_MixPercent;\nvoid main(){\n   vec4 tex1 = texture2D(u_Texture0, v_TexCoord);\n   vec4 tex2 = texture2D(u_Texture1, v_TexCoord);\n   gl_FragColor = vec4(mix(tex1.rgb, tex2.rgb, tex2.a * u_MixPercent), tex1.a);\n}\n";
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
