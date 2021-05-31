package project.android.imageprocessing;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class FastImageProcessingView extends GLSurfaceView {
    public FastImageProcessingView(Context context) {
        this(context, null);
        setDebugFlags(3);
        setEGLContextClientVersion(2);
    }

    public FastImageProcessingView(Context context, AttributeSet attr) {
        super(context, attr);
        setDebugFlags(3);
        setEGLContextClientVersion(2);
    }

    public void setPipeline(FastImageProcessingPipeline pipeline) {
        setRenderer(pipeline);
        setRenderMode(0);
    }
}
