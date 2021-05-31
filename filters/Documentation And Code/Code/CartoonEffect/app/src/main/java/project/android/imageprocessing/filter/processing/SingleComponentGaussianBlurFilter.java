package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.TwoPassMultiPixelFilter;

public class SingleComponentGaussianBlurFilter extends TwoPassMultiPixelFilter {
    private static final String UNIFORM_BLUR_SIZE = "u_BlurSize";
    private float blurSize;
    private int blurSizeHandle;

    public SingleComponentGaussianBlurFilter(float blurSize) {
        this.blurSize = blurSize;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_BlurSize;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 singleStepOffset = vec2(u_TexelWidth, u_TexelHeight);\n   int multiplier = 0;\n   vec2 blurStep = vec2(0,0);\n   vec2 blurCoordinates[9];   for(int i = 0; i < 9; i++) {\n     multiplier = (i - 4);\n     blurStep = float(multiplier) * singleStepOffset;\n     blurCoordinates[i] = v_TexCoord.xy + blurStep;\n   }\n   float sum = 0.0;\n   vec4 color = texture2D(u_Texture0, blurCoordinates[4]);\n   sum += texture2D(u_Texture0, blurCoordinates[0]).r * 0.05;\n   sum += texture2D(u_Texture0, blurCoordinates[1]).r * 0.09;\n   sum += texture2D(u_Texture0, blurCoordinates[2]).r * 0.12;\n   sum += texture2D(u_Texture0, blurCoordinates[3]).r * 0.15;\n   sum += color.r * 0.18;\n   sum += texture2D(u_Texture0, blurCoordinates[5]).r * 0.15;\n   sum += texture2D(u_Texture0, blurCoordinates[6]).r * 0.12;\n   sum += texture2D(u_Texture0, blurCoordinates[7]).r * 0.09;\n   sum += texture2D(u_Texture0, blurCoordinates[8]).r * 0.05;\n   gl_FragColor = vec4(vec3(sum), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.blurSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BLUR_SIZE);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.blurSizeHandle, this.blurSize);
    }
}
