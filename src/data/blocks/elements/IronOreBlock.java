package data.blocks.elements;

import data.blocks.AbstractSolidBlock;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class IronOreBlock extends AbstractSolidBlock {
    //static PhongMaterial mat = new PhongMaterial(Color.WHITE, new Image("res/GIOVANNI.JPG"), null, null, null);
    private static Image texture = new Image("textures/rock_iron.png", 1024, 1024, true, false);
    private static PhongMaterial mat = new PhongMaterial(Color.WHITE, texture, null, null, null);

    public IronOreBlock() {
        super();
        this.setMaterial(mat);
    }

    @Override
    public String getName() {
        return "Iron Ore";
    }

    public static Image getTexture() {
        return texture;
    }
}
