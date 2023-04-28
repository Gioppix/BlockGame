package data.blocks.elements;

import data.blocks.AbstractSolidBlock;
import data.blocks.Block;
import data.blocks.Smeltable;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class DirtBlock extends AbstractSolidBlock implements Smeltable {
    private static Image texture = new Image("textures/dirt.png", 1024, 1024, true, false);
    private static PhongMaterial mat = new PhongMaterial(Color.WHITE, texture, null, null, null);
    //static PhongMaterial mat = new PhongMaterial(Color.GREEN);

    public DirtBlock() {

        super();
        this.setMaterial(mat);
    }

    public static Image getTexture() {
        //System.out.println("DirtBlock gettexture");
        //texture = new Image("res/DirtBlock.png", 1024, 1024, true, false);
        return texture;
    }

    public Block smelt() {
        return new IronOreBlock();
    }

    @Override
    public String getName() {
        return "Dirt";
    }
}
