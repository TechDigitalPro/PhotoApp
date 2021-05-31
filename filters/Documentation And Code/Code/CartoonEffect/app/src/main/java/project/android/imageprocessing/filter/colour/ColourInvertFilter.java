package project.android.imageprocessing.filter.colour;

import project.android.imageprocessing.filter.BasicFilter;

public class ColourInvertFilter extends BasicFilter {
    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nvoid main(){\n   vec4 color = texture2D(u_Texture0,v_TexCoord);\n   gl_FragColor = vec4((1.0 -color.rgb), color.a);\n}\n";
    }
}
