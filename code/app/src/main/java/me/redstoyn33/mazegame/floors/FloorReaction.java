package me.redstoyn33.mazegame.floors;

public interface FloorReaction {
    default boolean OnMoveOn() {
        return true;
    }

    default boolean OnMoveFrom() {
        return true;
    }
    int getColor();
}
