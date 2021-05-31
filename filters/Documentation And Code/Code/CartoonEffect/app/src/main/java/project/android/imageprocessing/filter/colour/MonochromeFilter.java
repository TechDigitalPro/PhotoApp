package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class MonochromeFilter extends BasicFilter {
    private static final String UNIFORM_COLOUR = "u_Colour";
    private static final String UNIFORM_INTENSITY = "u_Intensity";
    private float[] colour;
    private int colourHandle;
    private float intensity;
    private int intensityHandle;

    public MonochromeFilter(float[] colour, float intensity) {
        this.intensity = intensity;
        this.colour = colour;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Intensity;\nuniform vec3 u_Colour;\nvec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float luminance =  dot(color.rgb, luminanceWeighting);\n   vec4 desat = vec4(vec3(luminance), 1.0);\n   vec4 outputColour = vec4(\n     (desat.r < 0.5 ? (2.0 * desat.r * u_Colour.r) : (1.0 - 2.0 * (1.0 - desat.r) * (1.0 - u_Colour.r))),\n     (desat.g < 0.5 ? (2.0 * desat.g * u_Colour.g) : (1.0 - 2.0 * (1.0 - desat.g) * (1.0 - u_Colour.g))),\n     (desat.b < 0.5 ? (2.0 * desat.b * u_Colour.b) : (1.0 - 2.0 * (1.0 - desat.b) * (1.0 - u_Colour.b))),\n     1.0);\n   gl_FragColor = vec4(mix(color.rgb, outputColour.rgb, u_Intensity), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.intensityHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_INTENSITY);
        this.colourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_COLOUR);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.intensityHandle, this.intensity);
        GLES20.glUniform3f(this.colourHandle, this.colour[0], this.colour[1], this.colour[2]);
    }

    public void setColorls(float col1, float col2, float col3) {
        this.colour[0] = col1;
        this.colour[1] = col2;
        this.colour[2] = col3;
    }
}
