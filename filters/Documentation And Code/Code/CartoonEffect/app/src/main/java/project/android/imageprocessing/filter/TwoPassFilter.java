package project.android.imageprocessing.filter;

import android.opengl.GLES20;

public abstract class TwoPassFilter extends BasicFilter {
    private int currentPass;
    private int[] firstPassDepthRenderBuffer;
    private int[] firstPassFrameBuffer;
    private int[] firstPassTextureOut;

    public void destroy() {
        super.destroy();
        if (this.firstPassFrameBuffer != null) {
            GLES20.glDeleteFramebuffers(1, this.firstPassFrameBuffer, 0);
            this.firstPassFrameBuffer = null;
        }
        if (this.firstPassTextureOut != null) {
            GLES20.glDeleteTextures(1, this.firstPassTextureOut, 0);
            this.firstPassTextureOut = null;
        }
        if (this.firstPassDepthRenderBuffer != null) {
            GLES20.glDeleteRenderbuffers(1, this.firstPassDepthRenderBuffer, 0);
            this.firstPassDepthRenderBuffer = null;
        }
    }

    protected void drawFrame() {
        this.currentPass = 1;
        if (this.firstPassFrameBuffer == null) {
            if (getWidth() != 0 && getHeight() != 0) {
                initFBO();
            } else {
                return;
            }
        }
        GLES20.glBindFramebuffer(36160, this.firstPassFrameBuffer[0]);
        if (this.texture_in != 0) {
            GLES20.glViewport(0, 0, getWidth(), getHeight());
            GLES20.glUseProgram(this.programHandle);
            GLES20.glClear(16640);
            GLES20.glClearColor(getBackgroundRed(), getBackgroundGreen(), getBackgroundBlue(), getBackgroundAlpha());
            passShaderValues();
            GLES20.glDrawArrays(5, 0, 4);
            this.texture_in = this.firstPassTextureOut[0];
            GLES20.glBindFramebuffer(36160, 0);
            this.currentPass = 2;
            super.drawFrame();
        }
    }

    protected int getCurrentPass() {
        return this.currentPass;
    }

    private void initFBO() {
        if (this.firstPassFrameBuffer != null) {
            GLES20.glDeleteFramebuffers(1, this.firstPassFrameBuffer, 0);
            this.firstPassFrameBuffer = null;
        }
        if (this.firstPassTextureOut != null) {
            GLES20.glDeleteTextures(1, this.firstPassTextureOut, 0);
            this.firstPassTextureOut = null;
        }
        if (this.firstPassDepthRenderBuffer != null) {
            GLES20.glDeleteRenderbuffers(1, this.firstPassDepthRenderBuffer, 0);
            this.firstPassDepthRenderBuffer = null;
        }
        this.firstPassFrameBuffer = new int[1];
        this.firstPassTextureOut = new int[1];
        this.firstPassDepthRenderBuffer = new int[1];
        GLES20.glGenFramebuffers(1, this.firstPassFrameBuffer, 0);
        GLES20.glGenRenderbuffers(1, this.firstPassDepthRenderBuffer, 0);
        GLES20.glGenTextures(1, this.firstPassTextureOut, 0);
        GLES20.glBindFramebuffer(36160, this.firstPassFrameBuffer[0]);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.firstPassTextureOut[0]);
        GLES20.glTexImage2D(3553, 0, 6407, getWidth(), getHeight(), 0, 6407, 33635, null);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.firstPassTextureOut[0], 0);
        GLES20.glBindRenderbuffer(36161, this.firstPassDepthRenderBuffer[0]);
        GLES20.glRenderbufferStorage(36161, 33189, getWidth(), getHeight());
        GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.firstPassDepthRenderBuffer[0]);
        int status = GLES20.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            throw new RuntimeException(this + ": Failed to set up render buffer with status " + status + " and error " + GLES20.glGetError());
        }
    }
}
