package data.blocks;

import data.utils.Coord;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;

public abstract class AbstractBlock extends Box implements Block {
    protected int hp;
    protected int contenuto;
    protected boolean falls_with_gravity;
    protected boolean fall_through;

    //private boolean texture_set = false;
    //private static Image texture;
    //private static PhongMaterial mat;
    //static PhongMaterial mat = new PhongMaterial(Color.GREEN);

    public AbstractBlock(boolean falls_with_gravity, boolean fall_through) {
        super(1, 1, 1);
        this.hp = 100;

        this.fall_through = false;
        this.falls_with_gravity = falls_with_gravity;

        this.setDrawMode(DrawMode.FILL);
    }

    public static Image getTexture() {
        return null;
    }


    public boolean falls_with_gravity() {
        return falls_with_gravity;
    }

    public void move(Coord p) {
        this.setTranslateX(p.x + 0.5);
        this.setTranslateY(p.y + 0.5);
        this.setTranslateZ(p.z + 0.5);
    }

    @Override
    public Coord getCoords() {
        return new Coord((int) this.getTranslateX(), (int) this.getTranslateY(), (int) this.getTranslateZ());
    }

    public boolean fall_through() {
        return fall_through;
    }

    public boolean subHp(int hit) {
        this.hp -= hit;
        return this.hp <= 0;
    }

    @Override
    public Node getDisplay() {
        return this;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hp=" + hp +
                ", contenuto=" + contenuto +
                ", X=" + getTranslateX() +
                ", Y=" + getTranslateY() +
                ", Z=" + getTranslateZ() +
                '}';
    }
}
