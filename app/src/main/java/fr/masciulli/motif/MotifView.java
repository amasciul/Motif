package fr.masciulli.motif;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private final Paint darkPaint = new Paint();
    private final Paint lightPaint = new Paint();

    List<List<Point>> points = new ArrayList<>();

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

    private void initPaints() {
        lightPaint.setColor(getContext().getResources().getColor(R.color.purple));
        darkPaint.setColor(getContext().getResources().getColor(R.color.purple_dark));
    }


    public MotifView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaints();
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
//        drawPoints(canvas);
//        drawLines(canvas);
//        drawGrid(canvas);
        drawTriangles(canvas);
    }

    private void drawGrid(Canvas canvas) {
        int x = BOX_SIZE_PX;
        while (x < getWidth()) {
            canvas.drawLine(x, 0, x, getHeight(), darkPaint);
            x += BOX_SIZE_PX;
        }

        int y = BOX_SIZE_PX;
        while (y < getHeight()) {
            canvas.drawLine(0, y, getWidth(), y, darkPaint);
            y += BOX_SIZE_PX;
        }
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.get(0).size(); j++) {
                Point point = points.get(i).get(j);
                if (i > 0) {
                    Point left = points.get(i - 1).get(j);
                    canvas.drawLine(left.x, left.y, point.x, point.y, darkPaint);
                    if (j > 0) {
                        Point leftTop = points.get(i - 1).get(j - 1);
                        canvas.drawLine(leftTop.x, leftTop.y, point.x, point.y, darkPaint);
                    }
                    if (j < points.get(0).size() - 1) {
                        Point leftBottom = points.get(i - 1).get(j + 1);
                        canvas.drawLine(leftBottom.x, leftBottom.y, point.x, point.y, darkPaint);
                    }
                }
                if (j < points.get(0).size() - 1) {
                    Point bottom = points.get(i).get(j + 1);
                    canvas.drawLine(bottom.x, bottom.y, point.x, point.y, darkPaint);
                }
            }
        }
    }

    private void drawTriangles(Canvas canvas) {
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.get(0).size(); j++) {
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
                    canvas.drawPath(path, darkPaint);
                }
                if (i > 0 && j < points.get(0).size() - 1) {
                    Point left = points.get(i - 1).get(j);
                    Point bottomLeft = points.get(i - 1).get(j + 1);
                    Path path = new Path();
                    path.setFillType(Path.FillType.EVEN_ODD);
                    path.moveTo(point.x, point.y);
                    path.lineTo(left.x, left.y);
                    path.lineTo(bottomLeft.x, bottomLeft.y);
                    path.close();
                    canvas.drawPath(path, lightPaint);
                }
            }
        }
    }

    private void drawPoints(Canvas canvas) {
        for (List<Point> column : points) {
            for (Point point : column) {
                canvas.drawCircle(point.x, point.y, 4, darkPaint);
            }
        }
    }
}
