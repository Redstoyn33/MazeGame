package me.redstoyn33.mazegame;

import android.widget.Toast;

import java.io.Reader;
import java.util.HashMap;
import java.util.Vector;

import me.redstoyn33.mazegame.floors.EndFloor;
import me.redstoyn33.mazegame.floors.FloorReaction;

public class Map {
    public WallCluster[][] walls;
    public int xSize;
    public int ySize;
    public XYCords spawn;
    public boolean err = false;
    private WallCluster empty = new WallCluster(false, false);

    public Map(String m) {
        try {
            if (m.length()==0) {
                err = true;
                return;
            }
            String[] lines = m.split("\n");
            String[] size = lines[0].split("=");
            xSize = Integer.parseInt(size[0]);
            ySize = Integer.parseInt(size[1]);
            walls = new WallCluster[xSize][ySize];
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    switch (lines[y + 1].charAt(x)) {
                        case '+':
                            walls[x][y] = new WallCluster(true, true);
                            break;
                        case '-':
                            walls[x][y] = new WallCluster(false, true);
                            break;
                        case '|':
                            walls[x][y] = new WallCluster(true, false);
                            break;
                        case '.':
                            walls[x][y] = new WallCluster(false, false);
                    }
                }
            }
            for (int i = ySize + 1; i < lines.length; i++) {
                String[] params = lines[i].split("=");
                switch (params[0]) {
                    case "s":
                        spawn = new XYCords(Integer.parseInt(params[1]), Integer.parseInt(params[2]));
                        break;
                    case "e":
                        walls[Integer.parseInt(params[1])][Integer.parseInt(params[2])].setFloor(new EndFloor());
                        break;
                }
            }
            if (spawn == null) {
                spawn = new XYCords(xSize / 2, ySize / 2);
            }
        } catch (Exception e) {
            err = true;
        }
    }

    public WallCluster getWall(int x, int y) {
        if (x < 0 || y < 0 || x >= xSize || y >= ySize) return empty;
        return walls[x][y];
    }

    public WallCluster getWall(XYCords cords) {
        if (cords.x < 0 || cords.y < 0 || cords.x >= xSize || cords.y >= ySize) return empty;
        return walls[cords.x][cords.y];
    }

    public boolean move(XYCords from, XYCords to) {
        FloorReaction f = getWall(from).floor;
        FloorReaction t = getWall(to).floor;
        if (f != null) {
            if (!f.OnMoveFrom()) return false;
        }
        if (t != null) {
            if (!t.OnMoveOn()) return false;
        }
        return true;
    }

    public static class WallCluster {
        public boolean vertical;
        public boolean horisontal;
        public FloorReaction floor;

        public WallCluster(boolean v, boolean h) {
            vertical = v;
            horisontal = h;
        }

        public void setFloor(FloorReaction floor) {
            this.floor = floor;
        }
    }
}