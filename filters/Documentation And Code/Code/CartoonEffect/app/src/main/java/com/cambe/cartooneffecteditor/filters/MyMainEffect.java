package com.cambe.cartooneffecteditor.filters;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.cambe.cartooneffecteditor.ImageActivity;
import com.cambe.cartooneffecteditor.R;

import project.android.imageprocessing.filter.BasicFilter;

public abstract class MyMainEffect {
    public static String BRIGHTNESS = "BRIGHTNESS";
    public static String CONTRAST = "CONTRAST";
    public static String GAMMA = "GAMMA";
    public static String LEVELS = "LEVELS";
    public static int NOBLACK = 2;
    public static int NORMAL = 3;
    public static String SATURATION = "SATURATION";
    public static int SPARSE = 1;
    protected BasicFilter filter;

    class mSeekbarChange implements OnSeekBarChangeListener {
        private final Context context;
        private final BasicFilter mBasicFilter;
        private final String strParameter;

        mSeekbarChange(BasicFilter basicFilter, String str, Context context) {
            this.mBasicFilter = basicFilter;
            this.strParameter = str;
            this.context = context;
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.mBasicFilter.setParameter(this.strParameter, (float) progress);
            if (this.context instanceof ImageActivity) {
                ((ImageActivity) this.context).requestRender();
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    public BasicFilter getFilter() {
        return this.filter;
    }

    public void setFilter(BasicFilter filter) {
        this.filter = filter;
    }

    public BasicFilter getNewFilter(Context ctx) {
        return null;
    }

    protected View getSeekbarView(Context ctx, BasicFilter filter, int max, int drawable, String parameter) {
        LinearLayout settings_line = (LinearLayout) ((LayoutInflater) ctx.getSystemService("layout_inflater")).inflate(R.layout.settingsbar, null, false);
        SeekBar seekbar = (SeekBar) settings_line.findViewById(R.id.seekbar);
        ((ImageView) settings_line.findViewById(R.id.icon)).setImageResource(drawable);
        seekbar.setProgress((int) filter.getParameter(parameter));
        seekbar.getProgressDrawable().setColorFilter(-1, Mode.SRC_IN);
        seekbar.getThumb().setColorFilter(-1, Mode.SRC_IN);
        seekbar.setMax(max);
        seekbar.setOnSeekBarChangeListener(new mSeekbarChange(filter, parameter, ctx));
        return settings_line;
    }
}
