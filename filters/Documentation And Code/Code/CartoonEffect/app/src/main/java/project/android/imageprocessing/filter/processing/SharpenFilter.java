package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiPixelRenderer;

public class SharpenFilter extends MultiPixelRenderer {
    private static final String UNIFORM_SHARPEN_AMOUNT = "u_SharpenAmount";
    private float sharpenAmount;
    private int sharpenAmountHandle;

    public SharpenFilter(float sharpenAmount) {
        this.sharpenAmount = sharpenAmount;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nuniform float u_SharpenAmount;\nvoid main(){\n   vec2 singleStepOffset = vec2(u_TexelWidth, u_TexelHeight);\n   vec4 sum = vec4(0,0,0,0);\n   vec2 up = vec2(0.0, u_TexelHeight);\n   vec2 left = vec2(u_TexelWidth, 0.0);\n   sum += texture2D(u_Texture0, v_TexCoord) * (1.0 + 4.0 * u_SharpenAmount);\n   sum += texture2D(u_Texture0, v_TexCoord + up) * -u_SharpenAmount;\n   sum += texture2D(u_Texture0, v_TexCoord - up) * -u_SharpenAmount;\n   sum += texture2D(u_Texture0, v_TexCoord + left) * -u_SharpenAmount;\n   sum += texture2D(u_Texture0, v_TexCoord - left) * -u_SharpenAmount;\n   gl_FragColor = sum;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.sharpenAmountHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SHARPEN_AMOUNT);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.sharpenAmountHandle, this.sharpenAmount);
    }
}
