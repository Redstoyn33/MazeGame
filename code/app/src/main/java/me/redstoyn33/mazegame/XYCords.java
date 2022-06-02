package me.redstoyn33.mazegame;

public class XYCords {
    public int x;
    public int y;

    public XYCords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public XYCords(XYCords cords) {
        this.x = cords.x;
        this.y = cords.y;
    }

    @Override
    public int hashCode() {
        return x+10000*y;
    }
}
