package project.android.imageprocessing.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import java.io.PrintWriter;
import java.io.StringWriter;

@TargetApi(14)
public class VideoResourceInput extends GLTextureOutputRenderer implements OnFrameAvailableListener {
    private static final String UNIFORM_CAM_MATRIX = "u_Matrix";
    private Context context;
    private int id;
    private float[] matrix = new float[16];
    private int matrixHandle;
    private MediaPlayer player;
    private boolean ready;
    private boolean startWhenReady;
    private SurfaceTexture videoTex;
    private GLSurfaceView view;

    public VideoResourceInput(GLSurfaceView view, Context context, int id) {
        this.view = view;
        this.player = MediaPlayer.create(context, id);
        this.context = context;
        this.id = id;
        this.startWhenReady = false;
        this.ready = false;
        setRenderSize(this.player.getVideoWidth(), this.player.getVideoHeight());
    }

    private void bindTexture() {
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, this.texture_in);
    }

    protected void drawFrame() {
        this.videoTex.updateTexImage();
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
        this.ready = false;
        try {
            this.player = MediaPlayer.create(this.context, this.id);
        } catch (Exception e) {
            Log.e("VideoPlayer", "Failed to load video");
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            Log.e("VideoPlayer", sw.toString());
            this.player.release();
        }
        setRenderSize(this.player.getVideoWidth(), this.player.getVideoHeight());
        super.initWithGLContext();
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(36197, textures[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        this.texture_in = textures[0];
        this.videoTex = new SurfaceTexture(this.texture_in);
        this.videoTex.setOnFrameAvailableListener(this);
        this.player.setSurface(new Surface(this.videoTex));
        this.ready = true;
        if (this.startWhenReady) {
            this.player.start();
        }
    }

    public void onFrameAvailable(SurfaceTexture arg0) {
        markAsDirty();
        this.view.requestRender();
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
        this.videoTex.getTransformMatrix(this.matrix);
        GLES20.glUniformMatrix4fv(this.matrixHandle, 1, false, this.matrix, 0);
    }

    public void setVideoSource(int id) {
        this.id = id;
    }

    public boolean isPlaying() {
        return this.player.isPlaying();
    }

    public void startWhenReady() {
        if (this.ready) {
            this.player.start();
        } else {
            this.startWhenReady = true;
        }
    }

    public void stop() {
        this.player.pause();
    }

    public void destroy() {
        super.destroy();
        if (this.player.isPlaying()) {
            this.player.stop();
            this.player.release();
            this.player = null;
            this.ready = false;
        }
        if (this.texture_in != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.texture_in}, 0);
            this.texture_in = 0;
        }
    }
}
