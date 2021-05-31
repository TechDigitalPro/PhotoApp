package com.cambe.cartooneffecteditor.cartoon.sketch.common.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public static int mScreenHeight;
    public static int mScreenWidth;

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MagicCamera");
        if (mediaStorageDir.exists() || mediaStorageDir.mkdirs()) {
            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date()) + ".jpg");
        }
        Log.d("MyCameraApp", "failed to create directory");
        return null;
    }
}
