package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class TransformFilter extends BasicFilter {
    private static final String UNIFORM_ORTHO_MATRIX = "u_OrthoMatrix";
    private static final String UNIFORM_TRANSFORM_MATRIX = "u_TransformMatrix";
    private boolean anchorTopLeft;
    private boolean ignoreAspect;
    private float[] orthoMatrix;
    private int orthoMatrixHandle;
    private float[] transformMatrix;
    private int transformMatrixHandle;

    public TransformFilter(float[] transformMatrix, boolean ignoreAspect, boolean anchorTopLeft) {
        this.transformMatrix = transformMatrix;
        this.ignoreAspect = ignoreAspect;
        this.anchorTopLeft = anchorTopLeft;
    }

    protected String getVertexShader() {
        return "attribute vec4 a_Position;\nattribute vec2 a_TexCoord;\nuniform mat4 u_OrthoMatrix;\nuniform mat4 u_TransformMatrix;\nvarying vec2 v_TexCoord;\nvoid main() {\n  v_TexCoord = a_TexCoord;\n   gl_Position = u_TransformMatrix * vec4(a_Position.xyz, 1.0) * u_OrthoMatrix;\n}\n";
    }

    protected void handleSizeChange() {
        super.handleSizeChange();
        if (this.ignoreAspect) {
            if (this.anchorTopLeft) {
                setRenderVertices(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
            }
            loadOrthoMatrix(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            return;
        }
        float normalizedHeight = ((float) getHeight()) / ((float) getWidth());
        if (this.anchorTopLeft) {
            setRenderVertices(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, normalizedHeight, 1.0f, normalizedHeight});
        } else {
            setRenderVertices(new float[]{-1.0f, -normalizedHeight, 1.0f, -normalizedHeight, -1.0f, normalizedHeight, 1.0f, normalizedHeight});
        }
        loadOrthoMatrix(-1.0f, 1.0f, (((float) getHeight()) * -1.0f) / ((float) getWidth()), (((float) getHeight()) * 1.0f) / ((float) getWidth()), -1.0f, 1.0f);
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.transformMatrixHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_TRANSFORM_MATRIX);
        this.orthoMatrixHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_ORTHO_MATRIX);
    }

    private void loadOrthoMatrix(float left, float right, float bottom, float top, float near, float far) {
        float r_l = right - left;
        float t_b = top - bottom;
        float f_n = far - near;
        float tx = (-(right + left)) / (right - left);
        float ty = (-(top + bottom)) / (top - bottom);
        float tz = (-(far + near)) / (far - near);
        float scale = 2.0f;
        if (this.anchorTopLeft) {
            scale = 4.0f;
            tx = -1.0f;
            ty = -1.0f;
        }
        this.orthoMatrix = new float[]{scale / r_l, 0.0f, 0.0f, tx, 0.0f, scale / t_b, 0.0f, ty, 0.0f, 0.0f, scale / f_n, tz, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniformMatrix4fv(this.transformMatrixHandle, 1, false, this.transformMatrix, 0);
        GLES20.glUniformMatrix4fv(this.orthoMatrixHandle, 1, false, this.orthoMatrix, 0);
    }
}
