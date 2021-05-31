package project.android.imageprocessing.filter.effect;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class PosterizeFilter extends BasicFilter {
    private static final String UNIFORM_QUANTIZATION = "u_Quantization";
    private float quantizationLevels;
    private int quantizationLevelsHandle;

    public PosterizeFilter(float quantizationLevels) {
        this.quantizationLevels = quantizationLevels;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Quantization;\nvoid main(){\n   vec4 color = texture2D(u_Texture0, v_TexCoord);\n   vec3 posterizedImageColor = floor((color.rgb * u_Quantization) + 0.5) / u_Quantization;\n   gl_FragColor = vec4(posterizedImageColor, color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.quantizationLevelsHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_QUANTIZATION);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.quantizationLevelsHandle, this.quantizationLevels);
    }
}
