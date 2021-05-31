package project.android.imageprocessing.filter;

import android.graphics.PointF;
import android.opengl.GLES20;
import java.util.HashMap;
import java.util.Map;

public class GenericFilter extends BasicFilter {
    private String fragmentShader;
    private Map<String, float[]> uniformFloatArrayList = new HashMap();
    private Map<String, Float> uniformFloatList = new HashMap();
    private Map<String, Integer> uniformHandleList = new HashMap();
    private Map<String, Integer> uniformIntList = new HashMap();
    private Map<String, float[]> uniformMat3List = new HashMap();
    private Map<String, float[]> uniformMat4List = new HashMap();
    private Map<String, PointF> uniformVec2List = new HashMap();
    private Map<String, float[]> uniformVec3List = new HashMap();
    private Map<String, float[]> uniformVec4List = new HashMap();
    private String vertexShader;

    public GenericFilter() {
        setFragmentShader(super.getFragmentShader());
        setVertexShader(super.getVertexShader());
    }

    public void addUniformInteger(String name, int value) {
        if (!this.uniformIntList.containsKey(name)) {
            reInitialize();
        }
        this.uniformIntList.put(name, Integer.valueOf(value));
    }

    public void addUniformFloat(String name, float value) {
        if (!this.uniformFloatList.containsKey(name)) {
            reInitialize();
        }
        this.uniformFloatList.put(name, Float.valueOf(value));
    }

    public void addUniformVec2(String name, PointF value) {
        if (!this.uniformVec2List.containsKey(name)) {
            reInitialize();
        }
        this.uniformVec2List.put(name, value);
    }

    public void addUniformVec3(String name, float[] value) {
        if (!this.uniformVec3List.containsKey(name)) {
            reInitialize();
        }
        this.uniformVec3List.put(name, value);
    }

    public void addUniformVec4(String name, float[] value) {
        if (!this.uniformVec4List.containsKey(name)) {
            reInitialize();
        }
        this.uniformVec4List.put(name, value);
    }

    public void addUniformMat3(String name, float[] value) {
        if (!this.uniformMat3List.containsKey(name)) {
            reInitialize();
        }
        this.uniformMat3List.put(name, value);
    }

    public void addUniformMat4(String name, float[] value) {
        if (!this.uniformMat4List.containsKey(name)) {
            reInitialize();
        }
        this.uniformMat4List.put(name, value);
    }

    public void addUniformFloatArray(String name, float[] value) {
        if (!this.uniformFloatArrayList.containsKey(name)) {
            reInitialize();
        }
        this.uniformFloatArrayList.put(name, value);
    }

    public void setFragmentShader(String fragmentShader) {
        this.fragmentShader = fragmentShader;
        reInitialize();
    }

    public void setVertexShader(String vertexShader) {
        this.vertexShader = vertexShader;
        reInitialize();
    }

    protected String getFragmentShader() {
        return this.fragmentShader;
    }

    protected String getVertexShader() {
        return this.vertexShader;
    }

    protected void passShaderValues() {
        super.passShaderValues();
        for (String key : this.uniformIntList.keySet()) {
            GLES20.glUniform1f(((Integer) this.uniformHandleList.get(key)).intValue(), (float) ((Integer) this.uniformIntList.get(key)).intValue());
        }
        for (String key2 : this.uniformFloatList.keySet()) {
            GLES20.glUniform1f(((Integer) this.uniformHandleList.get(key2)).intValue(), ((Float) this.uniformFloatList.get(key2)).floatValue());
        }
        for (String key22 : this.uniformVec2List.keySet()) {
            GLES20.glUniform2f(((Integer) this.uniformHandleList.get(key22)).intValue(), ((PointF) this.uniformVec2List.get(key22)).x, ((PointF) this.uniformVec2List.get(key22)).y);
        }
        for (String key222 : this.uniformVec3List.keySet()) {
            GLES20.glUniform3f(((Integer) this.uniformHandleList.get(key222)).intValue(), ((float[]) this.uniformVec3List.get(key222))[0], ((float[]) this.uniformVec3List.get(key222))[1], ((float[]) this.uniformVec3List.get(key222))[2]);
        }
        for (String key2222 : this.uniformVec4List.keySet()) {
            GLES20.glUniform4f(((Integer) this.uniformHandleList.get(key2222)).intValue(), ((float[]) this.uniformVec4List.get(key2222))[0], ((float[]) this.uniformVec4List.get(key2222))[1], ((float[]) this.uniformVec4List.get(key2222))[2], ((float[]) this.uniformVec4List.get(key2222))[3]);
        }
        for (String key22222 : this.uniformMat3List.keySet()) {
            GLES20.glUniformMatrix3fv(((Integer) this.uniformHandleList.get(key22222)).intValue(), 1, false, (float[]) this.uniformMat3List.get(key22222), 0);
        }
        for (String key222222 : this.uniformMat4List.keySet()) {
            GLES20.glUniformMatrix4fv(((Integer) this.uniformHandleList.get(key222222)).intValue(), 1, false, (float[]) this.uniformMat4List.get(key222222), 0);
        }
        for (String key2222222 : this.uniformFloatArrayList.keySet()) {
            GLES20.glUniform1fv(((Integer) this.uniformHandleList.get(key2222222)).intValue(), ((float[]) this.uniformFloatArrayList.get(key2222222)).length, (float[]) this.uniformFloatArrayList.get(key2222222), 0);
        }
    }

    protected void initShaderHandles() {
        super.initShaderHandles();
        for (String key : this.uniformIntList.keySet()) {
            this.uniformHandleList.put(key, Integer.valueOf(GLES20.glGetUniformLocation(this.programHandle, key)));
        }
        for (String key2 : this.uniformFloatList.keySet()) {
            this.uniformHandleList.put(key2, Integer.valueOf(GLES20.glGetUniformLocation(this.programHandle, key2)));
        }
    }
}
