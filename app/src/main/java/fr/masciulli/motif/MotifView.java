package fr.masciulli.motif;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotifView extends View {
    MotifDrawer drawer = new MotifDrawer();

    public MotifView(Context context) {
        super(context);
    }

    public MotifView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MotifView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColors(int[] colors) {
        drawer.setColors(colors);
        invalidate();
    }

    public void setBoxSize(int boxSizePx) {
        drawer.setBoxSize(boxSizePx, getWidth(), getHeight());
        invalidate();
    }

    public void setBoxPadding(int boxPaddingPx) {
        drawer.setBoxPadding(boxPaddingPx, getWidth(), getHeight());
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawer.generatePoints(w, h);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawer.drawTriangles(canvas);
    }
}
