package project.android.imageprocessing.helper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper {
    private static int MAX_IMAGE_DIMENSION = 1290;

    public static void setMaxDimension(int max) {
        MAX_IMAGE_DIMENSION = max;
    }

    public static int bitmapToTexture(Bitmap bitmap) {
        int[] tex = new int[1];
        GLES20.glGenTextures(1, tex, 0);
        GLES20.glBindTexture(3553, tex[0]);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        return tex[0];
    }

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri, new String[]{"orientation"}, null, null, null);
        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public String getAbsolutePath(Context context, Uri uri) {
        if (VERSION.SDK_INT >= 19) {
            String[] imageColumns = new String[]{"_data"};
            Cursor imageCursor = context.getContentResolver().query(getUri(), imageColumns, "_id=" + uri.getLastPathSegment().split(":")[1], null, null);
            if (imageCursor.moveToFirst()) {
                return imageCursor.getString(imageCursor.getColumnIndex("_data"));
            }
            return null;
        }
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
        int rotatedWidth;
        int rotatedHeight;
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        Options dbo = new Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();
        int orientation = getOrientation(context, photoUri);
        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }
        is = context.getContentResolver().openInputStream(photoUri);
        int scale = 1;
        if (rotatedHeight > MAX_IMAGE_DIMENSION || rotatedWidth > MAX_IMAGE_DIMENSION) {
            scale = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log(((double) MAX_IMAGE_DIMENSION) / ((double) Math.max(rotatedWidth, rotatedHeight))) / Math.log(0.5d))));
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        Bitmap srcBitmap = BitmapFactory.decodeStream(is, null, o2);
        is.close();
        if (orientation <= 0) {
            return srcBitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) orientation);
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, String f, int orientation) throws IOException {
        int rotatedWidth;
        int rotatedHeight;
        FileInputStream is = new FileInputStream(f);
        Options dbo = new Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();
        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }
        is = new FileInputStream(f);
        int scale = 1;
        if (rotatedHeight > MAX_IMAGE_DIMENSION || rotatedWidth > MAX_IMAGE_DIMENSION) {
            scale = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log(((double) MAX_IMAGE_DIMENSION) / ((double) Math.max(rotatedWidth, rotatedHeight))) / Math.log(0.5d))));
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        Bitmap srcBitmap = BitmapFactory.decodeStream(is, null, o2);
        is.close();
        if (orientation <= 0) {
            return srcBitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) orientation);
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

    public static Bitmap getRGB_565Image(Context context, String f, int orientation) throws IOException {
        int rotatedWidth;
        int rotatedHeight;
        FileInputStream is = new FileInputStream(f);
        Options dbo = new Options();
        dbo.inPreferredConfig = Config.RGB_565;
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();
        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }
        is = new FileInputStream(f);
        int scale = 1;
        if (rotatedHeight > MAX_IMAGE_DIMENSION || rotatedWidth > MAX_IMAGE_DIMENSION) {
            scale = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log(((double) MAX_IMAGE_DIMENSION) / ((double) Math.max(rotatedWidth, rotatedHeight))) / Math.log(0.5d))));
        }
        Options o2 = new Options();
        o2.inSampleSize = scale;
        o2.inPreferredConfig = Config.RGB_565;
        Bitmap srcBitmap = BitmapFactory.decodeStream(is, null, o2);
        is.close();
        if (orientation <= 0) {
            return srcBitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) orientation);
        return Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
    }

    private Uri getUri() {
        if (Environment.getExternalStorageState().equalsIgnoreCase("mounted")) {
            return Media.EXTERNAL_CONTENT_URI;
        }
        return Media.INTERNAL_CONTENT_URI;
    }
}
