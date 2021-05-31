package com.cambe.cartooneffecteditor.filters;

import android.graphics.PointF;

public class FaceFeatureMarks {
    PointF center;
    float radius = (this.strength * 1.2f);
    float strength;

    public FaceFeatureMarks(float x, float y, float str) {
        this.center = new PointF(x, y);
        this.strength = (str / 3.0f) + 0.1f;
        System.out.println();
    }

    public PointF getCenter() {
        return this.center;
    }

    public PointF getAroundThere() {
        return new PointF(this.center.x + 0.01f, this.center.y + 0.01f);
    }

    public void setCenter(PointF center) {
        this.center = center;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getStrength() {
        return this.strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }
}
