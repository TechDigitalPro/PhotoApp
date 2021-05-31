package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.filter.TwoPassMultiPixelFilter;

public class BoxBlurFilter extends TwoPassMultiPixelFilter {
    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 firstOffset = vec2(1.5 * u_TexelWidth, 1.5 * u_TexelHeight);\n   vec2 secondOffset = vec2(3.5 * u_TexelWidth, 3.5 * u_TexelHeight);\n   vec4 sum = vec4(0,0,0,0);\n   sum += texture2D(u_Texture0, v_TexCoord) * 0.2;   sum += texture2D(u_Texture0, v_TexCoord - firstOffset) * 0.2;   sum += texture2D(u_Texture0, v_TexCoord + firstOffset) * 0.2;   sum += texture2D(u_Texture0, v_TexCoord - secondOffset) * 0.2;   sum += texture2D(u_Texture0, v_TexCoord + secondOffset) * 0.2;   gl_FragColor = sum;\n}\n";
    }
}
