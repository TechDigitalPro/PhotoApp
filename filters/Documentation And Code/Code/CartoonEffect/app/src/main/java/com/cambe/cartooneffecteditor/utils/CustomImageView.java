package com.cambe.cartooneffecteditor.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import project.android.imageprocessing.output.BitmapOutput.BitmapOutputCallback;

public class CustomImageView extends ImageView implements BitmapOutputCallback {
    Activity activiy;

    class mLoaderRuable implements Runnable {
        private final Bitmap bitmap;

        mLoaderRuable(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public void run() {
            CustomImageView.this.setImageBitmap(this.bitmap);
            CustomImageView.this.setAdjustViewBounds(true);
            CustomImageView.this.setScaleType(ScaleType.FIT_CENTER);
        }
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activiy = (Activity) context;
    }

    public void bitmapCreated(Bitmap bitmap) {
        this.activiy.runOnUiThread(new mLoaderRuable(bitmap));
    }
}
