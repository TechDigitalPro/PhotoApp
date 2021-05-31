package com.cambe.cartooneffecteditor.cartoon.sketch.common.view.edit.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cambe.cartooneffecteditor.R;
import com.cambe.cartooneffecteditor.cartoon.sketch.common.view.FilterLayoutUtils;
import com.cambe.cartooneffecteditor.cartoon.sketch.common.view.edit.ImageEditFragment;


public class ImageEditFilterView extends ImageEditFragment {
    private FilterLayoutUtils mFilterLayoutUtils;


    public ImageEditFilterView(Context context, ImageView magicDisplay, Bitmap maBitmap) {
        super(context, magicDisplay, maBitmap);
        Log.e("ImageEditFilterView","yes");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.e("ImageEditfiter onCreate","yes");
        return inflater.inflate(R.layout.fragment_image_edit_filter, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mFilterLayoutUtils = new FilterLayoutUtils(getActivity(), this.mMagicDisplay, this.mainBitmap);
        this.mFilterLayoutUtils.init(getView());
        super.onCreate(savedInstanceState);
    }

    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            this.mFilterLayoutUtils.init(getView());
        }
    }

    protected boolean isChanged() {
        return this.mFilterLayoutUtils.getFilterType() != 0;
    }
}
