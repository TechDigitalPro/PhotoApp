package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.CompositeFilter;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class TiltShiftFilter extends CompositeFilter {
    protected static final String UNIFORM_BOTTOM_FOCUS = "u_BottomFocus";
    protected static final String UNIFORM_FOCUS_FALLOFF = "u_FocusFalloff";
    protected static final String UNIFORM_TOP_FOCUS = "u_TopFocus";
    private GaussianBlurFilter blur;
    private float bottomFocusLevel;
    private int bottomFocusLevelHandle;
    private float focusFallOffRate;
    private int focusFallOffRateHandle;
    private float topFocusLevel;
    private int topFocusLevelHandle;

    public TiltShiftFilter(float blurSize, float topFocusLevel, float bottomFocusLevel, float focusFallOffRate) {
        super(2);
        this.topFocusLevel = topFocusLevel;
        this.bottomFocusLevel = bottomFocusLevel;
        this.focusFallOffRate = focusFallOffRate;
        this.blur = new GaussianBlurFilter(blurSize);
        this.blur.addTarget(this);
        registerInitialFilter(this.blur);
        registerTerminalFilter(this.blur);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_FocusFalloff;\nuniform float u_TopFocus;\nuniform float u_BottomFocus;\nvoid main(){\n   vec4 sharpImageColor = texture2D(u_Texture0, v_TexCoord);\n   vec4 blurredImageColor = texture2D(u_Texture1, v_TexCoord);\n   float blurIntensity = 1.0 - smoothstep(u_TopFocus - u_FocusFalloff, u_TopFocus, v_TexCoord.y);\n   blurIntensity += smoothstep(u_BottomFocus, u_BottomFocus + u_FocusFalloff, v_TexCoord.y);\n   gl_FragColor = mix(sharpImageColor, blurredImageColor, blurIntensity);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.focusFallOffRateHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_FOCUS_FALLOFF);
        this.topFocusLevelHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_TOP_FOCUS);
        this.bottomFocusLevelHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BOTTOM_FOCUS);
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.filterLocations.size() < 2 || !this.filterLocations.contains(source)) {
            clearRegisteredFilterLocations();
            registerFilterLocation(source, 0);
            registerFilterLocation(this.blur, 1);
            registerInputOutputFilter(source);
        }
        super.newTextureReady(texture, source, newData);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.focusFallOffRateHandle, this.focusFallOffRate);
        GLES20.glUniform1f(this.topFocusLevelHandle, this.topFocusLevel);
        GLES20.glUniform1f(this.bottomFocusLevelHandle, this.bottomFocusLevel);
    }
}
