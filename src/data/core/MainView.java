package data.core;

import data.blocks.Block;
import data.userinterface.HotBar;
import data.userinterface.Menu;
import data.userinterface.UIcell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainView {
    private int width;
    private int height;
    private PerspectiveCamera camera;
    private Mappa mappa;
    private Controls controls;
    private Label label;

    private HotBar hotbar;
    private Menu menu;

    private Box player;

    private int selectedBlock = -1;

    public MainView(int width, int height, Stage primaryStage) {
        this.width = width;
        this.height = height;
        label = new Label();
        // Create a 3D scene
        Group root = new Group();
        //Group world = new Group();
        SubScene subScene = new SubScene(root, this.width, this.height, true, SceneAntialiasing.DISABLED);
        subScene.setFill(Color.LIGHTGRAY);
        //subScene.ren
        camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setFieldOfView(90);
        //camera.rota(180.0);
        subScene.setCamera(camera);


        int xb = 10, yb = 10, zb = 10;

        root.getChildren().add(subScene);

        // Create the main layout
        //label.setAlignment(label, javafx.geometry.Pos.TOP_LEFT);

        StackPane layout = new StackPane();
        hotbar = new HotBar();
        menu = new Menu();
        //Mappa m = new Mappa(xb, yb, zb, root, controls);

        //layout.getChildren().add(hotbar);
        //hotbar.setViewOrder(200);

        layout.getChildren().addAll(subScene, label, hotbar, menu);
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        StackPane.setAlignment(hotbar, Pos.TOP_RIGHT);
        StackPane.setAlignment(menu, Pos.CENTER);


        // Set the stage and show it
        Scene scene = new Scene(layout, this.width, this.height);
        primaryStage.setScene(scene);
        primaryStage.show();


        for (int i = 0; i < 5; i++) {
            menu.setContent(i, 0, i);

        }


        // Set the focus to the root so that it can receive key events
        root.requestFocus();

        this.controls = new Controls(new Point3D((float) xb / 2, (float) yb / 2, (float) zb / 2));
        this.mappa = new Mappa(xb, yb, zb, root);
        {
            player = new Box(controls.getX2() * 2, controls.getY2() * 2, controls.getZ2() * 2);
            player.setMaterial(new PhongMaterial(Color.WHITE));
            root.getChildren().add(player);
        }


        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.millis(1000.0 / 60),
                        event -> update(1000.0 / 60)));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();


        // Set the focus to the root so that it can receive key events
        root.requestFocus();

        layout.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof Block) {
                mappa.handleClickk(event, hotbar.getSelectedBlock());
                //System.out.println("Click on Block");
            } else if (event.getTarget() instanceof UIcell cell) {
                //System.out.println("Click on cell");
                int temp = cell.getBlockCode();
                cell.setBlockCode(selectedBlock);
                selectedBlock = temp;
                //System.out.println(cell.getBlockCode());
            } else {
                System.out.println("lol");
            }
        });
        root.setOnKeyPressed(event -> {
            controls.handleKeyPressed(event);
        });
        root.setOnKeyReleased(event -> {
            controls.handleKeyPressed(event);
        });
        subScene.setOnMouseMoved((MouseEvent event) -> {
            if (!controls.getMenuOpen()) {
                controls.update_orientation(event, this.getWidth(), this.getHeight(), subScene.localToScreen(subScene.getBoundsInLocal()));
            }
        });
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void update(double elapsedMillis) {
        controls.update_mov(mappa, elapsedMillis);
        player.setTranslateX(controls.getPos().getX());
        player.setTranslateY(controls.getPos().getY());
        player.setTranslateZ(controls.getPos().getZ());
        camera.getTransforms().clear();
        camera.getTransforms().addAll(new Rotate(controls.getRotV(), Rotate.Y_AXIS), new Rotate(controls.getRotH(), Rotate.X_AXIS), new Rotate(180, Rotate.Z_AXIS));
        camera.setTranslateX(controls.getPos().getX());
        camera.setTranslateY(controls.getPos().getY());
        camera.setTranslateZ(controls.getPos().getZ());


        label.setText(controls.toString() + "gs = " + selectedBlock);
        hotbar.update(controls.getSelectedBlock());
        menu.setVisible(controls.getMenuOpen());
    }


}
