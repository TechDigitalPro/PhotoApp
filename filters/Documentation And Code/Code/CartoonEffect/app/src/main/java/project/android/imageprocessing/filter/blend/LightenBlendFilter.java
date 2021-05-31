package project.android.imageprocessing.filter.blend;

import project.android.imageprocessing.filter.MultiInputFilter;

public class LightenBlendFilter extends MultiInputFilter {
    public LightenBlendFilter() {
        super(2);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nvoid main(){\n   vec4 color1 = texture2D(u_Texture0,v_TexCoord);\n   vec4 color2 = texture2D(u_Texture1,v_TexCoord);\n   gl_FragColor = max(color1, color2);\n}\n";
    }
}
