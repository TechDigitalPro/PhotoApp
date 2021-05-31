package project.android.imageprocessing.filter.colour;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLES20;
import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.helper.ImageHelper;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class LookupFilter extends MultiInputFilter {
    private Bitmap lookupBitmap;
    private int lookup_texture;

    public LookupFilter(Context context, int id) {
        super(2);
        Options options = new Options();
        options.inScaled = false;
        this.lookupBitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
    }

    public void destroy() {
        super.destroy();
        if (this.lookup_texture != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.lookup_texture}, 0);
            this.lookup_texture = 0;
        }
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nvoid main(){\n  vec4 texColour = texture2D(u_Texture0,v_TexCoord);\n  float blueColor = texColour.b * 63.0;\n  vec2 quad1;\n  quad1.y = floor(floor(blueColor) / 8.0);\n  quad1.x = floor(blueColor) - (quad1.y * 8.0);\n  vec2 quad2;\n  quad2.y = floor(ceil(blueColor) / 8.0);\n  quad2.x = ceil(blueColor) - (quad2.y * 8.0);\n  vec2 texPos1;\n  texPos1.x = (quad1.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * texColour.r);\n  texPos1.y = (quad1.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * texColour.g);\n  vec2 texPos2;\n  texPos2.x = (quad2.x * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * texColour.r);\n  texPos2.y = (quad2.y * 0.125) + 0.5/512.0 + ((0.125 - 1.0/512.0) * texColour.g);\n  vec4 newColor1 = texture2D(u_Texture1, texPos1);\n  vec4 newColor2 = texture2D(u_Texture1, texPos2);\n  vec4 newColor = mix(newColor1, newColor2, fract(blueColor));\n  gl_FragColor = vec4(newColor.rgb, texColour.a);\n}\n";
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.filterLocations.size() < 2 || !source.equals(this.filterLocations.get(0))) {
            clearRegisteredFilterLocations();
            registerFilterLocation(source, 0);
            registerFilterLocation(this, 1);
        }
        if (this.lookup_texture == 0) {
            this.lookup_texture = ImageHelper.bitmapToTexture(this.lookupBitmap);
        }
        super.newTextureReady(this.lookup_texture, this, newData);
        super.newTextureReady(texture, source, newData);
    }
}
