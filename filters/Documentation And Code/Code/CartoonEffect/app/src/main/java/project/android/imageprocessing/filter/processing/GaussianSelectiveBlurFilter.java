package project.android.imageprocessing.filter.processing;

import android.graphics.PointF;

public class GaussianSelectiveBlurFilter extends GaussianBlurPositionFilter {
    public GaussianSelectiveBlurFilter(float blurSize, float aspectRatio, PointF excludedCirclePoint, float excludedCircleRadius, float excludedBlurSize) {
        super(blurSize, aspectRatio, excludedCirclePoint, excludedCircleRadius, excludedBlurSize);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_BlurSize;\nuniform float u_AspectRatio;\nuniform vec2 u_ExcludeCirclePoint;\nuniform float u_ExcludeCircleRadius;\nvoid main(){\n   vec4 sharpImageColor = texture2D(u_Texture0, v_TexCoord);\n   vec4 blurredImageColor = texture2D(u_Texture1, v_TexCoord);\n   vec2 texCoordAfterAspect = vec2(v_TexCoord.x, v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio);\n   float distanceFromCenter = distance(u_ExcludeCirclePoint, texCoordAfterAspect);\n   gl_FragColor = mix(sharpImageColor, blurredImageColor, smoothstep(u_ExcludeCircleRadius - u_BlurSize, u_ExcludeCircleRadius, distanceFromCenter));\n}\n";
    }
}
