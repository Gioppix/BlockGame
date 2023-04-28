package data.blocks;

import data.blocks.elements.*;
import javafx.scene.image.Image;


public class Factory {
    public static Block createBlock(int code) {
        switch (code) {
            case 0 -> {
                return new DirtBlock();
            }
            case 1 -> {
                return new IronOreBlock();
            }
            case 2 -> {
                return new GlassBlock();
            }
            case 3 -> {
                return new SandBlock();
            }
            case 4 -> {
                return new FurnaceBlock();
            }
        }
        return null;
    }

    public static Image getTexture(int code) {
        switch (code) {
            case 0 -> {
                //System.out.println(DirtBlock.getTexture());
                return DirtBlock.getTexture();
            }
            case 1 -> {
                return IronOreBlock.getTexture();
            }
            case 2 -> {
                return GlassBlock.getTexture();
            }
            case 3 -> {
                return SandBlock.getTexture();
            }
            case 4 -> {
                return FurnaceBlock.getTexture();
            }
        }
        return new Image("textures/GIOVANNI.JPG");
    }
}
