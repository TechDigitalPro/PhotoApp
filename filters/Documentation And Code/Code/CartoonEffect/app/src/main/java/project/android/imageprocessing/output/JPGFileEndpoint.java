package project.android.imageprocessing.output;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.media.MediaScannerConnection;
import android.opengl.GLES20;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class JPGFileEndpoint extends GLRenderer implements GLTextureInputRenderer {
    private Context context;
    private int curNumber;
    protected int[] depthRenderBuffer;
    private String filePath;
    String filePathName;
    protected int[] frameBuffer;
    private boolean increment;
    private boolean storeToMedia;
    File tempFile = getTempFile();
    protected int[] texture_out;

    public JPGFileEndpoint(Context context, boolean storeToMedia, String filePath, boolean increment) {
        this.context = context;
        this.storeToMedia = storeToMedia;
        this.filePath = filePath;
        this.increment = increment;
        this.curNumber = 1;
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
        Bitmap image = Bitmap.createBitmap(pixels, getWidth(), getHeight(), Config.ARGB_8888);
        if (this.increment) {
            this.filePathName = this.filePath + this.curNumber;
            this.curNumber++;
        } else {
            this.filePathName = this.filePath;
        }
        try {
            OutputStream out = new FileOutputStream(this.tempFile);
            image.compress(CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            if (this.storeToMedia) {
                saveTmp();
                MediaScannerConnection.scanFile(this.context, new String[]{this.filePath}, null, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
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

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        this.texture_in = texture;
        setWidth(source.getWidth());
        setHeight(source.getHeight());
        onDrawFrame();
    }

    public void save2media(String newfile) {
        if (new File(newfile).exists()) {
            MediaScannerConnection.scanFile(this.context, new String[]{newfile}, null, null);
            return;
        }
        try {
            saveTmp(newfile);
            MediaScannerConnection.scanFile(this.context, new String[]{newfile}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getTempFile() {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "photo.jpg");
    }

    public void saveTmp() throws IOException {
        saveTmp(this.filePath);
    }

    public void saveTmp(String newfile) throws IOException {
        InputStream in = new FileInputStream(this.tempFile);
        OutputStream out = new FileOutputStream(new File(newfile));
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len <= 0) {
                in.close();
                out.close();
                return;
            }
            out.write(buf, 0, len);
        }
    }
}
