package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.filter.MultiPixelRenderer;

public class WeakPixelInclusionFilter extends MultiPixelRenderer {
    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nvoid main(){\n   vec2 up = vec2(0.0, u_TexelHeight);\n   vec2 right = vec2(u_TexelWidth, 0.0);\n   float bottomLeftIntensity = texture2D(u_Texture0, v_TexCoord - up - right).r;\n   float topRightIntensity = texture2D(u_Texture0, v_TexCoord + up + right).r;\n   float topLeftIntensity = texture2D(u_Texture0, v_TexCoord + up - right).r;\n   float bottomRightIntensity = texture2D(u_Texture0, v_TexCoord - up + right).r;\n   float leftIntensity = texture2D(u_Texture0, v_TexCoord - right).r;\n   float rightIntensity = texture2D(u_Texture0, v_TexCoord + right).r;\n   float bottomIntensity = texture2D(u_Texture0, v_TexCoord - up).r;\n   float topIntensity = texture2D(u_Texture0, v_TexCoord + up).r;\n   float centerIntensity = texture2D(u_Texture0,v_TexCoord).r;\n   float pixelIntensitySum = bottomLeftIntensity + topRightIntensity + topLeftIntensity + bottomRightIntensity + leftIntensity + rightIntensity + bottomIntensity + topIntensity + centerIntensity;\n   float sumTest = step(1.5, pixelIntensitySum);\n   float pixelTest = step(0.01, centerIntensity);\n   gl_FragColor = vec4(vec3(sumTest * pixelTest), 1.0);\n}\n";
    }
}
