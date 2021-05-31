package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class HazeFilter extends BasicFilter {
    private static final String UNIFORM_DISTANCE = "u_Distance";
    private static final String UNIFORM_SLOPE = "u_Slope";
    private float distance;
    private int distanceHandle;
    private float slope;
    private int slopeHandle;

    public HazeFilter(float distance, float slope) {
        this.distance = distance;
        this.slope = slope;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Distance;\nuniform float u_Slope;\nvoid main(){\n   float d = v_TexCoord.y * u_Slope + u_Distance;\n   vec4 color = vec4(d);\n   vec4 c = texture2D(u_Texture0,v_TexCoord);\n   vec4 result = (c - color) / (1.0-d);\n   gl_FragColor = vec4(result.rgb, c.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.distanceHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DISTANCE);
        this.slopeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SLOPE);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.distanceHandle, this.distance);
        GLES20.glUniform1f(this.slopeHandle, this.slope);
    }
}
