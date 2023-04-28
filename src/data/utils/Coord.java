package data.utils;

import javafx.geometry.Point3D;

public class Coord {
    public int x;
    public int y;
    public int z;

    public Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coord(Point3D p) {
        this.x = (int) p.getX();
        this.y = (int) p.getY();
        this.z = (int) p.getZ();
    }

    public void add(Coord c) {
        this.x += c.x;
        this.y += c.y;
        this.z += c.z;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
