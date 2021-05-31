package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class SaturationFilter extends BasicFilter {
    private static final String UNIFORM_SATURATION = "u_Saturation";
    private float saturation;
    private int saturationHandle;

    public SaturationFilter(float saturation) {
        if (saturation < 0.0f) {
            saturation = 0.0f;
        }
        this.saturation = saturation;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Saturation;\nconst vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float luminance = dot(color.rgb, luminanceWeighting);\n   vec3 greyScaleColor = vec3(luminance);\n   gl_FragColor = vec4(mix(greyScaleColor, color.rgb, u_Saturation), color.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.saturationHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SATURATION);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.saturationHandle, this.saturation);
    }

    public void setSaturation(float sat) {
        this.saturation = sat;
    }
}
