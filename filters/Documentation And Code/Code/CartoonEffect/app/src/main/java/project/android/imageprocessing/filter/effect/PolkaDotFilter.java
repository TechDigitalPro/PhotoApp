package project.android.imageprocessing.filter.effect;

import android.opengl.GLES20;

public class PolkaDotFilter extends PixellateFilter {
    protected static final String UNIFORM_DOT_SCALING = "u_DotScaling";
    private float dotScaling;
    private int dotScalingHandle;

    public PolkaDotFilter(float dotScaling, float fractionalWidth, float aspectRatio) {
        super(fractionalWidth, aspectRatio);
        this.dotScaling = dotScaling;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_FractionalWidth;\nuniform float u_AspectRatio;\nuniform highp float u_DotScaling;\nvoid main(){\n   highp vec2 sampleDivisor = vec2(u_FractionalWidth, u_FractionalWidth /  u_AspectRatio);\n   highp vec2 samplePos = v_TexCoord - mod(v_TexCoord, sampleDivisor) + 0.5 * sampleDivisor;\n   highp vec2 textureCoordinateToUse = vec2(v_TexCoord.x, (v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio));\n   highp vec2 adjustedSamplePos = vec2(samplePos.x, (samplePos.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio));\n   highp float distanceFromSamplePoint = distance(adjustedSamplePos, textureCoordinateToUse);\n   lowp float checkForPresenceWithinDot = step(distanceFromSamplePoint, (u_FractionalWidth * 0.5) * u_DotScaling);\n   gl_FragColor = vec4(texture2D(u_Texture0, samplePos ).rgb * checkForPresenceWithinDot, 1.0);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.dotScalingHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DOT_SCALING);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.dotScalingHandle, this.dotScaling);
    }
}
