package project.android.imageprocessing.filter;

import android.opengl.GLES20;

import com.cambe.cartooneffecteditor.R;

import java.util.ArrayList;
import java.util.List;

import project.android.imageprocessing.filter.processing.FlipFilter;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public abstract class MultiInputFilter extends BasicFilter {
    protected List<GLTextureOutputRenderer> filterLocations;
    private int numOfInputs;
    protected int[] texture;
    private int[] textureHandle;
    protected List<GLTextureOutputRenderer> texturesReceived;

    public MultiInputFilter(int numOfInputs) {
        this.numOfInputs = numOfInputs;
        this.textureHandle = new int[(numOfInputs - 1)];
        this.texture = new int[(numOfInputs - 1)];
        this.texturesReceived = new ArrayList(numOfInputs);
        this.filterLocations = new ArrayList(numOfInputs);
    }

    public void clearRegisteredFilterLocations() {
        this.filterLocations.clear();
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        for (int i = 0; i < this.numOfInputs - 1; i++) {
            this.textureHandle[i] = GLES20.glGetUniformLocation(this.programHandle, "u_Texture" + (i + 1));
        }
    }

    public synchronized void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (!this.texturesReceived.contains(source)) {
            this.texturesReceived.add(source);
            if (newData) {
                markAsDirty();
            }
        }
        int pos = this.filterLocations.lastIndexOf(source);
        if (pos == 0) {
            this.texture_in = texture;
        } else {
            this.texture[pos - 1] = texture;
        }
        if (this.texturesReceived.size() == this.numOfInputs) {
            setWidth(source.getWidth());
            setHeight(source.getHeight());
            onDrawFrame();
            this.texturesReceived.clear();
        }
    }

    protected void passShaderValues() {
        super.passShaderValues();
        int tex = 0;
        for (int i = 0; i < this.numOfInputs - 1; i++) {
            switch (i) {
                case R.styleable.View_android_theme /*0*/:
                    tex = 33985;
                    break;
                case FlipFilter.FLIP_HORIZONTAL /*1*/:
                    tex = 33986;
                    break;
                case FlipFilter.FLIP_BOTH /*2*/:
                    tex = 33987;
                    break;
                case R.styleable.View_theme /*4*/:
                    tex = 33989;
                    break;
                case R.styleable.Toolbar_contentInsetStart /*5*/:
                    tex = 33990;
                    break;
                case R.styleable.Toolbar_contentInsetEnd /*6*/:
                    tex = 33991;
                    break;
                case R.styleable.Toolbar_contentInsetLeft /*7*/:
                    tex = 33992;
                    break;
                case R.styleable.Toolbar_contentInsetRight /*8*/:
                    tex = 33993;
                    break;
                default:
                    break;
            }
            GLES20.glActiveTexture(tex);
            GLES20.glBindTexture(3553, this.texture[i]);
            GLES20.glUniform1i(this.textureHandle[i], i + 1);
        }
    }

    public void registerFilterLocation(GLTextureOutputRenderer filter) {
        if (!this.filterLocations.contains(filter)) {
            this.filterLocations.add(filter);
        }
    }

    public void registerFilterLocation(GLTextureOutputRenderer filter, int location) {
        if (this.filterLocations.contains(filter)) {
            this.filterLocations.remove(filter);
        }
        this.filterLocations.add(location, filter);
    }
}
