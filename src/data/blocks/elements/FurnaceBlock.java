package data.blocks.elements;

import data.blocks.AbstractSolidBlock;
import data.blocks.Block;
import data.blocks.Interactable;
import data.blocks.Smeltable;
import data.utils.Coord;
import data.core.Mappa;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class FurnaceBlock extends AbstractSolidBlock implements Interactable {
    private static Image texture = new Image("textures/furnace.png", 1024, 1024, true, false);
    private static PhongMaterial mat = new PhongMaterial(Color.WHITE, texture, null, null, null);

    //private Smeltable input;
    //private Block output;

    public FurnaceBlock() {
        super();
        this.setMaterial(mat);
    }

    public static Image getTexture() {
        return texture;
    }

    private Block smelt(Smeltable in) {
        return in.smelt();
    }

    public void onclick(Mappa map) {
        Coord cordd = this.getCoords();
        cordd.add(new Coord(0, 1, 0));
        if (map.checkCoords(cordd)) {
            Block temp = map.getBlock(cordd);
            //System.out.println(cordd);
            if (temp instanceof Smeltable smeltable_temp) {
                System.out.println("Smelted!");
                Block smelted = this.smelt(smeltable_temp);
                smelted.move(cordd);
                map.removeBlock(smeltable_temp);
                map.addBlock(smelted);
            }
        }
    }

    @Override
    public String getName() {
        return "Furnace";
    }
}
