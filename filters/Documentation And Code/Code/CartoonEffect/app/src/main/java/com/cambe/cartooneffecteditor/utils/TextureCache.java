package com.cambe.cartooneffecteditor.utils;

import android.graphics.Bitmap;

import java.util.HashMap;

public class TextureCache {
    private static TextureCache cache;
    private HashMap<Integer, Bitmap> mMemoryCache;

    public TextureCache() {
        cache = null;
        if (this.mMemoryCache == null) {
            int cacheSize = ((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8;
            this.mMemoryCache = new HashMap();
        }
    }

    public static TextureCache getCache() {
        if (cache == null) {
            cache = new TextureCache();
        }
        return cache;
    }

    public void addBitmapToMemoryCache(int key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            this.mMemoryCache.put(Integer.valueOf(key), bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(int key) {
        return (Bitmap) this.mMemoryCache.get(Integer.valueOf(key));
    }
}
