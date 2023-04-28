package data.userinterface;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class UIelement extends HBox {
    protected UIcell[][] cells;
    protected final int scale = 30;

    public UIelement(int cols, int rows) {
        super();
        this.setStyle("-fx-background-color:#bfbfbf");
        cells = new UIcell[cols][rows];
        for (int i = 0; i < cols; i++) {
            VBox v = new VBox();
            for (int j = 0; j < rows; j++) {
                UIcell temp = new UIcell(scale, scale);
                v.getChildren().add(temp);
                cells[i][j] = temp;
            }
            this.getChildren().add(v);
        }
        this.setMaxSize(cols * scale, rows * scale);
        this.setMinSize(cols * scale, rows * scale);
        //this.setStyle();
    }
}
