package data.userinterface;

import data.blocks.Block;
import data.blocks.Factory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class UIcell extends StackPane {

    private Block content;
    private int blockCode;
    //private int x, y;


    public UIcell(int x, int y) {
        super();
        //this.x = x;
        //this.y = y;
        content = null;
        this.setMaxSize(x, y);
        this.setMinSize(x, y);
        //this.setStyle("-fx-background-color:WHITE");
        this.setBlockCode(-1);
    }

    public int getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(int c) {
        //System.out.println("set: " + c);
        blockCode = c;
        if (c == -1) {
            this.setBackground(null);
            return;
        }

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                Factory.getTexture(c),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(0.8, 0.8, true, true, false, false)
                        )
                )
        );
    }

    public void selectCell() {
        //System.out.println("a");
        this.getChildren().add(new Circle(5));
    }

    public void unSelectCell() {
        //System.out.println("b");

        if (this.getChildren().isEmpty()) {
            return;
        }
        this.getChildren().remove(0);
    }
}
