package project.android.imageprocessing;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class GLRenderer {
    public static final String ATTRIBUTE_POSITION = "a_Position";
    public static final String ATTRIBUTE_TEXCOORD = "a_TexCoord";
    public static final String UNIFORM_TEXTURE0 = "u_Texture0";
    protected static final String UNIFORM_TEXTUREBASE = "u_Texture";
    public static final String VARYING_TEXCOORD = "v_TexCoord";
    private float alpha;
    private float blue;
    protected int curRotation;
    private boolean customSizeSet;
    private int fragmentShaderHandle;
    private float green;
    private int height;
    private boolean initialized = false;
    protected int positionHandle;
    protected int programHandle;
    private float red;
    protected FloatBuffer renderVertices;
    private boolean sizeChanged;
    protected int texCoordHandle;
    protected int textureHandle;
    protected FloatBuffer[] textureVertices;
    protected int texture_in;
    private int vertexShaderHandle;
    private int width;

    public GLRenderer() {
        setRenderVertices(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
        this.textureVertices = new FloatBuffer[4];
        float[] texData0 = new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
        this.textureVertices[0] = ByteBuffer.allocateDirect(texData0.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[0].put(texData0).position(0);
        float[] texData1 = new float[]{0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};
        this.textureVertices[1] = ByteBuffer.allocateDirect(texData1.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[1].put(texData1).position(0);
        float[] texData2 = new float[]{1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
        this.textureVertices[2] = ByteBuffer.allocateDirect(texData2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[2].put(texData2).position(0);
        float[] texData3 = new float[]{1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        this.textureVertices[3] = ByteBuffer.allocateDirect(texData3.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.textureVertices[3].put(texData3).position(0);
        this.curRotation = 0;
        this.texture_in = 0;
        this.customSizeSet = false;
        this.initialized = false;
        this.sizeChanged = false;
    }

    protected void setRenderVertices(float[] vertices) {
        this.renderVertices = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.renderVertices.put(vertices).position(0);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    protected void setWidth(int width) {
        if (!this.customSizeSet && this.width != width) {
            this.width = width;
            this.sizeChanged = true;
        }
    }

    protected void setHeight(int height) {
        if (!this.customSizeSet && this.height != height) {
            this.height = height;
            this.sizeChanged = true;
        }
    }

    public void rotateClockwise90Degrees(int numOfTimes) {
        this.curRotation += numOfTimes;
        this.curRotation %= 4;
        if (numOfTimes % 2 == 1) {
            int temp = this.width;
            this.width = this.height;
            this.height = temp;
        }
    }

    public void rotateCounterClockwise90Degrees(int numOfTimes) {
        this.curRotation += 4 - (numOfTimes % 4);
        this.curRotation %= 4;
        if (numOfTimes % 2 == 1) {
            int temp = this.width;
            this.width = this.height;
            this.height = temp;
        }
    }

    public void setRenderSize(int width, int height) {
        this.customSizeSet = true;
        if (this.curRotation % 2 == 1) {
            this.width = height;
            this.height = width;
        } else {
            this.width = width;
            this.height = height;
        }
        this.sizeChanged = true;
    }

    protected void passShaderValues() {
        this.renderVertices.position(0);
        GLES20.glVertexAttribPointer(this.positionHandle, 2, 5126, false, 8, this.renderVertices);
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        this.textureVertices[this.curRotation].position(0);
        GLES20.glVertexAttribPointer(this.texCoordHandle, 2, 5126, false, 8, this.textureVertices[this.curRotation]);
        GLES20.glEnableVertexAttribArray(this.texCoordHandle);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.texture_in);
        GLES20.glUniform1i(this.textureHandle, 0);
    }

    protected void bindShaderAttributes() {
        GLES20.glBindAttribLocation(this.programHandle, 0, ATTRIBUTE_POSITION);
        GLES20.glBindAttribLocation(this.programHandle, 1, ATTRIBUTE_TEXCOORD);
    }

    protected void initShaderHandles() {
        this.textureHandle = GLES20.glGetUniformLocation(this.programHandle, UNIFORM_TEXTURE0);
        this.positionHandle = GLES20.glGetAttribLocation(this.programHandle, ATTRIBUTE_POSITION);
        this.texCoordHandle = GLES20.glGetAttribLocation(this.programHandle, ATTRIBUTE_TEXCOORD);
    }

    public void reInitialize() {
        this.initialized = false;
    }

    public void onDrawFrame() {
        if (!this.initialized) {
            initWithGLContext();
            this.initialized = true;
        }
        if (this.sizeChanged) {
            handleSizeChange();
            this.sizeChanged = false;
        }
        drawFrame();
    }

    public void destroy() {
        this.initialized = false;
        if (this.programHandle != 0) {
            GLES20.glDeleteProgram(this.programHandle);
            this.programHandle = 0;
        }
        if (this.vertexShaderHandle != 0) {
            GLES20.glDeleteShader(this.vertexShaderHandle);
            this.vertexShaderHandle = 0;
        }
        if (this.fragmentShaderHandle != 0) {
            GLES20.glDeleteShader(this.fragmentShaderHandle);
            this.fragmentShaderHandle = 0;
        }
    }

    protected void initWithGLContext() {
        String vertexShader = getVertexShader();
        String fragmentShader = getFragmentShader();
        this.vertexShaderHandle = GLES20.glCreateShader(35633);
        String errorInfo = "none";
        if (this.vertexShaderHandle != 0) {
            int[] compileStatus;
            GLES20.glShaderSource(this.vertexShaderHandle, vertexShader);
            GLES20.glCompileShader(this.vertexShaderHandle);
            compileStatus = new int[1];
            GLES20.glGetShaderiv(this.vertexShaderHandle, 35713, compileStatus, 0);
            if (compileStatus[0] == 0) {
                errorInfo = GLES20.glGetShaderInfoLog(this.vertexShaderHandle);
                GLES20.glDeleteShader(this.vertexShaderHandle);
                this.vertexShaderHandle = 0;
            }
        }
        if (this.vertexShaderHandle == 0) {
            throw new RuntimeException(this + ": Could not create vertex shader. Reason: " + errorInfo);
        }
        this.fragmentShaderHandle = GLES20.glCreateShader(35632);
        if (this.fragmentShaderHandle != 0) {
            GLES20.glShaderSource(this.fragmentShaderHandle, fragmentShader);
            GLES20.glCompileShader(this.fragmentShaderHandle);
           int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(this.fragmentShaderHandle, 35713, compileStatus, 0);
            if (compileStatus[0] == 0) {
                errorInfo = GLES20.glGetShaderInfoLog(this.fragmentShaderHandle);
                GLES20.glDeleteShader(this.fragmentShaderHandle);
                this.fragmentShaderHandle = 0;
            }
        }
        if (this.fragmentShaderHandle == 0) {
            throw new RuntimeException(this + ": Could not create fragment shader. Reason: " + errorInfo);
        }
        this.programHandle = GLES20.glCreateProgram();
        if (this.programHandle != 0) {
            GLES20.glAttachShader(this.programHandle, this.vertexShaderHandle);
            GLES20.glAttachShader(this.programHandle, this.fragmentShaderHandle);
            bindShaderAttributes();
            GLES20.glLinkProgram(this.programHandle);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(this.programHandle, 35714, linkStatus, 0);
            if (linkStatus[0] == 0) {
                GLES20.glDeleteProgram(this.programHandle);
                this.programHandle = 0;
            }
        }
        if (this.programHandle == 0) {
            throw new RuntimeException("Could not create program.");
        }
        initShaderHandles();
    }

    protected void handleSizeChange() {
    }

    protected void drawFrame() {
        if (this.texture_in != 0) {
            GLES20.glViewport(0, 0, this.width, this.height);
            GLES20.glUseProgram(this.programHandle);
            GLES20.glClear(16640);
            GLES20.glClearColor(getBackgroundRed(), getBackgroundGreen(), getBackgroundBlue(), getBackgroundAlpha());
            passShaderValues();
            GLES20.glDrawArrays(5, 0, 4);
        }
    }

    protected String getVertexShader() {
        return "attribute vec4 a_Position;\nattribute vec2 a_TexCoord;\nvarying vec2 v_TexCoord;\nvoid main() {\n  v_TexCoord = a_TexCoord;\n   gl_Position = a_Position;\n}\n";
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nvarying vec2 v_TexCoord;\nvoid main(){\n   gl_FragColor = texture2D(u_Texture0,v_TexCoord);\n}\n";
    }

    public void setBackgroundColour(float red, float green, float blue, float alpha) {
        setBackgroundRed(red);
        setBackgroundGreen(green);
        setBackgroundBlue(blue);
        setBackgroundAlpha(alpha);
    }

    public float getBackgroundRed() {
        return this.red;
    }

    public void setBackgroundRed(float red) {
        this.red = red;
    }

    public float getBackgroundGreen() {
        return this.green;
    }

    public void setBackgroundGreen(float green) {
        this.green = green;
    }

    public float getBackgroundBlue() {
        return this.blue;
    }

    public void setBackgroundBlue(float blue) {
        this.blue = blue;
    }

    public float getBackgroundAlpha() {
        return this.alpha;
    }

    public void setBackgroundAlpha(float alpha) {
        this.alpha = alpha;
    }
}
