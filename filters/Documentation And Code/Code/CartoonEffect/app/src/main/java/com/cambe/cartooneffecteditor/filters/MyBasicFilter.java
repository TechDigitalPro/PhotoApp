package com.cambe.cartooneffecteditor.filters;

import android.opengl.GLES20;

import project.android.imageprocessing.filter.BasicFilter;

public class MyBasicFilter extends BasicFilter {
    private static final String UNIFORM_BRIGHTNESS = "u_Brightness";
    private static final String UNIFORM_CONTRAST = "u_Contrast";
    private static final String UNIFORM_SATURATION = "u_Saturation";
    private float brightness = 0.0f;
    private int brightnessHandle;
    private float contrast = 1.0f;
    private int contrastHandle;
    private float saturation = 1.0f;
    private int saturationHandle;

    public void destroy() {
        super.destroy();
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform float u_Saturation;\nuniform float u_Contrast;\nuniform float u_Brightness;\nconst vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvarying vec2 v_TexCoord;\nvoid main(){\n  vec4 lutColor = texture2D(u_Texture0,v_TexCoord);\n  if (u_Saturation != 1.0)\n  {\n   float luminance = dot(lutColor.rgb, luminanceWeighting);\n   vec3 greyScaleColor = vec3(luminance);\n   lutColor = vec4(mix(greyScaleColor, lutColor.rgb, u_Saturation), lutColor.a);\n  }\n  vec4 br = vec4(u_Brightness, u_Brightness, u_Brightness, 1);\n  lutColor = lutColor + br;\n  gl_FragColor = vec4(((lutColor.rgb - vec3(0.5)) * u_Contrast + vec3(0.5)), lutColor.a);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.saturationHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_SATURATION);
        this.contrastHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CONTRAST);
        this.brightnessHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BRIGHTNESS);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.saturationHandle, this.saturation);
        GLES20.glUniform1f(this.contrastHandle, this.contrast);
        GLES20.glUniform1f(this.brightnessHandle, this.brightness);
    }

    public void setSaturation(float sat) {
        this.saturation = sat;
    }

    public void setContrast(float sat) {
        this.contrast = sat;
    }

    public void setBrightness(float sat) {
        this.brightness = sat;
    }

    public void setParameter(String parameter, float value) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            this.brightness = (0.06f * value) - 0.3f;
        }
        if (parameter.equals(MyMainEffect.SATURATION)) {
            this.saturation = 0.1f * value;
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            this.contrast = (0.15f * value) + 0.5f;
        }
    }

    public float getParameter(String parameter) {
        if (parameter.equals(MyMainEffect.BRIGHTNESS)) {
            return (this.brightness + 0.3f) / 0.06f;
        }
        if (parameter.equals(MyMainEffect.SATURATION)) {
            return this.saturation / 0.1f;
        }
        if (parameter.equals(MyMainEffect.CONTRAST)) {
            return (this.contrast - 0.5f) / 0.15f;
        }
        return 0.0f;
    }
}
