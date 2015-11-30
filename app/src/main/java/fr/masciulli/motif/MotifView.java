package fr.masciulli.motif;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotifView extends View {
    private static final int BOX_SIZE_PX = 150;

    private final Paint blackPaint = new Paint();
    private final Paint paint1 = new Paint();
    private final Paint paint2 = new Paint();
    private final Paint paint3 = new Paint();
    private final Paint paint4 = new Paint();

    List<List<Point>> points = new ArrayList<>();
    private int[] colors = {0xFF009688, 0xFF26A69A, 0xFF4DB6AC, 0xFF80CBC4};

    public MotifView(Context context) {
        super(context);
        initPaints();
    }

    public MotifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public MotifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    public MotifView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaints();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        initPaints();
        invalidate();
    }

    private void initPaints() {
        paint1.setColor(colors[0]);
        paint2.setColor(colors[1]);
        paint3.setColor(colors[2]);
        paint4.setColor(colors[3]);
        paint1.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint3.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint4.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        generatePoints(w, h);
    }

    private void generatePoints(int width, int height) {
        points.clear();
        Random random = new Random();

        int x0 = -BOX_SIZE_PX;
        while (x0 < width + BOX_SIZE_PX) {
            List<Point> column = new ArrayList<>();
            int y0 = -BOX_SIZE_PX;
            while (y0 < height + BOX_SIZE_PX) {
                Point point = new Point(x0 + random.nextInt(BOX_SIZE_PX), y0 + random.nextInt(BOX_SIZE_PX));
                column.add(point);
                y0 += BOX_SIZE_PX;
            }
            points.add(column);
            x0 += BOX_SIZE_PX;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangles(canvas);
//        drawPoints(canvas);
//        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        int x = BOX_SIZE_PX;
        while (x < getWidth()) {
            canvas.drawLine(x, 0, x, getHeight(), blackPaint);
            x += BOX_SIZE_PX;
        }

        int y = BOX_SIZE_PX;
        while (y < getHeight()) {
            canvas.drawLine(0, y, getWidth(), y, blackPaint);
            y += BOX_SIZE_PX;
        }
    }

    private void drawTriangles(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = i % 2; j < points.get(0).size(); j += 2) {
                Point point = points.get(i).get(j);
                if (i > 0 && j > 0) {
                    Point left = points.get(i - 1).get(j);
                    Point top = points.get(i).get(j - 1);
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point.x, point.y);
                    path.lineTo(left.x, left.y);
                    path.lineTo(top.x, top.y);
                    path.close();
                    canvas.drawPath(path, paint1);
                }
                if (i > 0 && j < points.get(0).size() - 1) {
                    Point left = points.get(i - 1).get(j);
                    Point bottom = points.get(i).get(j + 1);
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point.x, point.y);
                    path.lineTo(left.x, left.y);
                    path.lineTo(bottom.x, bottom.y);
                    path.close();
                    canvas.drawPath(path, paint2);
                }
                if (i < points.size() - 1 && j > 0) {
                    Point top = points.get(i).get(j - 1);
                    Point right = points.get(i + 1).get(j);
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point.x, point.y);
                    path.lineTo(top.x, top.y);
                    path.lineTo(right.x, right.y);
                    path.close();
                    canvas.drawPath(path, paint3);
                }
                if (i < points.size() - 1 && j < points.get(0).size() - 1) {
                    Point bottom = points.get(i).get(j + 1);
                    Point right = points.get(i + 1).get(j);
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point.x, point.y);
                    path.lineTo(bottom.x, bottom.y);
                    path.lineTo(right.x, right.y);
                    path.close();
                    canvas.drawPath(path, paint4);
                }
            }
        }
    }

    private void drawPoints(Canvas canvas) {
        for (List<Point> column : points) {
            for (Point point : column) {
                canvas.drawCircle(point.x, point.y, 4, blackPaint);
            }
        }
    }
}
