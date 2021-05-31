package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class LevelsFilter extends BasicFilter {
    private static final String UNIFORM_GAMMA = "u_Gamma";
    private static final String UNIFORM_MAXIN = "u_MaxIn";
    private static final String UNIFORM_MAXOUT = "u_MaxOut";
    private static final String UNIFORM_MININ = "u_MinIn";
    private static final String UNIFORM_MINOUT = "u_MinOut";
    private float gamma;
    private int gammaHandle;
    private float maxIn;
    private int maxInHandle;
    private float maxOut;
    private int maxOutHandle;
    private float minIn;
    private int minInHandle;
    private float minOut;
    private int minOutHandle;

    public LevelsFilter(float minIn, float maxIn, float gamma, float minOut, float maxOut) {
        if (gamma < 0.0f) {
            gamma = 0.0f;
        }
        this.gamma = gamma;
        this.minIn = minIn;
        this.minOut = minOut;
        this.maxIn = maxIn;
        this.maxOut = maxOut;
    }

    protected String getFragmentShader() {
        return "#define GammaCorrection(color, gamma)\t\t\t\t\t\t\t\t\t\t\t\tpow(color, 1.0 / gamma)\n#define LevelsControlInputRange(color, minInput, maxInput)\t\t\t\t\t\tmin(max(color - minInput, vec3(0.0)) / (maxInput - minInput), vec3(1.0))\n#define LevelsControlInput(color, minInput, gamma, maxInput)\t\t\t\t\t\tGammaCorrection(LevelsControlInputRange(color, minInput, maxInput), gamma)\n#define LevelsControlOutputRange(color, minOutput, maxOutput) \t\t\t\t\tmix(minOutput, maxOutput, color)\n#define LevelsControl(color, minInput, gamma, maxInput, minOutput, maxOutput) \tLevelsControlOutputRange(LevelsControlInput(color, minInput, gamma, maxInput), minOutput, maxOutput)\nprecision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Gamma;\nuniform float u_MinIn;\nuniform float u_MaxIn;\nuniform float u_MinOut;\nuniform float u_MaxOut;\nvoid main(){\n   vec4 color = texture2D(u_Texture0, v_TexCoord);\n   gl_FragColor = vec4(LevelsControl(color.rgb, vec3(u_MinIn), vec3(u_Gamma), vec3(u_MaxIn), vec3(u_MinOut), vec3(u_MaxOut)), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.gammaHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_GAMMA);
        this.minInHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MININ);
        this.maxInHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MAXIN);
        this.minOutHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MINOUT);
        this.maxOutHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MAXOUT);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.gammaHandle, this.gamma);
        GLES20.glUniform1f(this.minInHandle, this.minIn);
        GLES20.glUniform1f(this.maxInHandle, this.maxIn);
        GLES20.glUniform1f(this.minOutHandle, this.minOut);
        GLES20.glUniform1f(this.maxOutHandle, this.maxOut);
    }
}
