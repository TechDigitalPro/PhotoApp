package project.android.imageprocessing.filter;

import project.android.imageprocessing.input.GLTextureOutputRenderer;
import project.android.imageprocessing.output.GLTextureInputRenderer;

public abstract class BasicFilter extends GLTextureOutputRenderer implements GLTextureInputRenderer {
    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (newData) {
            markAsDirty();
        }
        this.texture_in = texture;
        setWidth(source.getWidth());
        setHeight(source.getHeight());
        onDrawFrame();
    }

    public void setParameter(String parameter, float value) {
    }

    public float getParameter(String parameter) {
        return 0.0f;
    }
}
