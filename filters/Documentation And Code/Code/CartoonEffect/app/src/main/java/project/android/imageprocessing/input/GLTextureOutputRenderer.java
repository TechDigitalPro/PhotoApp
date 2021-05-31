package project.android.imageprocessing.input;

import android.opengl.GLES20;
import java.util.ArrayList;
import java.util.List;
import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.output.GLTextureInputRenderer;

public abstract class GLTextureOutputRenderer extends GLRenderer {
    protected int[] depthRenderBuffer;
    private boolean dirty;
    protected int[] frameBuffer;
    private Object listLock = new Object();
    private List<GLTextureInputRenderer> targets = new ArrayList();
    protected int[] texture_out;

    public synchronized void addTarget(GLTextureInputRenderer target) {
        synchronized (this.listLock) {
            this.targets.add(target);
        }
    }

    public void destroy() {
        super.destroy();
        if (this.frameBuffer != null) {
            GLES20.glDeleteFramebuffers(1, this.frameBuffer, 0);
            this.frameBuffer = null;
        }
        if (this.texture_out != null) {
            GLES20.glDeleteTextures(1, this.texture_out, 0);
            this.texture_out = null;
        }
        if (this.depthRenderBuffer != null) {
            GLES20.glDeleteRenderbuffers(1, this.depthRenderBuffer, 0);
            this.depthRenderBuffer = null;
        }
    }

    protected void drawFrame() {
        if (this.frameBuffer == null) {
            if (getWidth() != 0 && getHeight() != 0) {
                initFBO();
            } else {
                return;
            }
        }
        boolean newData = false;
        if (this.dirty) {
            newData = true;
            GLES20.glBindFramebuffer(36160, this.frameBuffer[0]);
            super.drawFrame();
            GLES20.glBindFramebuffer(36160, 0);
        }
        synchronized (this.listLock) {
            for (GLTextureInputRenderer target : this.targets) {
                target.newTextureReady(this.texture_out[0], this, newData);
            }
        }
    }

    public Object getLockObject() {
        return this.listLock;
    }

    public List<GLTextureInputRenderer> getTargets() {
        return this.targets;
    }

    protected void handleSizeChange() {
        initFBO();
    }

    private void initFBO() {
        if (this.frameBuffer != null) {
            GLES20.glDeleteFramebuffers(1, this.frameBuffer, 0);
            this.frameBuffer = null;
        }
        if (this.texture_out != null) {
            GLES20.glDeleteTextures(1, this.texture_out, 0);
            this.texture_out = null;
        }
        if (this.depthRenderBuffer != null) {
            GLES20.glDeleteRenderbuffers(1, this.depthRenderBuffer, 0);
            this.depthRenderBuffer = null;
        }
        this.frameBuffer = new int[1];
        this.texture_out = new int[1];
        this.depthRenderBuffer = new int[1];
        GLES20.glGenFramebuffers(1, this.frameBuffer, 0);
        GLES20.glGenRenderbuffers(1, this.depthRenderBuffer, 0);
        GLES20.glGenTextures(1, this.texture_out, 0);
        GLES20.glBindFramebuffer(36160, this.frameBuffer[0]);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.texture_out[0]);
        GLES20.glTexImage2D(3553, 0, 6408, getWidth(), getHeight(), 0, 6408, 5121, null);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.texture_out[0], 0);
        GLES20.glBindRenderbuffer(36161, this.depthRenderBuffer[0]);
        GLES20.glRenderbufferStorage(36161, 33189, getWidth(), getHeight());
        GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.depthRenderBuffer[0]);
        int status = GLES20.glCheckFramebufferStatus(36160);
        if (status != 36053) {
            throw new RuntimeException(this + ": Failed to set up render buffer with status " + status + " and error " + GLES20.glGetError());
        }
    }

    public void removeTarget(GLTextureInputRenderer target) {
        synchronized (this.listLock) {
            this.targets.remove(target);
        }
    }

    public void markAsDirty() {
        this.dirty = true;
    }
}
