package com.cambe.cartooneffecteditor.cartoon.widget;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cambe.cartooneffecteditor.R;


public class BubbleSeekBar extends SeekBar {
    private int end;
    private BubbleIndicator mBubbleIndicator;
    private boolean mIsListenerSet;
    private OnBubbleSeekBarChangeListener mOnBubbleSeekBarChangeListener;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;
    private Drawable mThumbDrawable;
    private int start;

    public class BubbleIndicator {
        private int[] mDrawingLocation = new int[2];
        private Floater mPopupView;
        private boolean mShowing;
        private final WindowManager mWindowManager;
        Point screenSize = new Point();

        public BubbleIndicator(Context context) {
            this.mWindowManager = (WindowManager) context.getSystemService("window");
            this.mPopupView = new Floater(context);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            this.screenSize.set(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }

        public void showIndicator(View parent, Rect touchBounds) {
            if (!isShowing()) {
                IBinder windowToken = parent.getWindowToken();
                if (windowToken != null) {
                    LayoutParams p = createPopupLayout(windowToken);
                    p.gravity = 8388659;
                    updateLayoutParamsForPosiion(parent, p);
                    this.mShowing = true;
                    translateViewIntoPosition(touchBounds.centerX());
                    invokePopup(p);
                }
            }
        }

        private LayoutParams createPopupLayout(IBinder windowToken) {
            LayoutParams p = new LayoutParams();
            p.gravity = 8388659;
            p.width = -1;
            p.height = -1;
            p.format = -3;
            p.flags = computeFlags(p.flags);
            p.type = LayoutParams.TYPE_APPLICATION_PANEL;
            p.token = windowToken;
            p.softInputMode = 3;
            return p;
        }

        private void invokePopup(LayoutParams p) {
            this.mWindowManager.addView(this.mPopupView, p);
        }

        public void moveIndicator(Rect touchBounds, int progress) {
            if (isShowing()) {
                translateViewIntoPosition(touchBounds.centerX());
                this.mPopupView.setProgressText(progress);
            }
        }

        public void hideIndicator() {
            if (isShowing()) {
                this.mShowing = false;
                this.mWindowManager.removeView(this.mPopupView);
            }
        }

        private void translateViewIntoPosition(int x) {
            this.mPopupView.setFloatOffset(this.mDrawingLocation[0] + x);
        }

        private void updateLayoutParamsForPosiion(View anchor, LayoutParams p) {
            measureFloater();
            int measuredHeight = this.mPopupView.getMeasuredHeight();
            anchor.getLocationInWindow(this.mDrawingLocation);
            p.x = 0;
            p.y = this.mDrawingLocation[1] - measuredHeight;
            p.width = this.screenSize.x;
            p.height = measuredHeight;
        }

        private void measureFloater() {
            this.mPopupView.measure(MeasureSpec.makeMeasureSpec(this.screenSize.x, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(this.screenSize.y, MeasureSpec.AT_MOST));
        }

        private boolean isShowing() {
            return this.mShowing;
        }

        private int computeFlags(int curFlags) {
            return ((((curFlags & -426521) | 32768) | 8) | 16) | 512;
        }
    }

    private class Floater extends FrameLayout {
        public TextView mMarker;
        private int mOffset;

        public Floater(Context context) {
            super(context);
            this.mMarker = new TextView(context);
            this.mMarker.setText("0%");
            this.mMarker.setGravity(17);
            this.mMarker.setBackgroundResource(R.drawable.tooltip_bg);
            addView(this.mMarker, new FrameLayout.LayoutParams(-2, -2, 51));
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), this.mMarker.getMeasuredHeight());
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int offset = this.mOffset - ((this.mMarker.getMeasuredWidth() - this.mMarker.getPaddingLeft()) / 2);
            this.mMarker.layout(offset, 0, this.mMarker.getMeasuredWidth() + offset, this.mMarker.getMeasuredHeight());
        }

        public void setFloatOffset(int x) {
            this.mOffset = x;
            this.mMarker.offsetLeftAndRight((this.mOffset - ((this.mMarker.getMeasuredWidth() - this.mMarker.getPaddingLeft()) / 2)) - this.mMarker.getLeft());
        }

        public void setProgressText(int progress) {
            this.mMarker.setText(progress + "%");
        }
    }

    public interface OnBubbleSeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int i, boolean z);

        void onStartTrackingTouch(SeekBar seekBar);

        void onStopTrackingTouch(SeekBar seekBar);
    }

    public BubbleSeekBar(Context context) {
        this(context, null);
    }

    public BubbleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsListenerSet = false;
        this.start = 0;
        this.end = 100;
        this.mOnSeekBarChangeListener = new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                BubbleSeekBar.this.mBubbleIndicator.hideIndicator();
                if (BubbleSeekBar.this.mOnBubbleSeekBarChangeListener != null) {
                    BubbleSeekBar.this.mOnBubbleSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                BubbleSeekBar.this.mBubbleIndicator.showIndicator(seekBar, BubbleSeekBar.this.mThumbDrawable.getBounds());
                if (BubbleSeekBar.this.mOnBubbleSeekBarChangeListener != null) {
                    BubbleSeekBar.this.mOnBubbleSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    BubbleSeekBar.this.mBubbleIndicator.moveIndicator(BubbleSeekBar.this.mThumbDrawable.getBounds(), BubbleSeekBar.this.start + ((int) (((float) ((BubbleSeekBar.this.end - BubbleSeekBar.this.start) * progress)) / 100.0f)));
                }
                if (BubbleSeekBar.this.mOnBubbleSeekBarChangeListener != null) {
                    BubbleSeekBar.this.mOnBubbleSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                }
            }
        };
        this.mBubbleIndicator = new BubbleIndicator(context);
        setOnSeekBarChangeListener(this.mOnSeekBarChangeListener);
    }

    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        this.mThumbDrawable = thumb;
    }

    public void setRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void setMax() {
        super.setMax(100);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        if (this.mIsListenerSet) {
            Log.e("BubbleSeekBar", "Use OnBubbleSeekBarChangeListener instead of OnSeekBarChangeListener!!!!!");
        } else {
            super.setOnSeekBarChangeListener(l);
        }
        this.mIsListenerSet = true;
    }

    public void setOnBubbleSeekBarChangeListener(OnBubbleSeekBarChangeListener l) {
        this.mOnBubbleSeekBarChangeListener = l;
    }
}
