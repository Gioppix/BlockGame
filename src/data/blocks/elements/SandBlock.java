package data.blocks.elements;

import data.blocks.AbstractFallingBlock;
import data.blocks.Block;
import data.blocks.Smeltable;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class SandBlock extends AbstractFallingBlock implements Smeltable {
    private static Image texture = new Image("textures/sand.png", 1024, 1024, true, false);
    private static PhongMaterial mat = new PhongMaterial(Color.WHITE, texture, null, null, null);

    public SandBlock() {
        super();
        this.setMaterial(mat);
    }

    public static Image getTexture() {
        return texture;
    }

    public Block smelt() {
        return new GlassBlock();
    }

    @Override
    public String getName() {
        return "Sand";
    }
}
