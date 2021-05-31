package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;

public class PinchDistortionFilter extends BulgeDistortionFilter {
    public PinchDistortionFilter(PointF center, float radius, float distortionAmount, float aspectRatio) {
        super(center, radius, distortionAmount, aspectRatio);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nuniform float u_Radius;\nuniform float u_DistortionAmount;\nuniform float u_AspectRatio;\nvoid main(){\n   highp vec2 textureCoordinateToUse = vec2(v_TexCoord.x, (v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio));\n   highp float dist = distance(u_Center, textureCoordinateToUse);\n   textureCoordinateToUse = v_TexCoord;\n   if (dist < u_Radius) {\n     textureCoordinateToUse -= u_Center;\n     highp float percent = 1.0 + (u_Radius - dist) / u_Radius * u_DistortionAmount;\n     textureCoordinateToUse = textureCoordinateToUse * percent;\n     textureCoordinateToUse += u_Center;\n   }\n   gl_FragColor = texture2D(u_Texture0, textureCoordinateToUse);\n}\n";
    }
}
