package project.android.imageprocessing.filter.colour;

import project.android.imageprocessing.filter.CompositeFilter;
import project.android.imageprocessing.filter.processing.BoxBlurFilter;

public class AdaptiveThresholdFilter extends CompositeFilter {
    public AdaptiveThresholdFilter() {
        super(2);
        GreyScaleFilter luminance = new GreyScaleFilter();
        BoxBlurFilter blur = new BoxBlurFilter();
        luminance.addTarget(blur);
        blur.addTarget(this);
        luminance.addTarget(this);
        registerFilterLocation(luminance, 0);
        registerFilterLocation(blur, 1);
        registerInitialFilter(luminance);
        registerTerminalFilter(luminance);
        registerTerminalFilter(blur);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nvoid main(){\n   vec4 luminance = texture2D(u_Texture0,v_TexCoord);\n   vec4 blur = texture2D(u_Texture1,v_TexCoord);\n   gl_FragColor = vec4(vec3(step(blur - 0.05, luminance)), 1.0);\n}\n";
    }
}
