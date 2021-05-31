package com.cambe.cartooneffecteditor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import project.android.imageprocessing.output.BitmapOutput.BitmapOutputCallback;

public class ViewTarget extends ImageView implements BitmapOutputCallback {
    public ViewTarget(Context context) {
        super(context);
    }

    public void bitmapCreated(Bitmap bitmap) {
    }
}
