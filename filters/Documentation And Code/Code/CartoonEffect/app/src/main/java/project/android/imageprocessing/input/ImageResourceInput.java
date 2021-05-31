package project.android.imageprocessing.input;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import project.android.imageprocessing.helper.ImageHelper;

public class ImageResourceInput extends GLTextureOutputRenderer {
    private Bitmap bitmap;
    private Context context;
    private int imageHeight;
    private int imageWidth;
    private boolean newBitmap;
    private GLSurfaceView view;

    public ImageResourceInput(GLSurfaceView view, Bitmap bitmap) {
        this.view = view;
        setImage(bitmap);
    }

    public ImageResourceInput(GLSurfaceView view, Context context, int resourceId) {
        this.context = context;
        this.view = view;
        setImage(resourceId);
    }

    public ImageResourceInput(GLSurfaceView view, String pathName) {
        this.view = view;
        setImage(pathName);
    }

    protected void drawFrame() {
        if (this.newBitmap) {
            loadTexture();
        }
        super.drawFrame();
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    private void loadImage(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.imageWidth = bitmap.getWidth();
        this.imageHeight = bitmap.getHeight();
        setRenderSize(this.imageWidth, this.imageHeight);
        this.newBitmap = true;
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
        this.view.requestRender();
    }

    public void destroy() {
        super.destroy();
        if (this.texture_in != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.texture_in}, 0);
        }
        this.newBitmap = true;
    }

    private void loadTexture() {
        if (this.texture_in != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.texture_in}, 0);
        }
        this.texture_in = ImageHelper.bitmapToTexture(this.bitmap);
        this.newBitmap = false;
        markAsDirty();
    }

    public void setImage(Bitmap bitmap) {
        loadImage(bitmap);
    }

    public void setImage(int resourceId) {
        Options options = new Options();
        options.inScaled = false;
        loadImage(BitmapFactory.decodeResource(this.context.getResources(), resourceId, options));
    }

    public void setImage(String filePath) {
        Options options = new Options();
        options.inScaled = false;
        loadImage(BitmapFactory.decodeFile(filePath, options));
    }
}
