package com.cambe.cartooneffecteditor;


import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import com.file.ib.fie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class ImagePikActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;

    Animation anim1;
    Animation anim2;

    ImageView imggg1, imggg2, imggg3;
    private Uri cameraImageUri = null;

    Context mContext = this;

    Context ads_context = this;

    public static com.file.ib.fie fie;

    String[] permissions = new String[] { Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE };


    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.image_pik);


        fie = new fie(ads_context, ads_context.getPackageName(), Ad_Id.Acccccc,
                Ad_Id.inter, Ad_Id.inter, Ad_Id.banner, Ad_Id.banner);

        fie.Splash_Screen(ads_context, false, 1);


       /* final RewardedVideoAd rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        AdRequest request = new AdRequest.Builder().build();
        rewardedVideoAd.loadAd(Ad_Id.videoads, request);
        rewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

                rewardedVideoAd.show();

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        }); */







        checkPermissions();






        anim1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);

        findViewById(R.id.galry).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivityForResult(createPickIntent(), REQUEST_LOAD_IMAGE);


            }
        });
        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });


        imggg1 = (ImageView) findViewById(R.id.imggg1);
        imggg2 = (ImageView) findViewById(R.id.imggg2);
        imggg3 = (ImageView) findViewById(R.id.imggg3);
        xx1();

    }


    void xx1() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                imggg2.setVisibility(View.VISIBLE);
                imggg2.startAnimation(anim1);
                imggg1.startAnimation(anim2);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        imggg1.setVisibility(View.GONE);
                        xx2();
                    }
                }, 500);

            }
        }, 2000);
    }

    void xx2() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                imggg3.setVisibility(View.VISIBLE);
                imggg3.startAnimation(anim1);
                imggg2.startAnimation(anim2);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        imggg2.setVisibility(View.GONE);
                        xx3();
                    }
                }, 500);

            }
        }, 2000);
    }

    void xx3() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                imggg1.setVisibility(View.VISIBLE);
                imggg1.startAnimation(anim1);
                imggg3.startAnimation(anim2);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        imggg3.setVisibility(View.GONE);
                        xx1();
                    }
                }, 500);

            }
        }, 2000);
    }


    private Intent createPickIntent() {
        Intent picImageIntent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return picImageIntent.resolveActivity(getPackageManager()) != null ? picImageIntent : null;
    }


    private Intent createCameraIntent() {
        Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        return takePictureIntent.resolveActivity(getPackageManager()) != null ? takePictureIntent : null;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = createCameraIntent();
        if (takePictureIntent != null) {
            try {

                File file = createImageFile();
                Uri uri = FileProvider.getUriForFile(mContext,
                        mContext.getPackageName() + ".provider", file);


                takePictureIntent.putExtra("output", uri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Log.e("camer", "Could not create imageFile for camera");
            }
        }
    }

    private File createImageFile() throws IOException {
        File imageFile = File.createTempFile("JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + "_", ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        this.cameraImageUri = Uri.fromFile(imageFile);
        return imageFile;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            Uri selectedImage = null;
            if (requestCode == REQUEST_LOAD_IMAGE && data != null) {
                selectedImage = data.getData();
                if (selectedImage == null) {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                selectedImage = this.cameraImageUri;
            }


            if (selectedImage != null) {
                showSelectedImage(selectedImage);
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void showSelectedImage(Uri selectedImageUri) {
        String filePath = getPath(getApplicationContext(), selectedImageUri);
        int orientation = getCameraPhotoOrientation(getApplicationContext(), selectedImageUri, filePath);
        Intent i = new Intent(getApplicationContext(), ImageActivity.class);
        i.putExtra("path", filePath);
        i.putExtra("orientation", orientation);
        startActivityForResult(i, 300);
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            int orientation = new ExifInterface(new File(imagePath).getAbsolutePath()).getAttributeInt("Orientation", REQUEST_IMAGE_CAPTURE);
            switch (orientation) {
                case 3 /*3*/:
                    rotate = 180;
                    break;
                case 6 /*6*/:
                    rotate = 90;
                    break;
                case 8 /*8*/:
                    rotate = 270;
                    break;
            }
            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getPath(Context context, Uri uri) {
        boolean isKitKatOrAbove;
        if (Build.VERSION.SDK_INT >= 19) {
            isKitKatOrAbove = true;
        } else {
            isKitKatOrAbove = false;
        }
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            String[] split;
            if (isExternalStorageDocument(uri)) {
                split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[REQUEST_IMAGE_CAPTURE];
                }
                return null;
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (!isMediaDocument(uri)) {
                return null;
            } else {
                split = DocumentsContract.getDocumentId(uri).split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                String[] selectionArgs = new String[REQUEST_IMAGE_CAPTURE];
                selectionArgs[0] = split[REQUEST_IMAGE_CAPTURE];
                return getDataColumn(context, contentUri, "_id=?", selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        } else {
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
            return null;
        }
    }


    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = new String[REQUEST_IMAGE_CAPTURE];
        projection[0] = "_data";
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded
                    .toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        fie.Exit(ads_context);
    }
}
