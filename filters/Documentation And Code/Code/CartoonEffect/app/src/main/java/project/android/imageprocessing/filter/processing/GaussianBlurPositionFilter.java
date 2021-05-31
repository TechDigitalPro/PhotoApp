package project.android.imageprocessing.filter.processing;

import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.CompositeFilter;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class GaussianBlurPositionFilter extends CompositeFilter {
    protected static final String UNIFORM_ASPECT_RATIO = "u_AspectRatio";
    protected static final String UNIFORM_BLUR_SIZE = "u_BlurSize";
    protected static final String UNIFORM_EXCLUDE_CIRCLE_POINT = "u_ExcludeCirclePoint";
    protected static final String UNIFORM_EXCLUDE_CIRCLE_RADIUS = "u_ExcludeCircleRadius";
    private float aspectRatio;
    private int aspectRatioHandle;
    private GaussianBlurFilter blur;
    private float blurSize;
    private int blurSizeHandle;
    private PointF excludedCirclePoint;
    private int excludedCirclePointHandle;
    private float excludedCircleRadius;
    private int excludedCircleRadiusHandle;

    public GaussianBlurPositionFilter(float blurSize, float aspectRatio, PointF excludedCirclePoint, float excludedCircleRadius, float excludedBlurSize) {
        super(2);
        this.blurSize = excludedBlurSize;
        this.aspectRatio = aspectRatio;
        this.excludedCirclePoint = excludedCirclePoint;
        this.excludedCircleRadius = excludedCircleRadius;
        this.blur = new GaussianBlurFilter(blurSize);
        this.blur.addTarget(this);
        registerInitialFilter(this.blur);
        registerTerminalFilter(this.blur);
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform float u_BlurSize;\nuniform float u_AspectRatio;\nuniform vec2 u_ExcludeCirclePoint;\nuniform float u_ExcludeCircleRadius;\nvoid main(){\n   vec4 sharpImageColor = texture2D(u_Texture0, v_TexCoord);\n   vec4 blurredImageColor = texture2D(u_Texture1, v_TexCoord);\n   vec2 texCoordAfterAspect = vec2(v_TexCoord.x, v_TexCoord.y * u_AspectRatio + 0.5 - 0.5 * u_AspectRatio);\n   float distanceFromCenter = distance(u_ExcludeCirclePoint, texCoordAfterAspect);\n   gl_FragColor = mix(blurredImageColor, sharpImageColor, smoothstep(u_ExcludeCircleRadius - u_BlurSize, u_ExcludeCircleRadius, distanceFromCenter));\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.blurSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_BLUR_SIZE);
        this.aspectRatioHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_ASPECT_RATIO);
        this.excludedCirclePointHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_EXCLUDE_CIRCLE_POINT);
        this.excludedCircleRadiusHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_EXCLUDE_CIRCLE_RADIUS);
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.filterLocations.size() < 2 || !this.filterLocations.contains(source)) {
            clearRegisteredFilterLocations();
            registerFilterLocation(source, 0);
            registerFilterLocation(this.blur, 1);
            registerInputOutputFilter(source);
        }
        super.newTextureReady(texture, source, newData);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform1f(this.blurSizeHandle, this.blurSize);
        GLES20.glUniform1f(this.aspectRatioHandle, this.aspectRatio);
        GLES20.glUniform1f(this.excludedCircleRadiusHandle, this.excludedCircleRadius);
        GLES20.glUniform2f(this.excludedCirclePointHandle, this.excludedCirclePoint.x, this.excludedCirclePoint.y);
    }
}
