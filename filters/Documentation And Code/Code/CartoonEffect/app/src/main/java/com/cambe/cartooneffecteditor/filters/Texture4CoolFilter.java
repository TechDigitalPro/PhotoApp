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

public class Texture4CoolFilter extends MultiInputFilter {
    float plakes;
    String shader;
    private Bitmap textureBitmap;
    private int textureid;

    public Texture4CoolFilter(Context context, int id, int plakes) {
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
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nuniform sampler2D u_Texture2;\nvarying vec2 v_TexCoord;\nconst float scaling =" + this.plakes + ";\n" + "const vec3 W = vec3(0.2125, 0.7154, 0.0721);\n" + "void main()\n" + "{\n" + "vec3 color;\n" + "vec3 sample4 = texture2D(" + GLRenderer.UNIFORM_TEXTURE0 + ", vec2(v_TexCoord.x, v_TexCoord.y)).rgb;\n" + "float gray = dot(sample4, W);\n" + "vec2 modi = mod(vec2(v_TexCoord.x, v_TexCoord.y), vec2(1.0/scaling))*scaling/2.0;\n" + "if(gray > 0.60)\n" + "color = vec3(1.0);\n" + "else if (gray > 0.38)\n" + "color= texture2D(" + "u_Texture" + 1 + ", modi).rgb;\n" + "else if (gray > 0.20)\n" + "color= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.5, 0.0)).rgb;\n" + "else if (gray > 0.06)\n" + "color= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.0, 0.5)).rgb;\n" + "else\n" + "color= texture2D(" + "u_Texture" + 1 + ", modi + vec2(0.5, 0.5)).rgb;\n" + "if (gray < 0.0){\n" + "vec4 color1;\n" + "vec4 color2;\n" + "color1= vec4(color, 1.0);\n" + "color2 = vec4(gray, gray, gray, 0.6);\n" + "   float r;\n" + "   if (2.0 * color2.r < color2.a) {\n" + "     r = 2.0 * color2.r * color1.r + color2.r * (1.0 - color1.a) + color1.r * (1.0 - color2.a);\n" + "   } else {\n" + "     r = color2.a * color1.a - 2.0 * (color1.a - color1.r) * (color2.a - color2.r) + color2.r * (1.0 - color1.a) + color1.r * (1.0 - color2.a);\n" + "   }\n" + "   float g;\n" + "   if (2.0 * color2.g < color2.a) {\n" + "     g = 2.0 * color2.g * color1.g + color2.g * (1.0 - color1.a) + color1.g * (1.0 - color2.a);\n" + "   } else {\n" + "     g = color2.a * color1.a - 2.0 * (color1.a - color1.g) * (color2.a - color2.g) + color2.g * (1.0 - color1.a) + color1.g * (1.0 - color2.a);\n" + "   }\n" + "   float b;\n" + "   if (2.0 * color2.b < color2.a) {\n" + "     b = 2.0 * color2.b * color1.b + color2.b * (1.0 - color1.a) + color1.b * (1.0 - color2.a);\n" + "   } else {\n" + "     b = color2.a * color1.a - 2.0 * (color1.a - color1.b) * (color2.a - color2.b) + color2.b * (1.0 - color1.a) + color1.b * (1.0 - color2.a);\n" + "   }\n" + "color = vec3(r,g,b);\n" + "}\n" + "gl_FragColor = vec4(color, 1.0);\n" + "}\n";
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
