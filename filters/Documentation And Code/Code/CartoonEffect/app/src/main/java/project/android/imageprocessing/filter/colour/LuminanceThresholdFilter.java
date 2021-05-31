package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class LuminanceThresholdFilter extends BasicFilter {
    private static final String UNIFORM_THRESHOLD = "u_Threshold";
    private float threshold;
    private int thresholdHandle;

    public LuminanceThresholdFilter(float threshold) {
        this.threshold = threshold;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Threshold;\nvec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float luminance = dot(color.rgb, luminanceWeighting);\n   gl_FragColor = vec4(vec3(step(u_Threshold, luminance)), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.thresholdHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_THRESHOLD);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.thresholdHandle, this.threshold);
    }
}
