package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class ChromaKeyFilter extends BasicFilter {
    private static final String UNIFORM_COLOUR = "u_Colour";
    private static final String UNIFORM_SMOOTHING = "u_Smoothing";
    private static final String UNIFORM_THRESHOLD = "u_Threshold";
    private float[] colour;
    private int colourHandle;
    private float smoothing;
    private int smoothingHandle;
    private float threshold;
    private int thresholdHandle;

    public ChromaKeyFilter(float[] colour, float threshold, float smoothing) {
        this.colour = colour;
        this.threshold = threshold;
        this.smoothing = smoothing;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec3 u_Colour;\nuniform float u_Threshold;\nuniform float u_Smoothing;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float maskY = 0.2989 * u_Colour.r + 0.5866 * u_Colour.g + 0.1145 * u_Colour.b;   float maskCr = 0.7132 * (u_Colour.r - maskY);   float maskCb = 0.5647 * (u_Colour.b - maskY);   float Y = 0.2989 * color.r + 0.5866 * color.g + 0.1145 * color.b;   float Cr = 0.7132 * (color.r - Y);   float Cb = 0.5647 * (color.b - Y);   float blendValue = smoothstep(u_Threshold, u_Threshold + u_Smoothing, distance(vec2(Cr, Cb), vec2(maskCr, maskCb)));   gl_FragColor = vec4(color.rgb, color.a * blendValue);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.colourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_COLOUR);
        this.thresholdHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_THRESHOLD);
        this.smoothingHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SMOOTHING);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform3f(this.colourHandle, this.colour[0], this.colour[1], this.colour[2]);
        GLES20.glUniform1f(this.thresholdHandle, this.threshold);
        GLES20.glUniform1f(this.smoothingHandle, this.smoothing);
    }
}
