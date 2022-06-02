package me.redstoyn33.mazegame;

import android.widget.Toast;

import androidx.annotation.NonNull;

public class Game {
    private final MainActivity app;
    //
    Player player;
    Map map;


    public Game(MainActivity app){
        this.app = app;
    }
    public void start(Map start){
        if (start.err) {
            map = new Map("4=4\n" +
                    "+--|\n" +
                    "|..|\n" +
                    "|..|\n" +
                    "---.\n" +
                    "s=1=1\n" +
                    "e=2=2");
        }
        else map = start;
        player = new Player(new XYCords(map.spawn));
        app.displayMaze();
    }
    public void movePlayer(Direction d){
        XYCords to;
        switch (d){
            case DOWN:
                to = new XYCords(player.cords.x, player.cords.y+1);
                if (!player.win){
                if (map.getWall(to).horisontal) return;
                if (!map.move(player.cords,to)) return;}
                player.cords.y++;
                app.displayMaze();
                break;
            case UP:
                to = new XYCords(player.cords.x, player.cords.y-1);
                if (!player.win){
                if (map.getWall(player.cords).horisontal) return;
                if (!map.move(player.cords,to)) return;}
                player.cords.y--;
                app.displayMaze();
                break;
            case RIGHT:
                to = new XYCords(player.cords.x+1, player.cords.y);
                if (!player.win){
                if (map.getWall(to).vertical) return;
                if (!map.move(player.cords,to)) return;}
                player.cords.x++;
                app.displayMaze();
                break;
            case LEFT:
                to = new XYCords(player.cords.x-1, player.cords.y);
                if (!player.win){
                if (map.getWall(player.cords).vertical) return;
                if (!map.move(player.cords,to)) return;}
                player.cords.x--;
                app.displayMaze();
                break;
        }
    }
    public void end(){
        player.win = true;
    }
}
