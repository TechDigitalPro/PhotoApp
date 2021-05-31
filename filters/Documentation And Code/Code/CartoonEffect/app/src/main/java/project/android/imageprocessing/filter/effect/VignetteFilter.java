package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class VignetteFilter extends BasicFilter {
    protected static final String UNIFORM_CENTER = "u_Center";
    protected static final String UNIFORM_COLOUR = "u_Colour";
    protected static final String UNIFORM_END = "u_End";
    protected static final String UNIFORM_START = "u_Start";
    private PointF center;
    private int centerHandle;
    private float[] colour;
    private int colourHandle;
    private float end;
    private int endHandle;
    private float start;
    private int startHandle;

    public VignetteFilter(PointF center, float[] colour, float start, float end) {
        this.center = center;
        this.colour = colour;
        this.start = start;
        this.end = end;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nuniform vec3 u_Colour;\nuniform float u_Start;\nuniform float u_End;\nvoid main(){\n\tlowp vec4 color = texture2D(u_Texture0, v_TexCoord);\n \tmediump float d = distance(v_TexCoord, u_Center);\n \tlowp float percent = smoothstep(u_Start, u_End, d);\n \tgl_FragColor = vec4(mix(color.rgb, u_Colour, percent), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.centerHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CENTER);
        this.colourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_COLOUR);
        this.startHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_START);
        this.endHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_END);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.centerHandle, this.center.x, this.center.y);
        GLES20.glUniform3f(this.colourHandle, this.colour[0], this.colour[1], this.colour[2]);
        GLES20.glUniform1f(this.startHandle, this.start);
        GLES20.glUniform1f(this.endHandle, this.end);
    }
}
