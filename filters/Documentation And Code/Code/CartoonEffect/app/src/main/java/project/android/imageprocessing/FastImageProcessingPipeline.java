package project.android.imageprocessing;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FastImageProcessingPipeline implements Renderer {
    private List<GLRenderer> filtersToDestroy = new ArrayList();
    private int height;
    private boolean rendering = false;
    private List<GLRenderer> rootRenderers = new ArrayList();
    private int width;

    public void addFilterToDestroy(GLRenderer renderer) {
        synchronized (this.filtersToDestroy) {
            this.filtersToDestroy.add(renderer);
        }
    }

    public synchronized void addRootRenderer(GLRenderer rootRenderer) {
        this.rootRenderers.add(rootRenderer);
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    private synchronized boolean isRendering() {
        return this.rendering;
    }

    public void onDrawFrame(GL10 unused) {
        if (isRendering()) {
            for (int i = 0; i < this.rootRenderers.size(); i++) {
                GLRenderer rootRenderer;
                synchronized (this) {
                    rootRenderer = (GLRenderer) this.rootRenderers.get(i);
                }
                rootRenderer.onDrawFrame();
            }
        }
        synchronized (this.filtersToDestroy) {
            for (GLRenderer renderer : this.filtersToDestroy) {
                renderer.destroy();
            }
            this.filtersToDestroy.clear();
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
    }

    public synchronized void pauseRendering() {
        this.rendering = false;
    }

    public synchronized void removeRootRenderer(GLRenderer rootRenderer) {
        this.rootRenderers.remove(rootRenderer);
    }

    public synchronized void startRendering() {
        if (this.rootRenderers.size() != 0) {
            this.rendering = true;
        }
    }
}
