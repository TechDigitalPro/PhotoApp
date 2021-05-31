package project.android.imageprocessing.filter.processing;

import android.opengl.GLES20;

import project.android.imageprocessing.filter.BasicFilter;

public class WideBluFilter extends BasicFilter {
    private static final String UNIFORM_BLUR = "u_Blur";
    private float blur;
    private int blurHandle;

    public WideBluFilter(float blurSize) {
        this.blur = blurSize;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_Blur;\nvec4 quickblur(vec2 pos)\n{\nvec4 pixval = vec4(0.);\nfloat csum = 0.;\nfloat blur_width = u_Blur;\nint nb = 3;\nfor (int y=0; y<nb; y++)\n{\nfor (int x=0; x<nb; x++)\n{ \n   vec2 ipos = pos + vec2(blur_width*float(x-1), blur_width*float(y-1));\n   pixval+= texture2D(u_Texture0, ipos);\n}\n}\nreturn pixval/pow(float(nb), 2.);\n}\nvoid main(){\n   vec4 blur =quickblur(v_TexCoord);\n   gl_FragColor = blur;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.blurHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BLUR);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.blurHandle, this.blur);
    }

    public void setBlur(float in) {
        this.blur = in;
    }
}
