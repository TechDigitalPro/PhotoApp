package project.android.imageprocessing.filter.effect;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class CrosshatchFilter extends BasicFilter {
    protected static final String UNIFORM_CROSS_HATCH_SPACING = "u_CrossHatchSpacing";
    protected static final String UNIFORM_LINE_WIDTH = "u_LineWidth";
    private float crossHatchSpacing;
    private int crossHatchSpacingHandle;
    private float lineWidth;
    private int lineWidthHandle;

    public CrosshatchFilter(float crossHatchSpacing, float lineWidth) {
        this.crossHatchSpacing = crossHatchSpacing;
        this.lineWidth = lineWidth;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nuniform float u_CrossHatchSpacing;\nuniform float u_LineWidth;\nconst highp vec3 W = vec3(0.2125, 0.7154, 0.0721);\nvoid main(){\n  highp float luminance = dot(texture2D(u_Texture0, v_TexCoord).rgb, W);\n  lowp vec4 colorToDisplay = vec4(1.0, 1.0, 1.0, 1.0);\n  if (luminance < 1.00) {\n    \tif (mod(v_TexCoord.x + v_TexCoord.y, u_CrossHatchSpacing) <= u_LineWidth) {\n      \tcolorToDisplay = vec4(0.0, 0.0, 0.0, 1.0);\n\t    }\n\t}\n\tif (luminance < 0.75) {\n    \tif (mod(v_TexCoord.x - v_TexCoord.y, u_CrossHatchSpacing) <= u_LineWidth) {\n       \tcolorToDisplay = vec4(0.0, 0.0, 0.0, 1.0);\n     \t}\n \t}\n\tif (luminance < 0.50) {\n    \tif (mod(v_TexCoord.x + v_TexCoord.y - (u_CrossHatchSpacing / 2.0), u_CrossHatchSpacing) <= u_LineWidth) {\n        \tcolorToDisplay = vec4(0.0, 0.0, 0.0, 1.0);\n    \t}\n\t}\n\tif (luminance < 0.3) {\n\t\tif (mod(v_TexCoord.x - v_TexCoord.y - (u_CrossHatchSpacing / 2.0), u_CrossHatchSpacing) <= u_LineWidth) {\n     \t\tcolorToDisplay = vec4(0.0, 0.0, 0.0, 1.0);\n\t\t}\n\t}\n  gl_FragColor = colorToDisplay;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.crossHatchSpacingHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_CROSS_HATCH_SPACING);
        this.lineWidthHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_LINE_WIDTH);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.crossHatchSpacingHandle, this.crossHatchSpacing);
        GLES20.glUniform1f(this.lineWidthHandle, this.lineWidth);
    }
}
