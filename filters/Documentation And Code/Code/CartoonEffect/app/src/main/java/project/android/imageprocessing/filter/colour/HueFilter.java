package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class HueFilter extends BasicFilter {
    private static final String UNIFORM_HUEADJUST = "u_HueAdjust";
    private float hueAdjust;
    private int hueAdjustHandle;

    public HueFilter(float hueAdjust) {
        this.hueAdjust = hueAdjust;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_HueAdjust;\nconst vec4  kRGBToYPrime = vec4 (0.299, 0.587, 0.114, 0.0);\nconst vec4  kRGBToI = vec4 (0.595716, -0.274453, -0.321263, 0.0);\nconst vec4  kRGBToQ = vec4 (0.211456, -0.522591, 0.31135, 0.0);\nconst vec4  kYIQToR = vec4 (1.0, 0.9563, 0.6210, 0.0);\nconst vec4  kYIQToG = vec4 (1.0, -0.2721, -0.6474, 0.0);\nconst vec4  kYIQToB = vec4 (1.0, -1.1070, 1.7046, 0.0);\nvoid main() {\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   float YPrime = dot(color, kRGBToYPrime);\n   float I = dot(color, kRGBToI);\n   float Q = dot(color, kRGBToQ);\n   float hue = atan(Q, I);\n   float chroma = sqrt(I * I + Q * Q);\n   hue += (-u_HueAdjust);\n   Q = chroma * sin(hue);   I = chroma * cos(hue);   vec4 yIQ = vec4(YPrime, I, Q, 0.0);\n   color.r = dot(yIQ, kYIQToR);\n   color.g = dot(yIQ, kYIQToG);\n   color.b = dot(yIQ, kYIQToB);\n   gl_FragColor = color;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.hueAdjustHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_HUEADJUST);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.hueAdjustHandle, this.hueAdjust);
    }
}
