package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;

import com.cambe.cartooneffecteditor.utils.TextureCache;

import project.android.imageprocessing.GLRenderer;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.helper.ImageHelper;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class TextureMixFilter extends MultiInputFilter {
    float plakes;
    String shader;
    private Bitmap textureBitmap;
    private int textureid;

    public TextureMixFilter(Context context, int id, int plakes) {
        super(2);
        Options options = new Options();
        options.inScaled = false;
        options.inPreferredConfig = Config.RGB_565;
        this.textureBitmap = TextureCache.getCache().getBitmapFromMemCache(id);
        if (this.textureBitmap == null) {
            this.textureBitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
            TextureCache.getCache().addBitmapToMemoryCache(id, this.textureBitmap);
        }
        this.plakes = (float) plakes;
    }

    public void destroy() {
        super.destroy();
        if (this.textureid != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.textureid}, 0);
            this.textureid = 0;
        }
    }

    protected String getFragmentShader() {
        return "precision highp float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nconst float scaling =" + this.plakes + ";\n" + "const vec3 W = vec3(0.2125, 0.7154, 0.0721);\n" + "highp float lum(lowp vec3 c) {\n" + "  return dot(c, vec3(0.3, 0.59, 0.11));\n" + "}\n" + "vec3 setlum(lowp vec3 c, highp float l2) {\n" + "  float d = l2 - lum(c);\n" + "  c = c + vec3(d);\n" + "  float l = lum(c);\n" + "  float n = min(min(c.r, c.g), c.b);\n" + "  float x = max(max(c.r, c.g), c.b);\n" + "  if (n < 0.0) {\n" + "    c.r = l + ((c.r - l) * l) / (l - n);\n" + "    c.g = l + ((c.g - l) * l) / (l - n);\n" + "    c.b = l + ((c.b - l) * l) / (l - n);\n" + "  }\n" + "  if (x > 1.0) {\n" + "    c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);\n" + "    c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);\n" + "    c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);\n" + "  }\n" + "  return c;\n" + "}\n" + "void main()\n" + "{\n" + "vec3 color1;\n" + "vec3 color2 = texture2D(" + GLRenderer.UNIFORM_TEXTURE0 + ", vec2(v_TexCoord.x, v_TexCoord.y)).rgb;\n" + "float gray = dot(color2, W);\n" + "vec2 modi = mod(vec2(v_TexCoord.x, v_TexCoord.y), vec2(1.0/scaling))*scaling/2.0;\n" + "if(gray > 0.75)\n" + "color1= texture2D(" + "u_Texture" + 1 + ", modi).rgb;\n" + "else if (gray > 0.50)\n" + "color1= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.5, 0.0)).rgb;\n" + "else if (gray > 0.25)\n" + "color1= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.0, 0.5)).rgb;\n" + "else\n" + "color1= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.5, 0.5)).rgb;\n" + "   gl_FragColor = vec4( setlum(color2.rgb, lum(color1.rgb)), 1.0);\n" + "}\n";
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.filterLocations.size() < 2 || !source.equals(this.filterLocations.get(0))) {
            clearRegisteredFilterLocations();
            registerFilterLocation(source, 0);
            registerFilterLocation(this, 1);
        }
        if (this.textureid == 0) {
            this.textureid = ImageHelper.bitmapToTexture(this.textureBitmap);
        }
        super.newTextureReady(this.textureid, this, newData);
        super.newTextureReady(texture, source, newData);
    }
}
