package com.cambe.cartooneffecteditor.cartoon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.cambe.cartooneffecteditor.R;

import java.util.Map;
import java.util.TreeMap;

public class TwoLineSeekBar extends View {
    private Rect mCircleRect;
    private int mCurrentValue;
    private float mDefaultAreaRadius;
    private OnSeekDefaultListener mDefaultListener;
    private int mDefaultValue;
    private OnSeekDownListener mDownListener;
    private boolean mEnableTouch;
    private GestureDetector mGestureDetector;
    private SeekBarGestureListener mGestureListener;
    private Paint mHighLightLinePaint;
    private boolean mIsGlobalDrag;
    private boolean mIsTouchCircle;
    private Paint mLinePaint1;
    private Paint mLinePaint2;
    private float mLineWidth;
    private OnSeekChangeListener mListener;
    private int mMaxValue;
    private float mNailOffset;
    private Paint mNailPaint;
    private float mNailRadius;
    private float mNailStrokeWidth;
    private Map<String, Integer> mSavedColors;
    private Scroller mScroller;
    private float mSeekLength;
    private float mSeekLineEnd;
    private float mSeekLineStart;
    private int mStartValue;
    private float mStep;
    private boolean mSupportSingleTap;
    private float mThumbOffset;
    private Paint mThumbPaint;
    private float mThumbRadius;

    public interface OnSeekChangeListener {
        void onSeekChanged(float f, float f2);

        void onSeekStopped(float f, float f2);
    }

    public interface OnSeekDefaultListener {
        void onSeekDefaulted(float f);
    }

    public interface OnSeekDownListener {
        void onSeekDown();
    }

    class SeekBarGestureListener extends SimpleOnGestureListener {
        SeekBarGestureListener() {
        }

        public boolean onUp(MotionEvent e) {
            float initThumbOffset = TwoLineSeekBar.this.mThumbOffset;
            TwoLineSeekBar.this.updateThumbOffset();
            TwoLineSeekBar.this.mScroller.startScroll(0, Math.round(initThumbOffset), 0, Math.round(TwoLineSeekBar.this.mThumbOffset - initThumbOffset), 0);
            TwoLineSeekBar.this.mThumbOffset = initThumbOffset;
            TwoLineSeekBar.this.invalidate();
            return true;
        }

        public boolean onDown(MotionEvent e) {
            if (TwoLineSeekBar.this.mDownListener != null) {
                TwoLineSeekBar.this.mDownListener.onSeekDown();
            }
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float newValue;
            TwoLineSeekBar twoLineSeekBar = TwoLineSeekBar.this;
            twoLineSeekBar.mThumbOffset = twoLineSeekBar.mThumbOffset - distanceX;
            if (TwoLineSeekBar.this.mThumbOffset < TwoLineSeekBar.this.mSeekLineStart - TwoLineSeekBar.this.mThumbRadius) {
                TwoLineSeekBar.this.mThumbOffset = TwoLineSeekBar.this.mSeekLineStart - TwoLineSeekBar.this.mThumbRadius;
            }
            if (TwoLineSeekBar.this.mThumbOffset > TwoLineSeekBar.this.mSeekLineEnd - TwoLineSeekBar.this.mThumbRadius) {
                TwoLineSeekBar.this.mThumbOffset = TwoLineSeekBar.this.mSeekLineEnd - TwoLineSeekBar.this.mThumbRadius;
            }
            if (TwoLineSeekBar.this.mThumbOffset < TwoLineSeekBar.this.mNailOffset - TwoLineSeekBar.this.mDefaultAreaRadius) {
                newValue = (TwoLineSeekBar.this.mThumbOffset * ((float) (TwoLineSeekBar.this.mMaxValue - 2))) / (TwoLineSeekBar.this.mSeekLength - (TwoLineSeekBar.this.mDefaultAreaRadius * 2.0f));
            } else if (TwoLineSeekBar.this.mThumbOffset > TwoLineSeekBar.this.mNailOffset + TwoLineSeekBar.this.mDefaultAreaRadius) {
                newValue = 1.0f + (((float) TwoLineSeekBar.this.mDefaultValue) + ((((TwoLineSeekBar.this.mThumbOffset - TwoLineSeekBar.this.mNailOffset) - TwoLineSeekBar.this.mDefaultAreaRadius) * ((float) (TwoLineSeekBar.this.mMaxValue - 2))) / (TwoLineSeekBar.this.mSeekLength - (TwoLineSeekBar.this.mDefaultAreaRadius * 2.0f))));
            } else {
                newValue = (float) TwoLineSeekBar.this.mDefaultValue;
            }
            if (TwoLineSeekBar.this.mDefaultValue == 0 || TwoLineSeekBar.this.mDefaultValue == TwoLineSeekBar.this.mMaxValue) {
                newValue = (TwoLineSeekBar.this.mThumbOffset * ((float) TwoLineSeekBar.this.mMaxValue)) / TwoLineSeekBar.this.mSeekLength;
            }
            if (newValue < 0.0f) {
                newValue = 0.0f;
            }
            if (newValue > ((float) TwoLineSeekBar.this.mMaxValue)) {
                newValue = (float) TwoLineSeekBar.this.mMaxValue;
            }
            TwoLineSeekBar.this.setValueInternal(Math.round(newValue));
            TwoLineSeekBar.this.invalidate();
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (!TwoLineSeekBar.this.mSupportSingleTap) {
                return false;
            }
            int newValue = TwoLineSeekBar.this.mCurrentValue - 1;
            if (e.getX() > TwoLineSeekBar.this.mThumbOffset) {
                newValue = TwoLineSeekBar.this.mCurrentValue + 1;
            }
            if (newValue < 0) {
                newValue = 0;
            }
            if (newValue > TwoLineSeekBar.this.mMaxValue) {
                newValue = TwoLineSeekBar.this.mMaxValue;
            }
            TwoLineSeekBar.this.setValueInternal(Math.round((float) newValue));
            float initThumbOffset = TwoLineSeekBar.this.mThumbOffset;
            TwoLineSeekBar.this.updateThumbOffset();
            TwoLineSeekBar.this.mScroller.startScroll(0, Math.round(initThumbOffset), 0, Math.round(TwoLineSeekBar.this.mThumbOffset - initThumbOffset), 400);
            TwoLineSeekBar.this.mThumbOffset = initThumbOffset;
            TwoLineSeekBar.this.postInvalidate();
            if (TwoLineSeekBar.this.mListener != null) {
                TwoLineSeekBar.this.mListener.onSeekStopped(((float) (TwoLineSeekBar.this.mCurrentValue + TwoLineSeekBar.this.mStartValue)) * TwoLineSeekBar.this.mStep, TwoLineSeekBar.this.mStep);
            }
            return true;
        }
    }

    public TwoLineSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDefaultAreaRadius = 0.0f;
        this.mThumbRadius = 8.0f;
        this.mNailRadius = 5.0f;
        this.mNailStrokeWidth = 0.0f;
        this.mLineWidth = 0.0f;
        this.mMaxValue = 100;
        this.mCurrentValue = 50;
        this.mDefaultValue = 50;
        this.mEnableTouch = true;
        this.mCircleRect = new Rect();
        this.mIsGlobalDrag = true;
        this.mIsTouchCircle = false;
        this.mSupportSingleTap = true;
        this.mThumbRadius = dpToPixel(this.mThumbRadius);
        this.mNailRadius = dpToPixel(this.mNailRadius);
        this.mNailStrokeWidth = context.getResources().getDimension(R.dimen.seekbar_nail_stroke_width);
        this.mLineWidth = context.getResources().getDimension(R.dimen.seekbar_line_width);
        this.mDefaultAreaRadius = (((this.mThumbRadius - this.mNailRadius) - this.mNailStrokeWidth) + this.mThumbRadius) / 2.0f;
        init();
    }

    private void init() {
        this.mScroller = new Scroller(getContext());
        this.mGestureListener = new SeekBarGestureListener();
        this.mGestureDetector = new GestureDetector(getContext(), this.mGestureListener);
        this.mNailPaint = new Paint();
        this.mNailPaint.setAntiAlias(true);
        this.mNailPaint.setColor(Color.parseColor("#ffd600"));
        this.mNailPaint.setStrokeWidth(this.mNailStrokeWidth);
        this.mNailPaint.setStyle(Style.STROKE);
        this.mThumbPaint = new Paint();
        this.mThumbPaint.setAntiAlias(true);
        this.mThumbPaint.setColor(Color.parseColor("#ffffff"));
        this.mThumbPaint.setStyle(Style.FILL);
        this.mLinePaint1 = new Paint();
        this.mLinePaint1.setAntiAlias(true);
        this.mLinePaint1.setColor(Color.parseColor("#ffffff"));
        this.mLinePaint1.setAlpha(200);
        this.mLinePaint2 = new Paint();
        this.mLinePaint2.setAntiAlias(true);
        this.mLinePaint2.setColor(Color.parseColor("#ffffff"));
        this.mLinePaint2.setAlpha(200);
        this.mHighLightLinePaint = new Paint();
        this.mHighLightLinePaint.setAntiAlias(true);
        this.mHighLightLinePaint.setColor(Color.parseColor("#ffd600"));
        this.mHighLightLinePaint.setAlpha(200);
        this.mSupportSingleTap = true;
    }

    public float dpToPixel(float dp) {
        return getResources().getDisplayMetrics().density * dp;
    }

    public void setSingleTapSupport(boolean support) {
        this.mSupportSingleTap = support;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), Math.round(this.mThumbRadius * 2.0f) + (getPaddingTop() + getPaddingBottom()));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mSeekLength == 0.0f) {
            int width = getWidth();
            this.mSeekLength = ((float) ((width - getPaddingLeft()) - getPaddingRight())) - (this.mThumbRadius * 2.0f);
            this.mSeekLineStart = ((float) getPaddingLeft()) + this.mThumbRadius;
            this.mSeekLineEnd = ((float) (width - getPaddingRight())) - this.mThumbRadius;
            int currValue = Math.max(0, this.mCurrentValue);
            this.mNailOffset = (this.mSeekLength * ((float) this.mDefaultValue)) / ((float) this.mMaxValue);
            if (this.mDefaultValue == 0 || this.mDefaultValue == this.mMaxValue) {
                this.mThumbOffset = (this.mSeekLength * ((float) currValue)) / ((float) this.mMaxValue);
            } else {
                float defaultAreaLength = this.mDefaultAreaRadius * 2.0f;
                if (currValue < this.mDefaultValue) {
                    this.mThumbOffset = ((this.mSeekLength - defaultAreaLength) * ((float) currValue)) / ((float) this.mMaxValue);
                } else if (currValue > this.mDefaultValue) {
                    this.mThumbOffset = (((this.mSeekLength - defaultAreaLength) * ((float) currValue)) / ((float) this.mMaxValue)) + (this.mDefaultAreaRadius * 2.0f);
                } else {
                    this.mThumbOffset = this.mNailOffset;
                }
            }
        }
        float top = ((float) (getMeasuredHeight() / 2)) - (this.mLineWidth / 2.0f);
        float bottom = top + this.mLineWidth;
        float right1 = ((this.mSeekLineStart + this.mNailOffset) + (this.mNailStrokeWidth / 2.0f)) - this.mNailRadius;
        if (right1 > this.mSeekLineStart) {
            canvas.drawRect(this.mSeekLineStart, top, right1, bottom, this.mLinePaint1);
        }
        float left2 = right1 + (this.mNailRadius * 2.0f);
        if (this.mSeekLineEnd > left2) {
            canvas.drawRect(left2, top, this.mSeekLineEnd, bottom, this.mLinePaint2);
        }
        float nailX = this.mSeekLineStart + this.mNailOffset;
        canvas.drawCircle(nailX, (float) (getMeasuredHeight() / 2), this.mNailRadius, this.mNailPaint);
        float thumbX = this.mSeekLineStart + this.mThumbOffset;
        float thumbY = (float) (getMeasuredHeight() / 2);
        float highLightLeft = thumbX + this.mThumbRadius;
        float highLightRight = nailX - this.mNailRadius;
        if (thumbX > nailX) {
            highLightLeft = nailX + this.mNailRadius;
            highLightRight = thumbX - this.mThumbRadius;
        }
        canvas.drawRect(highLightLeft, top, highLightRight, bottom, this.mHighLightLinePaint);
        canvas.drawCircle(thumbX, thumbY, this.mThumbRadius, this.mThumbPaint);
        this.mCircleRect.top = (int) (thumbY - this.mThumbRadius);
        this.mCircleRect.left = (int) (thumbX - this.mThumbRadius);
        this.mCircleRect.right = (int) (this.mThumbRadius + thumbX);
        this.mCircleRect.bottom = (int) (this.mThumbRadius + thumbY);
        if (this.mScroller.computeScrollOffset()) {
            this.mThumbOffset = (float) this.mScroller.getCurrY();
            invalidate();
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0 && !this.mIsGlobalDrag) {
            this.mIsTouchCircle = this.mCircleRect.contains((int) event.getX(), (int) event.getY());
        }
        if ((!this.mIsGlobalDrag && !this.mIsTouchCircle) || !this.mEnableTouch || this.mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if (1 != event.getAction() && 3 != event.getAction()) {
            return false;
        }
        this.mIsTouchCircle = false;
        this.mGestureListener.onUp(event);
        if (this.mListener == null) {
            return true;
        }
        this.mListener.onSeekStopped(((float) (this.mCurrentValue + this.mStartValue)) * this.mStep, this.mStep);
        return true;
    }

    public void setLineColor(String color) {
        this.mHighLightLinePaint.setColor(Color.parseColor(color));
        this.mNailPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setBaseLineColor(String color) {
        this.mLinePaint1.setColor(Color.parseColor(color));
        this.mLinePaint2.setColor(Color.parseColor(color));
    }

    public void setThumbColor(String color) {
        this.mThumbPaint.setColor(Color.parseColor(color));
    }

    public void setEnabled(boolean enabled) {
        if (enabled != isEnabled()) {
            super.setEnabled(enabled);
            this.mEnableTouch = enabled;
            if (this.mSavedColors == null) {
                this.mSavedColors = new TreeMap();
            }
            if (enabled) {
                this.mNailPaint.setColor(Integer.valueOf(((Integer) this.mSavedColors.get("mNailPaint")).intValue()).intValue());
                this.mThumbPaint.setColor(Integer.valueOf(((Integer) this.mSavedColors.get("mThumbPaint")).intValue()).intValue());
                this.mLinePaint1.setColor(Integer.valueOf(((Integer) this.mSavedColors.get("mLinePaint1")).intValue()).intValue());
                this.mLinePaint2.setColor(Integer.valueOf(((Integer) this.mSavedColors.get("mLinePaint2")).intValue()).intValue());
                this.mHighLightLinePaint.setColor(Integer.valueOf(((Integer) this.mSavedColors.get("mHighLightLinePaint")).intValue()).intValue());
                return;
            }
            this.mSavedColors.put("mNailPaint", Integer.valueOf(this.mNailPaint.getColor()));
            this.mSavedColors.put("mThumbPaint", Integer.valueOf(this.mThumbPaint.getColor()));
            this.mSavedColors.put("mLinePaint1", Integer.valueOf(this.mLinePaint1.getColor()));
            this.mSavedColors.put("mLinePaint2", Integer.valueOf(this.mLinePaint2.getColor()));
            this.mSavedColors.put("mHighLightLinePaint", Integer.valueOf(this.mHighLightLinePaint.getColor()));
            this.mNailPaint.setColor(Color.parseColor("#505050"));
            this.mThumbPaint.setColor(Color.parseColor("#505050"));
            this.mLinePaint1.setColor(Color.parseColor("#505050"));
            this.mLinePaint2.setColor(Color.parseColor("#505050"));
            this.mHighLightLinePaint.setColor(Color.parseColor("#505050"));
        }
    }

    public void setThumbSize(float size) {
        this.mThumbRadius = size;
    }

    public void setSeekLength(int startValue, int endValue, int circleValue, float step) {
        this.mDefaultValue = Math.round(((float) (circleValue - startValue)) / step);
        this.mMaxValue = Math.round(((float) (endValue - startValue)) / step);
        this.mStartValue = Math.round(((float) startValue) / step);
        this.mStep = step;
    }

    public void setDefaultValue(float value) {
        this.mCurrentValue = Math.round(value / this.mStep) - this.mStartValue;
        if (this.mDefaultListener != null) {
            this.mDefaultListener.onSeekDefaulted(value);
        }
        updateThumbOffset();
        invalidate();
    }

    public float getValue() {
        return ((float) (this.mCurrentValue + this.mStartValue)) * this.mStep;
    }

    public void setValue(float value) {
        int newValue = Math.round(value / this.mStep) - this.mStartValue;
        if (newValue != this.mCurrentValue) {
            this.mCurrentValue = newValue;
            if (this.mListener != null) {
                this.mListener.onSeekChanged(this.mStep * value, this.mStep);
            }
            updateThumbOffset();
            postInvalidate();
        }
    }

    public void setOnSeekChangeListener(OnSeekChangeListener listener) {
        this.mListener = listener;
    }

    public OnSeekChangeListener getOnSeekChangeListener() {
        return this.mListener;
    }

    public void setOnDefaultListener(OnSeekDefaultListener listener) {
        this.mDefaultListener = listener;
    }

    public void setOnSeekDownListener(OnSeekDownListener listener) {
        this.mDownListener = listener;
    }

    private void setValueInternal(int value) {
        if (this.mCurrentValue != value) {
            this.mCurrentValue = value;
            if (this.mListener != null) {
                this.mListener.onSeekChanged(((float) (this.mStartValue + value)) * this.mStep, this.mStep);
            }
        }
    }

    private void updateThumbOffset() {
        if (this.mDefaultValue != 0 && this.mDefaultValue != this.mMaxValue) {
            float defaultAreaLength = this.mDefaultAreaRadius * 2.0f;
            if (this.mCurrentValue <= 0) {
                this.mThumbOffset = 0.0f;
            } else if (this.mCurrentValue == this.mMaxValue) {
                this.mThumbOffset = this.mSeekLineEnd - this.mSeekLineStart;
            } else if (this.mCurrentValue < this.mDefaultValue) {
                this.mThumbOffset = ((this.mSeekLength - defaultAreaLength) * ((float) this.mCurrentValue)) / ((float) this.mMaxValue);
            } else if (this.mCurrentValue > this.mDefaultValue) {
                this.mThumbOffset = (((this.mSeekLength - defaultAreaLength) * ((float) this.mCurrentValue)) / ((float) this.mMaxValue)) + defaultAreaLength;
            } else {
                this.mThumbOffset = this.mNailOffset;
            }
        } else if (this.mCurrentValue <= 0) {
            this.mThumbOffset = 0.0f;
        } else if (this.mCurrentValue == this.mMaxValue) {
            this.mThumbOffset = this.mSeekLineEnd - this.mSeekLineStart;
        } else if (this.mCurrentValue == this.mDefaultValue) {
            this.mThumbOffset = this.mNailOffset;
        } else {
            this.mThumbOffset = (((float) this.mCurrentValue) * this.mSeekLength) / ((float) this.mMaxValue);
        }
    }

    public void reset() {
        this.mSeekLength = 0.0f;
        this.mSeekLineStart = 0.0f;
        this.mSeekLineEnd = 0.0f;
        this.mNailOffset = 0.0f;
        this.mThumbOffset = 0.0f;
        this.mMaxValue = 0;
        this.mCurrentValue = Integer.MAX_VALUE;
        this.mDefaultValue = 0;
        this.mStartValue = 0;
        this.mStep = 0.0f;
        this.mScroller.abortAnimation();
    }

    public void setIsGlobalDrag(boolean mIsGlobalDrag) {
        this.mIsGlobalDrag = mIsGlobalDrag;
    }
}
