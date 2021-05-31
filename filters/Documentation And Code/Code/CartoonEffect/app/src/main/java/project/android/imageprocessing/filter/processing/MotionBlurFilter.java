package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.filter.MultiPixelRenderer;

public class MotionBlurFilter extends MultiPixelRenderer {
    private float blurSize;
    private float degree;

    public MotionBlurFilter(float blurSize, float degree) {
        this.blurSize = blurSize;
        this.degree = degree;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 step = vec2(u_TexelWidth, u_TexelHeight);\n   vec4 fragColour = texture2D(u_Texture0, v_TexCoord) * 0.18;\n   fragColour += texture2D(u_Texture0, v_TexCoord + step) * 0.15;\n   fragColour += texture2D(u_Texture0, v_TexCoord - step) * 0.15;\n   fragColour += texture2D(u_Texture0, v_TexCoord + step * 2.0) * 0.12;\n   fragColour += texture2D(u_Texture0, v_TexCoord - step * 2.0) * 0.12;\n   fragColour += texture2D(u_Texture0, v_TexCoord + step * 3.0) * 0.09;\n   fragColour += texture2D(u_Texture0, v_TexCoord - step * 3.0) * 0.09;\n   fragColour += texture2D(u_Texture0, v_TexCoord + step * 4.0) * 0.05;\n   fragColour += texture2D(u_Texture0, v_TexCoord - step * 4.0) * 0.05;\n   gl_FragColor = fragColour;\n}\n";
    }

    protected void handleSizeChange() {
        super.handleSizeChange();
        this.texelWidth = ((float) (((double) this.blurSize) * Math.cos((((double) this.degree) * 3.141592653589793d) / 180.0d))) / ((float) getWidth());
        this.texelHeight = ((float) (((double) this.blurSize) * Math.sin((((double) this.degree) * 3.141592653589793d) / 180.0d))) / ((float) getHeight());
    }
}
