package project.android.imageprocessing.filter.processing;

import com.cambe.cartooneffecteditor.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import project.android.imageprocessing.filter.BasicFilter;

public class FlipFilter extends BasicFilter {
    public static final int FLIP_BOTH = 2;
    public static final int FLIP_HORIZONTAL = 1;
    public static final int FLIP_VERTICAL = 0;

    public FlipFilter(int flipDirection) {
        this.textureVertices = new FloatBuffer[4];
        float minX = 0.0f;
        float maxX = 1.0f;
        float minY = 0.0f;
        float maxY = 1.0f;
        switch (flipDirection) {
            case R.styleable.View_android_theme /*0*/:
                minX = 1.0f;
                maxX = 0.0f;
                break;
            case FLIP_HORIZONTAL /*1*/:
                minY = 1.0f;
                maxY = 0.0f;
                break;
            case FLIP_BOTH /*2*/:
                minX = 1.0f;
                maxX = 0.0f;
                minY = 1.0f;
                maxY = 0.0f;
                break;
        }
        float[] texData0 = new float[]{minX, minY, maxX, minY, minX, maxY, maxX, maxY};
        this.textureVertices[0] = ByteBuffer.allocateDirect(texData0.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[0].put(texData0).position(0);
        float[] texData1 = new float[]{minY, maxX, minY, minX, maxY, maxX, maxY, minX};
        this.textureVertices[FLIP_HORIZONTAL] = ByteBuffer.allocateDirect(texData1.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[FLIP_HORIZONTAL].put(texData1).position(0);
        float[] texData2 = new float[]{maxX, maxY, minX, maxY, maxX, minY, minX, minY};
        this.textureVertices[FLIP_BOTH] = ByteBuffer.allocateDirect(texData2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[FLIP_BOTH].put(texData2).position(0);
        float[] texData3 = new float[]{maxY, minX, maxY, maxX, minY, minX, minY, maxX};
        this.textureVertices[3] = ByteBuffer.allocateDirect(texData3.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[3].put(texData3).position(0);
        for (int i = 0; i < 4; i += FLIP_HORIZONTAL) {
        }
    }
}
