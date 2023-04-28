package data.blocks;

import data.utils.Coord;
import javafx.scene.Node;
import javafx.scene.image.Image;

public interface Block {
    public boolean falls_with_gravity();

    public boolean fall_through();

    public boolean subHp(int hit);

    public void move(Coord p);

    public String getName();

    public Coord getCoords();

    public Node getDisplay();
}
