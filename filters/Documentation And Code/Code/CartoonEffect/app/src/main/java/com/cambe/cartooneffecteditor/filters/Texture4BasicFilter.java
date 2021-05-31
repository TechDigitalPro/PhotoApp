package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;

import com.cambe.cartooneffecteditor.utils.TextureCache;

import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.helper.ImageHelper;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class Texture4BasicFilter extends MultiInputFilter {
    String shader;
    private Bitmap textureBitmap;
    private int textureid;

    public Texture4BasicFilter(Context context, int id) {
        super(2);
        Options options = new Options();
        options.inScaled = false;
        options.inPreferredConfig = Config.RGB_565;
        this.textureBitmap = TextureCache.getCache().getBitmapFromMemCache(id);
        if (this.textureBitmap == null) {
            this.textureBitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
            TextureCache.getCache().addBitmapToMemoryCache(id, this.textureBitmap);
        }
    }

    public void destroy() {
        super.destroy();
        if (this.textureid != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.textureid}, 0);
            this.textureid = 0;
        }
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nuniform sampler2D u_Texture2;\nvarying vec2 v_TexCoord;\nconst vec3 W = vec3(0.2125, 0.7154, 0.0721);\nvoid main()\n{\nvec3 color;\nvec3 sample4 = texture2D(u_Texture0, vec2(v_TexCoord.x, v_TexCoord.y)).rgb;\nfloat gray = dot(sample4, W);\nif (gray > 0.20)\ncolor= vec3(1.0);\nelse if (gray > 0.05){\ncolor= texture2D(u_Texture1, vec2(v_TexCoord.x/2.0 + 0.5, v_TexCoord.y/2.0  + 0.5)).rgb;\nvec4 color2;\nvec4 color1;\ncolor2= vec4(color, 1.0);\ncolor1 = vec4(1.0*gray, 1.0*gray, 1.0*gray, 0.8);\ncolor = (color1 * (color2.a * (color1 / color1.a) + (2.0 * color2 * (1.0 - (color1 / color1.a)))) + color2 * (1.0 - color1.a) + color1 * (1.0 - color2.a)).rgb;}\nelse\ncolor = vec3(0.0);\ngl_FragColor = vec4(color, 1.0);\n}\n";
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
