package com.cambe.cartooneffecteditor.utils;

import android.os.Build.VERSION;
import android.os.Environment;

import java.io.File;

public class SavingUtils {
    private static final String FOLDER_NAME = "Cambe Cartoon Photo";
    public static File GALLERY_FOLDER;

    public static void createFolders() {
        File baseDir;
        if (VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        if (baseDir == null) {
            GALLERY_FOLDER = Environment.getExternalStorageDirectory();
        }
        GALLERY_FOLDER = new File(baseDir, FOLDER_NAME);
        if (GALLERY_FOLDER.exists()) {
            String[] files = GALLERY_FOLDER.list();
            System.out.println();
        } else {
            GALLERY_FOLDER.mkdirs();
        }
        System.out.println();
    }

    private boolean isExternalStorageAvilable() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static String getNextFileName() {
        if (GALLERY_FOLDER == null || !GALLERY_FOLDER.exists()) {
            return null;
        }
        return new File(GALLERY_FOLDER, "Cartoon" + System.currentTimeMillis() + ".jpg").getAbsolutePath();
    }

    public static String getNextFileName11(int i) {
        if (GALLERY_FOLDER == null || !GALLERY_FOLDER.exists()) {
            return null;
        }
        return new File(GALLERY_FOLDER, "filter_" + i++ + ".jpg").getAbsolutePath();
    }

}
