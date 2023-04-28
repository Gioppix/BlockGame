package data.blocks.elements;

import data.blocks.AbstractSolidBlock;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class GlassBlock extends AbstractSolidBlock {
    private static Image texture = new Image("textures/glass.png", 1024, 1024, true, false);
    private static PhongMaterial mat = new PhongMaterial(Color.WHITE, texture, null, null, null);


    public GlassBlock() {
        super();
        this.setMaterial(mat);
    }

    public static Image getTexture() {
        return texture;
    }

    @Override
    public String getName() {
        return "Glass";
    }
}
