package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.filter.TwoPassMultiPixelFilter;

public class ErosionFilter extends TwoPassMultiPixelFilter {
    private int erosionRadius;

    public ErosionFilter(int erosionRadius) {
        this.erosionRadius = erosionRadius;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_TexelWidth;\nuniform float u_TexelHeight;\nconst int dilationSize = " + ((this.erosionRadius * 2) + 1) + ";\n" + "const int dilationRadius = " + this.erosionRadius + ";\n" + "void main(){\n" + "   vec2 step = vec2(" + "u_TexelWidth" + ", " + "u_TexelHeight" + ");\n" + "   float stepIntensity[dilationSize];\n" + "   for(int i = 0; i < dilationSize; i++) {\n" + "     stepIntensity[i] = texture2D(" + GLRenderer.UNIFORM_TEXTURE0 + ", " + GLRenderer.VARYING_TEXCOORD + " + step * float(i - dilationRadius)).r;\n" + "   }\n" + "   float minValue = 1.0;\n" + "   for(int i = 0; i < dilationSize; i++) {\n" + "     minValue = min(minValue, stepIntensity[i]);\n" + "   }\n" + "   gl_FragColor = vec4(vec3(minValue), 1.0);\n" + "}\n";
    }
}
