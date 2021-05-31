package project.android.imageprocessing.output;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class BitmapOutput extends GLRenderer implements GLTextureInputRenderer {
    private BitmapOutputCallback callback;
    protected int[] depthRenderBuffer;
    protected int[] frameBuffer;
    protected int[] texture_out;

    public interface BitmapOutputCallback {
        void bitmapCreated(Bitmap bitmap);
    }

    public BitmapOutput(BitmapOutputCallback callback) {
        this.callback = callback;
        this.textureVertices = new FloatBuffer[4];
        float[] texData0 = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
        this.textureVertices[0] = ByteBuffer.allocateDirect(texData0.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[0].put(texData0).position(0);
        float[] texData1 = new float[]{1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
        this.textureVertices[1] = ByteBuffer.allocateDirect(texData1.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[1].put(texData1).position(0);
        float[] texData2 = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
        this.textureVertices[2] = ByteBuffer.allocateDirect(texData2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[2].put(texData2).position(0);
        float[] texData3 = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        this.textureVertices[3] = ByteBuffer.allocateDirect(texData3.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[3].put(texData3).position(0);
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

    public void drawFrame() {
        if (this.frameBuffer == null) {
            if (getWidth() != 0 && getHeight() != 0) {
                initFBO();
            } else {
                return;
            }
        }
        GLES20.glBindFramebuffer(36160, this.frameBuffer[0]);
        super.drawFrame();
        int[] pixels = new int[(getWidth() * getHeight())];
        IntBuffer intBuffer = IntBuffer.wrap(pixels);
        intBuffer.position(0);
        GLES20.glReadPixels(0, 0, getWidth(), getHeight(), 6408, 5121, intBuffer);
        GLES20.glBindFramebuffer(36160, 0);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = ((pixels[i] & -16711936) | ((pixels[i] >> 16) & 255)) | ((pixels[i] << 16) & 16711680);
        }
        this.callback.bitmapCreated(Bitmap.createBitmap(pixels, getWidth(), getHeight(), Config.ARGB_8888));
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

    public void setRotation(int rot) {
        int turns = rot - this.curRotation;
        if (turns < 0) {
            rotateClockwise90Degrees(Math.abs(turns));
        } else {
            rotateCounterClockwise90Degrees(turns);
        }
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        this.texture_in = texture;
        if (this.curRotation % 2 == 1) {
            setWidth(source.getHeight());
            setHeight(source.getWidth());
        } else {
            setWidth(source.getWidth());
            setHeight(source.getHeight());
        }
        onDrawFrame();
    }
}
