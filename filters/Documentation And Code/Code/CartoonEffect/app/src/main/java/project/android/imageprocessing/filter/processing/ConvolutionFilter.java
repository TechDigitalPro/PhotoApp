package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;
import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.filter.MultiPixelRenderer;

public class ConvolutionFilter extends MultiPixelRenderer {
    protected static final String UNIFORM_FILTER = "u_Filter";
    private float[] filter;
    private String filterBody;
    private int filterHandle;
    private int filterSize;

    public ConvolutionFilter(float[] filter, int filterWidth, int filterHeight) {
        this.filter = filter;
        this.filterBody = createFilterBody(filterWidth, filterHeight);
        this.filterSize = filterWidth * filterHeight;
    }

    private String createFilterBody(int width, int height) {
        String filterBody = "   vec3 color = ";
        int middleWidth = (width - 1) / 2;
        int middleHeight = (height - 1) / 2;
        int j = 0;
        while (j < height) {
            for (int i = 0; i < width; i++) {
                filterBody = new StringBuilder(String.valueOf(filterBody)).append("   texture2D(u_Texture0,v_TexCoord + widthStep * ").append(i - middleWidth).append(".0 + heightStep * ").append(j - middleHeight).append(".0).rgb * ").append(UNIFORM_FILTER).append("[").append((j * width) + i).append("]").toString();
                if (i == width - 1 && j == height - 1) {
                    filterBody = new StringBuilder(String.valueOf(filterBody)).append(";\n").toString();
                } else {
                    filterBody = new StringBuilder(String.valueOf(filterBody)).append(" +\n").toString();
                }
            }
            j++;
        }
        return new StringBuilder(String.valueOf(filterBody)).append("   gl_FragColor = vec4(color, 1);\n").toString();
    }

    private int getFilterSize() {
        return this.filterSize;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nuniform float u_Filter[" + getFilterSize() + "];" + "varying vec2 " + GLRenderer.VARYING_TEXCOORD + ";\n" + "void main(){\n" + "   vec2 widthStep = vec2(" + "u_TexelWidth" + ", 0);" + "   vec2 heightStep = vec2(0, " + "u_TexelHeight" + ");" + this.filterBody + "}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.filterHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_FILTER);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1fv(this.filterHandle, this.filterSize, this.filter, 0);
    }
}
