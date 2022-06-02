package me.redstoyn33.mazegame.floors;

import me.redstoyn33.mazegame.MainActivity;

public class EndFloor implements FloorReaction{
    @Override
    public boolean OnMoveOn() {
        MainActivity.game.end();
        return true;
    }

    @Override
    public int getColor() {
        return 0x44630099;
    }
}
