package project.android.imageprocessing.filter.effect;

import android.graphics.PointF;

public class SphereRefractionFilter extends BulgeDistortionFilter {
    public SphereRefractionFilter(PointF center, float radius, float refractiveIndex, float aspectRatio) {
        super(center, radius, refractiveIndex, aspectRatio);
    }

    protected String getFragmentShader() {
        return "precision highp float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform vec2 u_Center;\nuniform float u_Radius;\nuniform float u_DistortionAmount;\nuniform float u_AspectRatio;\nvoid main(){\n\tvec2 textureCoordinateToUse = vec2(v_TexCoord.x, (v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio));\n\tfloat distanceFromCenter = distance(u_Center, textureCoordinateToUse);\n \tfloat checkForPresenceWithinSphere = step(distanceFromCenter, u_Radius);\n \tdistanceFromCenter = distanceFromCenter / u_Radius;\n \tfloat normalizedDepth = u_Radius * sqrt(1.0 - distanceFromCenter * distanceFromCenter);\n \tvec3 sphereNormal = normalize(vec3(textureCoordinateToUse - u_Center, normalizedDepth));\n \tvec3 refractedVector = refract(vec3(0.0, 0.0, -1.0), sphereNormal, u_DistortionAmount);\n \tgl_FragColor = texture2D(u_Texture0, (refractedVector.xy + 1.0) * 0.5) * checkForPresenceWithinSphere;\n}\n";
    }
}
