package com.cambe.cartooneffecteditor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cambe.cartooneffecteditor.cartoon.sketch.common.view.edit.ImageEditFragment.onHideListener;
import com.cambe.cartooneffecteditor.cartoon.sketch.common.view.edit.filter.ImageEditFilterView;
import com.cambe.cartooneffecteditor.filters.BeautyEffect;
import com.cambe.cartooneffecteditor.filters.C1PencilEffect;
import com.cambe.cartooneffecteditor.filters.C2SketchEffect;
import com.cambe.cartooneffecteditor.filters.ChawlkEffect;
import com.cambe.cartooneffecteditor.filters.DarkEffect;
import com.cambe.cartooneffecteditor.filters.DollerEffect;
import com.cambe.cartooneffecteditor.filters.DrawingEffect1;
import com.cambe.cartooneffecteditor.filters.DrawingEffect2;
import com.cambe.cartooneffecteditor.filters.EffectCom;
import com.cambe.cartooneffecteditor.filters.FilterChangedInterface;
import com.cambe.cartooneffecteditor.filters.MarkEffect;
import com.cambe.cartooneffecteditor.filters.MichealEffect;
import com.cambe.cartooneffecteditor.filters.MoviePosterEffect;
import com.cambe.cartooneffecteditor.filters.MyCrossEffect;
import com.cambe.cartooneffecteditor.filters.MyMainEffect;
import com.cambe.cartooneffecteditor.filters.OldPaperEffect;
import com.cambe.cartooneffecteditor.filters.PTEffect;
import com.cambe.cartooneffecteditor.filters.PaintEffect;
import com.cambe.cartooneffecteditor.filters.PaintMeEffect;
import com.cambe.cartooneffecteditor.filters.Prisma4Effect;
import com.cambe.cartooneffecteditor.filters.Prisma4Effect2;
import com.cambe.cartooneffecteditor.filters.PrismaEdgeEffect;
import com.cambe.cartooneffecteditor.filters.PrismaEffect;
import com.cambe.cartooneffecteditor.filters.PrismaNoiseEffect;
import com.cambe.cartooneffecteditor.filters.SketchColorEffect;
import com.cambe.cartooneffecteditor.filters.SmartWaterEffect;
import com.cambe.cartooneffecteditor.filters.SubPencilEffect;
import com.cambe.cartooneffecteditor.filters.WeFiveEffect1;
import com.cambe.cartooneffecteditor.filters.WefiveEffect2;
import com.cambe.cartooneffecteditor.utils.CustomImageView;
import com.cambe.cartooneffecteditor.utils.SavingUtils;

import java.io.IOException;
import java.util.ArrayList;

import project.android.imageprocessing.FastImageProcessingPipeline;
import project.android.imageprocessing.FastImageProcessingView;
import project.android.imageprocessing.filter.BasicFilter;
import project.android.imageprocessing.helper.ImageHelper;
import project.android.imageprocessing.input.GLTextureOutputRenderer;
import project.android.imageprocessing.input.ImageResourceInput;
import project.android.imageprocessing.output.BitmapOutput;
import project.android.imageprocessing.output.JPGFileEndpoint;

public class ImageActivity extends FragmentActivity implements FilterChangedInterface {
    FilterChangedInterface activity;
    boolean applied;
    private BitmapOutput bit;
    Bitmap bmImg = null;

    int currentDegree;
    MyMainEffect currentEffect;
    private BasicFilter currentFilter = null;
    ArrayList<MyMainEffect> effects;
    String filePath;
    CustomImageView imageView;
    private GLTextureOutputRenderer input;
    private Fragment mFragment;
    private int mFragmentTag = -1;

    int Adss = 0;
    Context mContext = this;

    Context ads_context = this;

    private onHideListener mOnHideListener = new onHideListener() {
        public void onHide() {
            ImageActivity.this.mRadioGroup.check(-1);
        }
    };
    private RadioGroup mRadioGroup;
    String original;
    JPGFileEndpoint output;
    private FastImageProcessingPipeline pipeline;
    private FastImageProcessingView view;

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);



        ImagePikActivity.fie.Interstial(ads_context);



        SavingUtils.createFolders();
        this.view = (FastImageProcessingView) findViewById(R.id.preview);
        this.currentDegree = 1;
        this.pipeline = new FastImageProcessingPipeline();
        this.view.setPipeline(this.pipeline);
        this.applied = false;
        Bundle extras = getIntent().getExtras();
        this.original = extras.getString("path");
        int orientation = extras.getInt("orientation");

        Log.e("original", "" + original);
        Log.e("orientation", "" + orientation);
        this.view.setAlpha(0.0f);
        this.imageView = (CustomImageView) findViewById(R.id.view_image);
        try {
            this.bmImg = ImageHelper.getCorrectlyOrientedImage(this, this.original, orientation);
            Log.e("bitmap", "" + bmImg);
            this.imageView.setImageBitmap(this.bmImg);
            this.imageView.setAdjustViewBounds(true);
            this.imageView.setScaleType(ScaleType.FIT_CENTER);
            this.input = new ImageResourceInput(this.view, this.bmImg);
        } catch (Exception e) {
            Log.e("error", "" + e);
        }
        if (this.input == null) {
            Toast.makeText(this, "Problem loading image", Toast.LENGTH_LONG).show();
            finish();
        }

        float imagewidth = (float) this.input.getWidth();
        float imageheight = (float) this.input.getHeight();
        this.filePath = SavingUtils.getNextFileName();
        this.output = new JPGFileEndpoint(this, false, this.filePath, false);
        LayoutParams layoutParams = this.view.getLayoutParams();
        layoutParams.height = (int) imageheight;
        layoutParams.width = (int) imagewidth;

        Log.e("height", "" + imageheight);
        Log.e("width", "" + imagewidth);


        this.view.setLayoutParams(layoutParams);
        this.bit = new BitmapOutput(this.imageView);
        this.pipeline.addRootRenderer(this.input);
        this.pipeline.startRendering();
        if (this.currentFilter != null) {
            this.currentFilter = this.currentEffect.getFilter();
            this.currentFilter.addTarget(this.output);
            this.currentFilter.addTarget(this.bit);
            this.input.addTarget(this.currentFilter);
        }
        addFilters(this);
        initFragments();
        if (this.mFragment.isAdded()) {
            getFragmentManager().beginTransaction().show(this.mFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, this.mFragment).show(this.mFragment).commit();
        }

        ImageView image_edit_save = (ImageView) findViewById(R.id.image_edit_save);

        image_edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aa = onSaveImage1();

                Intent intt = new Intent(ImageActivity.this, SaveActivity.class);
                intt.putExtra("path", aa);
                startActivity(intt);


            }
        });

        ImageView back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();



            }
        });


        aaa();

    }

   int iooo = 0;

    void aaa() {


        this.imageView.setImageResource(R.drawable.image);
        filterChanged(iooo);


       /* final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
           @Override
            public void run() {
                //Do something after 100ms

                onSaveImage1111(iooo);

                if (iooo == 26) {
                    Toast.makeText(ImageActivity.this, "ok done", Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("done", "" + iooo);

                    iooo++;

                    aaa();

                }


            }
        }, 3000); */


    }


    private void initFragments() {
        ImageEditFilterView mImageEditFilterView = new ImageEditFilterView(this, this.imageView, this.bmImg);
        mImageEditFilterView.setOnHideListener(this.mOnHideListener);
        this.mFragment = mImageEditFilterView;
    }

//    private void hideFragment() {
//        ((ImageEditFragment) this.mFragment).onHide();
//    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4 /*4*/:
                if (this.mFragmentTag != -1) {
//                    hideFragment();
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void requestRender() {
        this.view.requestRender();
    }

    public void filterChanged(int i) {






        Log.e("chage Effect","click");
        MyMainEffect myMainEffect = (MyMainEffect) this.effects.get(i);
        this.applied = true;
        this.currentEffect = myMainEffect;
        this.pipeline.pauseRendering();
        if (this.currentFilter != null) {
            this.input.removeTarget(this.currentFilter);
            this.currentFilter.removeTarget(this.output);
            this.currentFilter.removeTarget(this.bit);
            this.pipeline.addFilterToDestroy(this.currentFilter);
        }
        this.currentFilter = myMainEffect.getFilter();
        this.currentFilter.addTarget(this.output);
        this.currentFilter.addTarget(this.bit);
        this.input.addTarget(this.currentFilter);
        this.pipeline.startRendering();
        this.view.requestRender();
    }

    public void addFilters(Context ctx) {
        this.effects = new ArrayList();
        this.effects.add(new BeautyEffect(ctx));
        this.effects.add(new C2SketchEffect(ctx));
        this.effects.add(new PrismaEdgeEffect(ctx));
        this.effects.add(new MyCrossEffect(ctx));
        this.effects.add(new Prisma4Effect(ctx));
        this.effects.add(new SketchColorEffect(ctx));
        this.effects.add(new DollerEffect(ctx));
        this.effects.add(new ChawlkEffect(ctx));
        this.effects.add(new Prisma4Effect2(ctx));
        this.effects.add(new OldPaperEffect(ctx));
        this.effects.add(new PaintEffect(ctx));
        this.effects.add(new SmartWaterEffect(ctx));
        this.effects.add(new DarkEffect(ctx));
        this.effects.add(new EffectCom(ctx));
        this.effects.add(new PrismaEffect());
        this.effects.add(new MoviePosterEffect(ctx));
        this.effects.add(new MarkEffect(ctx));
        this.effects.add(new WeFiveEffect1(ctx));
        this.effects.add(new WefiveEffect2(ctx));
        this.effects.add(new DrawingEffect1(ctx));
        this.effects.add(new PrismaNoiseEffect(ctx));
        this.effects.add(new PaintMeEffect(ctx));
        this.effects.add(new MichealEffect());
        this.effects.add(new PTEffect(ctx));
        this.effects.add(new C1PencilEffect(ctx));
        this.effects.add(new SubPencilEffect(ctx));
        this.effects.add(new DrawingEffect2(ctx));
        this.activity = (FilterChangedInterface) ctx;
    }

    private void saveExif(String filename) {
        ExifInterface newExif = null;
        try {
            newExif = new ExifInterface(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newExif != null) {
            Bundle out = new Bundle();
            newExif.setAttribute("Orientation", Integer.toString(this.currentDegree));
            try {
                newExif.saveAttributes();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onSaveImage() {
        String newfile = SavingUtils.getNextFileName();
        this.output.save2media(newfile);
        saveExif(newfile);
        Toast.makeText(this, "Saved to file:" + newfile, Toast.LENGTH_LONG).show();

    }


    public String onSaveImage1() {
        String newfile = SavingUtils.getNextFileName();
        this.output.save2media(newfile);
        saveExif(newfile);
//        Toast.makeText(this, "Saved to file:" + newfile, Toast.LENGTH_LONG).show();
        return newfile;
    }

    public String onSaveImage1111(int i) {
        String newfile = SavingUtils.getNextFileName11(i);
        this.output.save2media(newfile);
        saveExif(newfile);

//        Toast.makeText(this, "Saved to file:" + newfile, Toast.LENGTH_LONG).show();
        return newfile;
    }


    public void onBackPressed() {
        finish();


    }


}
