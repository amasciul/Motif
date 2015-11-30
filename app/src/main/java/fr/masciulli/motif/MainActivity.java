package fr.masciulli.motif;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MotifView motifView;

    private int[] red = {0xFFF44336, 0xFFEF5350, 0xFFE57373, 0xFFEF9A9A};
    private int[] teal = {0xFF009688, 0xFF26A69A, 0xFF4DB6AC, 0xFF80CBC4};
    private int[] blue = {0xFF3F51B5, 0xFF5C6BC0, 0xFF7986CB, 0xFF9FA8DA};
    private int[] brown = {0xFF795548, 0xFF8D6E63, 0xFFA1887F, 0xFFBCAAA4};
    private int[] boxSizes = {200, 300, 400};
    private int[] boxPaddings = {25, 50, 75};

    private int[][] colors = {red, teal, blue, brown};
    private int colorIndex = 0;
    private int boxSizeIndex = 0;
    private int boxPaddingIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        motifView = (MotifView) findViewById(R.id.motif);
        changeColor();
        changeBoxSize();
        changeBoxPadding();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_color:
                changeColor();
                return true;
            case R.id.action_change_boxsize:
                changeBoxSize();
                return true;
            case R.id.action_change_boxpadding:
                changeBoxPadding();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor() {
        motifView.setColors(colors[colorIndex++ % colors.length]);
    }

    private void changeBoxSize() {
        int newBoxSize = boxSizes[boxSizeIndex++ % boxSizes.length];
        motifView.setBoxSize(newBoxSize);
        Toast.makeText(this, getString(R.string.box_size_changed, newBoxSize), Toast.LENGTH_SHORT).show();
    }

    private void changeBoxPadding() {
        int newBoxPadding = boxPaddings[boxPaddingIndex++ % boxPaddings.length];
        motifView.setBoxPadding(newBoxPadding);
        Toast.makeText(this, getString(R.string.box_padding_changed, newBoxPadding), Toast.LENGTH_SHORT).show();
    }
}
