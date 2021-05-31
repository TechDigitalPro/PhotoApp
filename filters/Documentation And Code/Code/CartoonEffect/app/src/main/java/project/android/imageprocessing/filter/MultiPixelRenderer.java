package project.android.imageprocessing.filter;

import android.opengl.GLES20;

public abstract class MultiPixelRenderer extends BasicFilter {
    protected static final String UNIFORM_TEXELHEIGHT = "u_TexelHeight";
    protected static final String UNIFORM_TEXELWIDTH = "u_TexelWidth";
    protected float texelHeight;
    private int texelHeightHandle;
    protected float texelWidth;
    private int texelWidthHandle;

    protected void handleSizeChange() {
        super.handleSizeChange();
        this.texelWidth = 1.0f / ((float) getWidth());
        this.texelHeight = 1.0f / ((float) getHeight());
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.texelWidthHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_TEXELWIDTH);
        this.texelHeightHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_TEXELHEIGHT);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.texelWidthHandle, this.texelWidth);
        GLES20.glUniform1f(this.texelHeightHandle, this.texelHeight);
    }
}
