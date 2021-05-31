package project.android.imageprocessing.output;

import project.android.imageprocessing.FastImageProcessingPipeline;
import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class ScreenEndpoint extends GLRenderer implements GLTextureInputRenderer {
    private FastImageProcessingPipeline rendererContext;

    public ScreenEndpoint(FastImageProcessingPipeline rendererContext) {
        this.rendererContext = rendererContext;
    }

    protected void initWithGLContext() {
        setRenderSize(this.rendererContext.getWidth(), this.rendererContext.getHeight());
        super.initWithGLContext();
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        this.texture_in = texture;
        setWidth(source.getWidth());
        setHeight(source.getHeight());
        onDrawFrame();
    }
}
