package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;

import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.filter.BasicFilter;

public class MonoUnsharpMaskFilter extends BasicFilter {
    private static final String UNIFORM_INTENSITY = "u_Intensity";
    private int blur;
    private float intensity;
    private int intensityHandle;

    public MonoUnsharpMaskFilter(int blurSize, float intensity) {
        this.intensity = intensity;
        this.blur = blurSize;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Intensity;\nconst int  blur_size =" + this.blur + ";\n" + "vec3 W = vec3(0.2125, 0.7154, 0.0721);\n" + "vec4 quickblur(vec2 pos)\n" + "{\n" + "vec4 pixval = vec4(0.);\n" + "float csum = 0.;\n" + "float blur_width = 1.0;\n" + "int nb = 2*blur_size+1;\n" + "for (int y=0; y<nb; y++)\n" + "{\n" + "for (int x=0; x<nb; x++)\n" + "{ \n" + "   vec2 ipos = pos + vec2(blur_width*float(x-blur_size)/256.0, blur_width*float(y-blur_size)/256.0);\n" + "   pixval+= texture2D(" + GLRenderer.UNIFORM_TEXTURE0 + ", ipos);\n" + "}\n" + "}\n" + "return pixval/pow(float(nb), 2.);\n" + "}\n" + "void main(){\n" + "   vec4 sharp = texture2D(" + GLRenderer.UNIFORM_TEXTURE0 + ", " + GLRenderer.VARYING_TEXCOORD + ");\n" + "   vec4 blur =quickblur(" + GLRenderer.VARYING_TEXCOORD + ");\n" + "   float lumsharp = dot(sharp.rgb, W);\n" + "   float lumblur = dot(blur.rgb, W);\n" + "   float difff = lumsharp - lumblur;\n" + "   float intensity = " + UNIFORM_INTENSITY + ";\n" + "   if (abs(difff) < 0.01)\n" + "   \tintensity = 0.0;\n" + "   gl_FragColor = vec4(vec3(lumsharp + difff*intensity), 1.0);\n" + "}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.intensityHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_INTENSITY);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.intensityHandle, this.intensity);
    }

    public void setIntensity(float in) {
        this.intensity = in;
    }
}
