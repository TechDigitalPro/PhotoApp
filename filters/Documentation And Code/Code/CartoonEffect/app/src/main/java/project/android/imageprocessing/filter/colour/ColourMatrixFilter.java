package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class ColourMatrixFilter extends BasicFilter {
    private static final String UNIFORM_COLORMATRIX = "u_ColorMatrix";
    private static final String UNIFORM_INTENSITY = "u_Intensity";
    private float[] colorMatrix;
    private int colorMatrixHandle;
    private float intensity;
    private int intensityHandle;

    public ColourMatrixFilter(float[] colorMatrix, float intensity) {
        this.colorMatrix = colorMatrix;
        if (intensity < 0.0f) {
            intensity = 0.0f;
        }
        if (intensity > 1.0f) {
            intensity = 1.0f;
        }
        this.intensity = intensity;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Intensity;\nuniform mat4 u_ColorMatrix;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   vec4 matrixResult = vec4(color.rgb, 1.0) * u_ColorMatrix;\n   vec4 colorResult = u_Intensity * matrixResult + (1.0 - u_Intensity) * color;\n   gl_FragColor = vec4(colorResult.rgb, color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.colorMatrixHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_COLORMATRIX);
        this.intensityHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_INTENSITY);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniformMatrix4fv(this.colorMatrixHandle, 1, false, this.colorMatrix, 0);
        GLES20.glUniform1f(this.intensityHandle, this.intensity);
    }

    protected void setMatrix(float[] filter) {
        this.colorMatrix = filter;
    }
}
