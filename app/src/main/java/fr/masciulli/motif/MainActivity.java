package fr.masciulli.motif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private MotifView motifView;

    private int[] red = {0xFFF44336, 0xFFEF5350, 0xFFE57373, 0xFFEF9A9A};
    private int[] teal = {0xFF009688, 0xFF26A69A, 0xFF4DB6AC, 0xFF80CBC4};
    private int[] blue = {0xFF3F51B5, 0xFF5C6BC0, 0xFF7986CB, 0xFF9FA8DA};
    private int[] brown = {0xFF795548, 0xFF8D6E63, 0xFFA1887F, 0xFFBCAAA4};
    private int[][] colors = {red, teal, blue, brown};
    private int colorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        motifView = (MotifView) findViewById(R.id.motif);
        changeColor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_color) {
            changeColor();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor() {
        motifView.setColors(colors[colorIndex++ % colors.length]);
    }
}
