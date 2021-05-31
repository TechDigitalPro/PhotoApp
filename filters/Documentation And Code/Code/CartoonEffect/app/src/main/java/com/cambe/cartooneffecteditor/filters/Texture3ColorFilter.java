package com.cambe.cartooneffecteditor.filters;

import android.opengl.GLES20;

import project.android.imageprocessing.filter.BasicFilter;

public class Texture3ColorFilter extends BasicFilter {
    private static final String UNIFORM_DARK_COLOUR = "u_DarkColour";
    private static final String UNIFORM_LIGHT_COLOUR = "u_LightColour";
    private static final String UNIFORM_MEDIUM_COLOUR = "u_MediumColour";
    private float[] darkColour;
    private int darkColourHandle;
    private float[] lightColour;
    private int lightColourHandle;
    private float[] mediumColour;
    private int mediumColourHandle;

    public Texture3ColorFilter() {
        this.lightColour = new float[3];
        this.lightColour[0] = 255.0f;
        this.lightColour[1] = 255.0f;
        this.lightColour[2] = 255.0f;
        this.mediumColour = new float[3];
        this.mediumColour[0] = 215.0f;
        this.mediumColour[1] = 26.0f;
        this.mediumColour[2] = 33.0f;
        this.darkColour = new float[3];
        this.darkColour[0] = 1.0f;
        this.darkColour[1] = 48.0f;
        this.darkColour[2] = 76.0f;
    }

    public Texture3ColorFilter(float[] lightColour, float[] mediumColour, float[] darkColour) {
        this.lightColour = lightColour;
        this.mediumColour = mediumColour;
        this.darkColour = darkColour;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec3 u_LightColour;\nuniform vec3 u_MediumColour;\nuniform vec3 u_DarkColour;\nvec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n   vec3 color = texture2D(u_Texture0,v_TexCoord).rgb;\n   float gray = dot(color, luminanceWeighting);\n   vec3 light = u_LightColour;\n   vec3 medium = u_MediumColour;\n   vec3 dark = u_DarkColour;\nif(gray < 0.25)\ncolor = vec3(dark[0]/255., dark[1]/255., dark[2]/255.);\nelse if(gray >= 0.25 && gray < 0.4)\ncolor = vec3(medium[0]/255., medium[1]/255., medium[2]/255.);\nelse \ncolor = vec3(light[0]/255., light[1]/255., light[2]/255.);\ngl_FragColor = vec4(color, 1.0);\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.lightColourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_LIGHT_COLOUR);
        this.mediumColourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_MEDIUM_COLOUR);
        this.darkColourHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DARK_COLOUR);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform3f(this.lightColourHandle, this.lightColour[0], this.lightColour[1], this.lightColour[2]);
        GLES20.glUniform3f(this.mediumColourHandle, this.mediumColour[0], this.mediumColour[1], this.mediumColour[2]);
        GLES20.glUniform3f(this.darkColourHandle, this.darkColour[0], this.darkColour[1], this.darkColour[2]);
    }

    public void setMedium(int r, int g, int b) {
        this.mediumColour[0] = (float) r;
        this.mediumColour[1] = (float) g;
        this.mediumColour[2] = (float) b;
    }

    public void setLight(int r, int g, int b) {
        this.lightColour[0] = (float) r;
        this.lightColour[1] = (float) g;
        this.lightColour[2] = (float) b;
    }

    public void setDark(int r, int g, int b) {
        this.darkColour[0] = (float) r;
        this.darkColour[1] = (float) g;
        this.darkColour[2] = (float) b;
    }

    public float[] getLightColour() {
        return this.lightColour;
    }

    public float[] getMediumColour() {
        return this.mediumColour;
    }

    public float[] getDarkColour() {
        return this.darkColour;
    }
}
