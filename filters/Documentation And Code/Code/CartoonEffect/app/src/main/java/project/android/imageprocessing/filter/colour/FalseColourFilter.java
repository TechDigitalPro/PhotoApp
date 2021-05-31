package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class FalseColourFilter extends BasicFilter {
    private static final String UNIFORM_HIGHLIGHT_COLOUR = "u_HightlightColour";
    private static final String UNIFORM_SHADOW_COLOUR = "u_ShadowColour";
    private float[] highlightColour;
    private int highlightColourHandle;
    private float[] shadowColour;
    private int shadowColourHandle;

    public FalseColourFilter(float[] shadowColour, float[] highlightColour) {
        this.shadowColour = shadowColour;
        this.highlightColour = highlightColour;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec3 u_ShadowColour;\nuniform vec3 u_HightlightColour;\nvec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float luminance =  dot(color.rgb, luminanceWeighting);\n   gl_FragColor = vec4(mix(u_ShadowColour, u_HightlightColour, luminance), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.shadowColourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SHADOW_COLOUR);
        this.highlightColourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_HIGHLIGHT_COLOUR);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform3f(this.shadowColourHandle, this.shadowColour[0], this.shadowColour[1], this.shadowColour[2]);
        GLES20.glUniform3f(this.highlightColourHandle, this.highlightColour[0], this.highlightColour[1], this.highlightColour[2]);
    }
}
