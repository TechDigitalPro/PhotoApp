package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.filter.TwoPassMultiPixelFilter;

public class FastBlurFilter extends TwoPassMultiPixelFilter {
    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 firstOffset = vec2(1.3846153846 * u_TexelWidth, 1.3846153846 * u_TexelHeight);\n   vec2 secondOffset = vec2(3.2307692308 * u_TexelWidth, 3.2307692308 * u_TexelHeight);\n   vec3 sum = vec3(0,0,0);\n   vec4 color = texture2D(u_Texture0, v_TexCoord);\n   sum += color.rgb * 0.2270270270;\n   sum += texture2D(u_Texture0, v_TexCoord - firstOffset).rgb * 0.3162162162;\n   sum += texture2D(u_Texture0, v_TexCoord + firstOffset).rgb * 0.3162162162;\n   sum += texture2D(u_Texture0, v_TexCoord - secondOffset).rgb * 0.0702702703;\n   sum += texture2D(u_Texture0, v_TexCoord + secondOffset).rgb * 0.0702702703;\n   gl_FragColor = vec4(sum, color.a);\n}\n";
    }
}
