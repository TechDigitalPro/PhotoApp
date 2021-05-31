package project.android.imageprocessing.filter.effect;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.colour.LookupFilter;

public class MosaicFilter extends LookupFilter {
    protected static final String UNIFORM_COLOR = "u_Color";
    protected static final String UNIFORM_DISPLAY_SIZE = "u_DisplaySize";
    protected static final String UNIFORM_INPUT_SIZE = "u_InputSize";
    protected static final String UNIFORM_NUM_TILES = "u_NumTiles";
    private boolean color;
    private int colorHandle;
    private PointF displayTileSize;
    private int displayTileSizeHandle;
    private PointF inputTileSize;
    private int inputTileSizeHandle;
    private int numOfTiles;
    private int numOfTilesHandle;

    public MosaicFilter(Context context, int id, PointF inputTileSize, PointF displayTileSize, int numOfTiles, boolean color) {
        super(context, id);
        this.inputTileSize = inputTileSize;
        this.displayTileSize = displayTileSize;
        this.numOfTiles = numOfTiles;
        this.color = color;
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nuniform vec2 u_InputSize;\nuniform vec2 u_DisplaySize;\nuniform int u_NumTiles;\nuniform int u_Color;\nvoid main(){\n\tvec2 xy = v_TexCoord;\n \txy = xy - mod(xy, u_DisplaySize);\n \tvec4 lumcoeff = vec4(0.299,0.587,0.114,0.0);\n \tvec4 inputColor = texture2D(u_Texture0, xy);\n \tfloat lum = dot(inputColor,lumcoeff);\n \tlum = 1.0 - lum;\n \tfloat stepsize = 1.0 / float(u_NumTiles);\n \tfloat lumStep = (lum - mod(lum, stepsize)) / stepsize;\n \tfloat rowStep = 1.0 / u_InputSize.x;\n \tfloat x = mod(lumStep, rowStep);\n \tfloat y = floor(lumStep / rowStep);\n \tvec2 startCoord = vec2(float(x) *  u_InputSize.x, float(y) * u_InputSize.y);\n \tvec2 finalCoord = startCoord + ((v_TexCoord - xy) * (u_InputSize / u_DisplaySize));\n \tvec4 color = texture2D(u_Texture1, finalCoord);\n \tif (u_Color == 1) {\n \t\tcolor = color * inputColor;\n\t}\n\tgl_FragColor = color;\n}\n";
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        this.inputTileSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_INPUT_SIZE);
        this.displayTileSizeHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_DISPLAY_SIZE);
        this.numOfTilesHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_NUM_TILES);
        this.colorHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_COLOR);
    }

    protected void passShaderValues() {
        super.passShaderValues();
        GLES20.glUniform2f(this.inputTileSizeHandle, this.inputTileSize.x, this.inputTileSize.y);
        GLES20.glUniform2f(this.displayTileSizeHandle, this.displayTileSize.x, this.displayTileSize.y);
        GLES20.glUniform1i(this.numOfTilesHandle, this.numOfTiles);
        if (this.color) {
            GLES20.glUniform1i(this.colorHandle, 1);
        } else {
            GLES20.glUniform1i(this.colorHandle, 0);
        }
    }
}
