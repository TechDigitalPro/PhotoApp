package com.cambe.cartooneffecteditor.cartoon.sketch.common.view.edit;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.ImageView;

public abstract class ImageEditFragment extends Fragment {
    protected Context mContext;
    protected ImageView mMagicDisplay;
    protected onHideListener mOnHideListener;
    protected Bitmap mainBitmap;

    public interface onHideListener {
        void onHide();
    }

    protected abstract boolean isChanged();

    public ImageEditFragment(Context context, ImageView magicDisplay, Bitmap bitmap) {
        this.mMagicDisplay = magicDisplay;
        this.mContext = context;
        this.mainBitmap = bitmap;
    }

    public void onHide() {
    }

    public void setOnHideListener(onHideListener l) {
        this.mOnHideListener = l;
    }

    protected void onDialogButtonClick(DialogInterface dialog) {
        if (this.mOnHideListener != null) {
            this.mOnHideListener.onHide();
        }
        dialog.dismiss();
    }
}
