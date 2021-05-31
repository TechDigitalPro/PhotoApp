package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class HighlightShadowFilter extends BasicFilter {
    private static final String UNIFORM_HIGHLIGHT = "u_Highlight";
    private static final String UNIFORM_SHADOW = "u_Shadow";
    private float highlight;
    private int highlightHandle;
    private float shadow;
    private int shadowHandle;

    public HighlightShadowFilter(float highlight, float shadow) {
        this.highlight = highlight;
        this.shadow = shadow;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform float u_Highlight;\nuniform float u_Shadow;\nvarying vec2 v_TexCoord;\nconst vec3 luminanceWeighting = vec3(0.3, 0.3, 0.3);\nvoid main(){\n   vec4 texColour = texture2D(u_Texture0,v_TexCoord);\n   float luminance = dot(texColour.rgb, luminanceWeighting);\n   float s = clamp((pow(luminance, 1.0/(u_Shadow+1.0)) + (-0.76)*pow(luminance, 2.0/(u_Shadow+1.0))) - luminance, 0.0, 1.0);\n   float h = clamp((1.0 - (pow(1.0-luminance, 1.0/(2.0-u_Highlight)) + (-0.8)*pow(1.0-luminance, 2.0/(2.0-u_Highlight)))) - luminance, -1.0, 0.0);\n   vec3 result = vec3(0.0, 0.0, 0.0) + ((luminance + s + h) - 0.0) * ((texColour.rgb - vec3(0.0, 0.0, 0.0))/(luminance - 0.0));\n   gl_FragColor = vec4(result, texColour.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.highlightHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_HIGHLIGHT);
        this.shadowHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SHADOW);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.highlightHandle, this.highlight);
        GLES20.glUniform1f(this.shadowHandle, this.shadow);
    }
}
