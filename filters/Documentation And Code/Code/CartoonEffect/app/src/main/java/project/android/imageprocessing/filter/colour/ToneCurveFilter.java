package project.android.imageprocessing.filter.colour;

import android.graphics.Point;
import android.opengl.GLES20;

import java.lang.reflect.Array;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import project.android.imageprocessing.filter.MultiInputFilter;
import project.android.imageprocessing.input.GLTextureOutputRenderer;

public class ToneCurveFilter extends MultiInputFilter {
    private int[] bluePart = new int[256];
    private int[] greenPart = new int[256];
    private int[] redPart = new int[256];
    private int[] splineTexture;

    public ToneCurveFilter(Point[] red, Point[] green, Point[] blue, Point[] rgbComposite) {
        super(2);
        float[] redCurve = getPreparedSpline(red);
        float[] blueCurve = getPreparedSpline(blue);
        float[] greenCurve = getPreparedSpline(green);
        float[] rgbCompositeCurve = getPreparedSpline(rgbComposite);
        for (int i = 0; i < 256; i++) {
            this.redPart[i] = (int) Math.min(Math.max((((float) i) + redCurve[i]) + rgbCompositeCurve[i], 0.0f), 255.0f);
            this.greenPart[i] = (int) Math.min(Math.max((((float) i) + greenCurve[i]) + rgbCompositeCurve[i], 0.0f), 255.0f);
            this.bluePart[i] = (int) Math.min(Math.max((((float) i) + blueCurve[i]) + rgbCompositeCurve[i], 0.0f), 255.0f);
        }
    }

    private void createSplineTexture() {
        int[] data = new int[256];
        for (int i = 0; i < 256; i++) {
            data[i] = (((this.redPart[i] & 255) | ((this.greenPart[i] << 8) & 65280)) | ((this.bluePart[i] << 16) & 16711680)) | -16777216;
        }
        this.splineTexture = new int[1];
        GLES20.glGenTextures(1, this.splineTexture, 0);
        GLES20.glBindTexture(3553, this.splineTexture[0]);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9728);
        GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, IntBuffer.wrap(data));
    }

    public void destroy() {
        super.destroy();
        if (this.splineTexture != null && this.splineTexture[0] != 0) {
            GLES20.glDeleteTextures(1, this.splineTexture, 0);
            this.splineTexture = null;
        }
    }

    protected String getFragmentShader() {
        return "precision mediump float;\nuniform sampler2D u_Texture0;\nuniform sampler2D u_Texture1;\nvarying vec2 v_TexCoord;\nconst float halfPixelWidth = 1.0/512.0;void main(){\n   vec4 texColour = texture2D(u_Texture0,v_TexCoord);\n   float rVal;\n   if(texColour.r < halfPixelWidth) {     rVal = texture2D(u_Texture1, vec2(texColour.r + halfPixelWidth, 0.5)).r;\n   } else {\n     rVal = texture2D(u_Texture1, vec2(texColour.r - halfPixelWidth, 0.5)).r;\n   }\n   float gVal;\n   if(texColour.g < halfPixelWidth) {     gVal = texture2D(u_Texture1, vec2(texColour.g + halfPixelWidth, 0.5)).r;\n   } else {\n     gVal = texture2D(u_Texture1, vec2(texColour.g - halfPixelWidth, 0.5)).r;\n   }\n   float bVal;\n   if(texColour.b < halfPixelWidth) {     bVal = texture2D(u_Texture1, vec2(texColour.b + halfPixelWidth, 0.5)).r;\n   } else {\n     bVal = texture2D(u_Texture1, vec2(texColour.b - halfPixelWidth, 0.5)).r;\n   }\n   gl_FragColor = vec4(rVal,gVal,bVal,texColour.a);\n}\n";
    }

    private float[] getPreparedSpline(Point[] points) {
        int i;
        Arrays.sort(points, new Comparator<Point>() {
            public int compare(Point lhs, Point rhs) {
                return lhs.x - rhs.x;
            }
        });
        List<Point> spline = getSplineCurve(points);
        if (((Point) spline.get(0)).x > 0) {
            for (i = ((Point) spline.get(0)).x; i >= 0; i--) {
                spline.add(0, new Point(i, 0));
            }
        }
        if (((Point) spline.get(spline.size() - 1)).x < 255) {
            for (i = ((Point) spline.get(spline.size() - 1)).x; i < 256; i++) {
                spline.add(new Point(i, 255));
            }
        }
        float[] preparedSplinePoints = new float[spline.size()];
        for (i = 0; i < spline.size(); i++) {
            Point newPoint = (Point) spline.get(i);
            Point origPoint = new Point(newPoint.x, newPoint.x);
            float distance = (float) Math.sqrt(Math.pow((double) (origPoint.x - newPoint.x), 2.0d) + Math.pow((double) (origPoint.y - newPoint.y), 2.0d));
            if (origPoint.y > newPoint.y) {
                distance = -distance;
            }
            preparedSplinePoints[i] = distance;
        }
        return preparedSplinePoints;
    }

    private List<Point> getSplineCurve(Point[] points) {
        double[] sdA = secondDerivative(points);
        int n = sdA.length;
        if (n < 1) {
            return null;
        }
        List<Point> output = new ArrayList(n + 1);
        for (int i = 0; i < n - 1; i++) {
            Point cur = points[i];
            Point next = points[i + 1];
            for (int x = cur.x; x < next.x; x++) {
                double t = ((double) (x - cur.x)) / ((double) (next.x - cur.x));
                double a = 1.0d - t;
                double b = t;
                double h = (double) (next.x - cur.x);
                double y = ((((double) cur.y) * a) + (((double) next.y) * b)) + (((h * h) / 6.0d) * (((((a * a) * a) - a) * sdA[i]) + ((((b * b) * b) - b) * sdA[i + 1])));
                if (y > 255.0d) {
                    y = 255.0d;
                } else if (y < 0.0d) {
                    y = 0.0d;
                }
                output.add(new Point(x, (int) y));
            }
        }
        if (output.size() != 255) {
            return output;
        }
        output.add(points[points.length - 1]);
        return output;
    }

    public void newTextureReady(int texture, GLTextureOutputRenderer source, boolean newData) {
        if (this.filterLocations.size() < 2 || !source.equals(this.filterLocations.get(0))) {
            clearRegisteredFilterLocations();
            registerFilterLocation(source, 0);
            registerFilterLocation(this, 1);
        }
        if (this.splineTexture == null || this.splineTexture[0] == 0) {
            createSplineTexture();
        }
        super.newTextureReady(this.splineTexture[0], this, newData);
        super.newTextureReady(texture, source, newData);
    }

    private double[] secondDerivative(Point[] points) {
        int n = points.length;
        if (n <= 1) {
            return null;
        }
        int i;
        double[][] matrix = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, 3});
        double[] result = new double[n];
        matrix[0][1] = 1.0d;
        matrix[0][0] = 0.0d;
        matrix[0][2] = 0.0d;
        for (i = 1; i < n - 1; i++) {
            Point P1 = points[i - 1];
            Point P2 = points[i];
            Point P3 = points[i + 1];
            matrix[i][0] = ((double) (P2.x - P1.x)) / 6.0d;
            matrix[i][1] = ((double) (P3.x - P1.x)) / 3.0d;
            matrix[i][2] = ((double) (P3.x - P2.x)) / 6.0d;
            result[i] = (((double) (P3.y - P2.y)) / ((double) (P3.x - P2.x))) - (((double) (P2.y - P1.y)) / ((double) (P2.x - P1.x)));
        }
        result[0] = 0.0d;
        result[n - 1] = 0.0d;
        matrix[n - 1][1] = 1.0d;
        matrix[n - 1][0] = 0.0d;
        matrix[n - 1][2] = 0.0d;
        for (i = 1; i < n; i++) {
            double k = matrix[i][0] / matrix[i - 1][1];
            double[] dArr = matrix[i];
            dArr[1] = dArr[1] - (matrix[i - 1][2] * k);
            matrix[i][0] = 0.0d;
            result[i] = result[i] - (result[i - 1] * k);
        }
        for (i = n - 2; i >= 0; i--) {
            double k = matrix[i][2] / matrix[i + 1][1];
            double[] dArr = matrix[i];
            dArr[1] = dArr[1] - (matrix[i + 1][0] * k);
            matrix[i][2] = 0.0d;
            result[i] = result[i] - (result[i + 1] * k);
        }
        double[] y2 = new double[n];
        for (i = 0; i < n; i++) {
            y2[i] = result[i] / matrix[i][1];
        }
        return y2;
    }
}
