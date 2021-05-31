package project.android.imageprocessing.input;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

@TargetApi(14)
public class CameraPreviewInput extends GLTextureOutputRenderer implements OnFrameAvailableListener {
    private static final String UNIFORM_CAM_MATRIX = "u_Matrix";
    private SurfaceTexture camTex;
    private Camera camera = createCamera();
    private float[] matrix = new float[16];
    private int matrixHandle;
    private GLSurfaceView view;

    public CameraPreviewInput(GLSurfaceView view) {
        this.view = view;
    }

    private void bindTexture() {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, this.texture_in);
    }

    protected Camera createCamera() {
        return Camera.open();
    }

    public void destroy() {
        super.destroy();
        if (this.camera != null) {
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
        if (this.camTex != null) {
            this.camTex.release();
            this.camTex = null;
        }
        if (this.texture_in != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.texture_in}, 0);
            this.texture_in = 0;
        }
    }

    protected void drawFrame() {
        this.camTex.updateTexImage();
        super.drawFrame();
    }

    protected String getFragmentShader() {
        return "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nuniform samplerExternalOES u_Texture0;\nvarying vec2 v_TexCoord;\nvoid main() {\n   gl_FragColor = texture2D(u_Texture0, v_TexCoord);\n}\n";
    }

    protected String getVertexShader() {
        return "uniform mat4 u_Matrix;\nattribute vec4 a_Position;\nattribute vec2 a_TexCoord;\nvarying vec2 v_TexCoord;\nvoid main() {\n   vec4 texPos = u_Matrix * vec4(a_TexCoord, 1, 1);\n   v_TexCoord = texPos.xy;\n   gl_Position = a_Position;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.matrixHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CAM_MATRIX);
    }

    protected void initWithGLContext() {
        super.initWithGLContext();
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(36197, textures[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        this.texture_in = textures[0];
        this.camTex = new SurfaceTexture(this.texture_in);
        this.camTex.setOnFrameAvailableListener(this);
        boolean failed = true;
        while (failed) {
            try {
                if (this.camera != null) {
                    this.camera.stopPreview();
                    this.camera.release();
                    this.camera = null;
                }
                this.camera = createCamera();
                this.camera.setPreviewTexture(this.camTex);
                this.camera.startPreview();
                setRenderSizeToCameraSize();
                failed = false;
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                Log.e("CameraInput", sw.toString());
                if (this.camera != null) {
                    this.camera.release();
                    this.camera = null;
                }
            }
        }
    }

    public void onFrameAvailable(SurfaceTexture arg0) {
        markAsDirty();
        this.view.requestRender();
    }

    public void onPause() {
        if (this.camera != null) {
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
    }

    public void onResume() {
        reInitialize();
    }

    protected void passShaderValues() {
        this.renderVertices.position(0);
        GLES20.glVertexAttribPointer(this.positionHandle, 2, 5126, false, 8, this.renderVertices);
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        this.textureVertices[this.curRotation].position(0);
        GLES20.glVertexAttribPointer(this.texCoordHandle, 2, 5126, false, 8, this.textureVertices[this.curRotation]);
        GLES20.glEnableVertexAttribArray(this.texCoordHandle);
        bindTexture();
        GLES20.glUniform1i(this.textureHandle, 0);
        this.camTex.getTransformMatrix(this.matrix);
        GLES20.glUniformMatrix4fv(this.matrixHandle, 1, false, this.matrix, 0);
    }

    private void setRenderSizeToCameraSize() {
        Size previewSize = this.camera.getParameters().getPreviewSize();
        setRenderSize(previewSize.width, previewSize.height);
    }
}
