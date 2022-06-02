package me.redstoyn33.mazegame;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import me.redstoyn33.mazegame.floors.FloorReaction;

public class MainActivity extends AppCompatActivity {
    TableRow[][] wallsVertical;
    TableRow[][] wallsHorisontal;
    TableRow[][] floors;
    FrameLayout root;
    TextInputEditText input;
    InputMethodManager imm;
    FrameLayout pl;
    public static Game game;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        root = findViewById(R.id.root);
        floors = new TableRow[][]{
                {findViewById(R.id.f00), findViewById(R.id.f10), findViewById(R.id.f20)},
                {findViewById(R.id.f01), findViewById(R.id.f11), findViewById(R.id.f21)},
                {findViewById(R.id.f02), findViewById(R.id.f12), findViewById(R.id.f22)}};
        wallsHorisontal = new TableRow[][]{
                {findViewById(R.id.wh00), findViewById(R.id.wh10), findViewById(R.id.wh20)},
                {findViewById(R.id.wh01), findViewById(R.id.wh11), findViewById(R.id.wh21)},
                {findViewById(R.id.wh02), findViewById(R.id.wh12), findViewById(R.id.wh22)},
                {findViewById(R.id.wh03), findViewById(R.id.wh13), findViewById(R.id.wh23)}};
        wallsVertical = new TableRow[][]{
                {findViewById(R.id.wv00), findViewById(R.id.wv10), findViewById(R.id.wv20), findViewById(R.id.wv30)},
                {findViewById(R.id.wv01), findViewById(R.id.wv11), findViewById(R.id.wv21), findViewById(R.id.wv31)},
                {findViewById(R.id.wv02), findViewById(R.id.wv12), findViewById(R.id.wv22), findViewById(R.id.wv32)}};
        game = new Game(this);
        input = findViewById(R.id.input);
        pl = findViewById(R.id.player);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        findViewById(R.id.button1).setOnClickListener(v -> {
            game.start(new Map(Objects.requireNonNull(input.getText()).toString()));
            if (getCurrentFocus()!=null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        });
        ((ListView)findViewById(R.id.levels)).setOnItemClickListener((parent, view, position, id) -> {
            game.start(new Map(getResources().getStringArray(R.array.levels)[position]));
        });
        root.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float x = event.getX() - v.getWidth() / 2;
                float y = event.getY() - v.getHeight() / 2;
                if (y > x & y > -x) game.movePlayer(Direction.DOWN);
                else if (y > x & y < -x) game.movePlayer(Direction.LEFT);
                else if (y < x & y > -x) game.movePlayer(Direction.RIGHT);
                else if (y < x & y < -x) game.movePlayer(Direction.UP);
            }
            return true;
        });
        game.start(new Map("4=4\n" +
                "+--|\n" +
                "|..|\n" +
                "|..|\n" +
                "---.\n" +
                "s=1=1\n" +
                "e=2=2"));
    }

    public void displayMaze() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 4; y++) {
                wallsHorisontal[3 - y][x].setVisibility((game.map.getWall(game.player.cords.x - 1 + x, game.player.cords.y - 1 + y).horisontal) ? View.VISIBLE : View.INVISIBLE);
            }
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                wallsVertical[2 - y][x].setVisibility((game.map.getWall(game.player.cords.x - 1 + x, game.player.cords.y - 1 + y).vertical) ? View.VISIBLE : View.INVISIBLE);
            }
        }
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                FloorReaction f = game.map.getWall(game.player.cords.x - 1 + x, game.player.cords.y - 1 + y).floor;
                if (f == null) {
                    floors[2 - y][x].setBackgroundColor((game.player.cords.x + game.player.cords.y + x + y) % 2 == 0 ? 0x11003b99 : 0x1100994a);
                } else {
                    floors[2 - y][x].setBackgroundColor(f.getColor());
                }
            }
        }
        pl.setBackgroundColor(game.player.win?0xFFEDCA58:0xFF402F2F);
    }
}