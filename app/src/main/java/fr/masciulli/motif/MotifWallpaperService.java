package fr.masciulli.motif;

import android.graphics.Canvas;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class MotifWallpaperService extends WallpaperService {
    private int[] teal = {0xFF009688, 0xFF26A69A, 0xFF4DB6AC, 0xFF80CBC4};
    private MotifDrawer drawer;

    @Override
    public Engine onCreateEngine() {
        drawer = new MotifDrawer();
        return new MotifEngine();
    }

    private class MotifEngine extends Engine {
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Canvas canvas = holder.lockCanvas();
            drawer.setColors(teal);
            drawer.setBoxSize(200, canvas.getWidth(), canvas.getHeight());
            drawer.setBoxPadding(30, canvas.getWidth(), canvas.getHeight());
            drawer.drawTriangles(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
