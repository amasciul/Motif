package fr.masciulli.motif;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotifDrawer {
    private final Paint blackPaint = new Paint();
    private final Paint paint1 = new Paint();
    private final Paint paint2 = new Paint();
    private final Paint paint3 = new Paint();
    private final Paint paint4 = new Paint();

    private int boxSizePx = 150;
    private int boxPaddingPx = 30;

    List<List<Point>> points = new ArrayList<>();
    private int[] colors = {0xFF009688, 0xFF26A69A, 0xFF4DB6AC, 0xFF80CBC4};

    public MotifDrawer() {
        initPaints();
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

    public void setColors(int[] colors) {
        this.colors = colors;
        initPaints();
    }

    public void setBoxSize(int boxSizePx, int width, int height) {
        this.boxSizePx = boxSizePx;
        generatePoints(width, height);
    }

    public void setBoxPadding(int boxPaddingPx, int width, int height) {
        if (boxPaddingPx >= boxSizePx / 2) {
            throw new IllegalArgumentException("Box padding can't be greater than half the box size");
        }
        generatePoints(width, height);
        this.boxPaddingPx = boxPaddingPx;
    }

    public void generatePoints(int width, int height) {
        points.clear();
        Random random = new Random();

        int x0 = -boxSizePx;
        while (x0 < width + boxSizePx) {
            List<Point> column = new ArrayList<>();
            int y0 = -boxSizePx;
            while (y0 < height + boxSizePx) {
                int x = x0 + boxPaddingPx + random.nextInt(boxSizePx - boxPaddingPx);
                int y = y0 + boxPaddingPx + random.nextInt(boxSizePx - boxPaddingPx);
                Point point = new Point(x, y);
                column.add(point);
                y0 += boxSizePx;
            }
            points.add(column);
            x0 += boxSizePx;
        }
    }

    public void drawGrid(Canvas canvas, int width, int height) {
        int x = boxSizePx;
        while (x < width) {
            canvas.drawLine(x, 0, x, height, blackPaint);
            x += boxSizePx;
        }

        int y = boxSizePx;
        while (y < height) {
            canvas.drawLine(0, y, width, y, blackPaint);
            y += boxSizePx;
        }
    }

    public void drawTriangles(Canvas canvas) {
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

    public void drawPoints(Canvas canvas) {
        for (List<Point> column : points) {
            for (Point point : column) {
                canvas.drawCircle(point.x, point.y, 4, blackPaint);
            }
        }
    }
}
