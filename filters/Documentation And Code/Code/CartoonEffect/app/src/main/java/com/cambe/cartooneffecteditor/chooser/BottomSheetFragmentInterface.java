package com.cambe.cartooneffecteditor.chooser;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public interface BottomSheetFragmentInterface {
    void dismiss();

    void dismissAllowingStateLoss();

    ViewTransformer getViewTransformer();

    int show(FragmentTransaction fragmentTransaction, @IdRes int i);

    void show(FragmentManager fragmentManager, @IdRes int i);
}
