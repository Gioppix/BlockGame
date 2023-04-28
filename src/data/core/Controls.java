package data.core;

import data.blocks.Block;
import data.blocks.elements.GlassBlock;
import data.utils.Coord;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Controls {
    private final double mouse_sens = 0.1;
    private final double max_speed = 4;
    private final double acc = 8;
    private Map<KeyCode, Boolean> keyboard;
    private double rotV, rotH;
    private int selectedBlock;
    private boolean menuOpen;
    private final int blockCount = 9;
    private Point3D position;
    private Point3D speed;
    private double y2 = 0.9;
    private double x2 = 0.2;
    private double z2 = 0.2;
    //private Point3D acceleration;
    //public List<Block> debug_b;


    public Controls(Point3D initalPos) {
        rotV = 0;
        rotH = 0;
        keyboard = new HashMap<>();
        selectedBlock = 0;
        menuOpen = false;
        position = initalPos;
        speed = new Point3D(0, 0, 0);
        //acceleration = new Point3D(0, 0, 0);
    }

    public double getY2() {
        return y2;
    }

    public double getX2() {
        return x2;
    }

    public double getZ2() {
        return z2;
    }

    public int getSelectedBlock() {
        return selectedBlock;
    }

    public boolean getMenuOpen() {
        return menuOpen;
    }

    public Point3D getPosition() {
        return position;
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            keyboard.put(event.getCode(), true);
        }
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            keyboard.put(event.getCode(), false);
        }
    }

    public void update_orientation(MouseEvent event, int width, int height, Bounds bounds) {
        double x = bounds.getMinX();
        double y = bounds.getMinY();

        rotV += -(event.getSceneX() - ((float) width) / 2) * mouse_sens;
        rotH += (event.getSceneY() - ((float) height) / 2) * mouse_sens;
        rotH = Math.min(rotH, 90);
        rotH = Math.max(rotH, -90);

        try {
            Robot robot = new Robot();
            robot.mouseMove(width / 2 + ((int) x), height / 2 + ((int) y));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void update_mov(Mappa map, double elapsedMillis) {
        //double h = 2;
        //double r = 0.2;
        double g = -10;

        Point3D acceleration = new Point3D(0, 0, 0);
        if (keyboard.get(KeyCode.W) != null && keyboard.get(KeyCode.W)) {
            acceleration = acceleration.add(new Point3D(Math.sin(rotV * Math.PI / 180), 0, 0));
            acceleration = acceleration.add(new Point3D(0, 0, Math.cos(rotV * Math.PI / 180)));
        }
        if (keyboard.get(KeyCode.S) != null && keyboard.get(KeyCode.S)) {
            acceleration = acceleration.add(new Point3D(-Math.sin(rotV * Math.PI / 180), 0, 0));
            acceleration = acceleration.add(new Point3D(0, 0, -Math.cos(rotV * Math.PI / 180)));
        }
        if (keyboard.get(KeyCode.A) != null && keyboard.get(KeyCode.A)) {
            acceleration = acceleration.add(new Point3D(Math.sin((rotV + 90) * Math.PI / 180), 0, 0));
            acceleration = acceleration.add(new Point3D(0, 0, Math.cos((rotV + 90) * Math.PI / 180)));
        }
        if (keyboard.get(KeyCode.D) != null && keyboard.get(KeyCode.D)) {
            acceleration = acceleration.add(new Point3D(Math.sin((rotV - 90) * Math.PI / 180), 0, 0));
            acceleration = acceleration.add(new Point3D(0, 0, Math.cos((rotV - 90) * Math.PI / 180)));
        }
        if (keyboard.get(KeyCode.Q) != null && keyboard.get(KeyCode.Q)) {
            acceleration = acceleration.add(new Point3D(0, -1, 0));
        }
        if (keyboard.get(KeyCode.E) != null && keyboard.get(KeyCode.E)) {
            acceleration = acceleration.add(new Point3D(0, 1, 0));
        }


        if (keyboard.get(KeyCode.SPACE) != null && keyboard.get(KeyCode.SPACE)) {
            speed = new Point3D(speed.getX(), 100, speed.getZ());
            keyboard.put(KeyCode.SPACE, false);
        }
        if (keyboard.get(KeyCode.R) != null && keyboard.get(KeyCode.R)) {
            selectedBlock = (selectedBlock + 1) % blockCount;
            keyboard.put(KeyCode.R, false);
        }
        if (keyboard.get(KeyCode.F) != null && keyboard.get(KeyCode.F)) {
            menuOpen = !menuOpen;
            keyboard.put(KeyCode.F, false);
        }
        acceleration = acceleration.normalize();
        acceleration = acceleration.multiply(acc);
        double elapsedSeconds = elapsedMillis / 1000;
        if (acceleration.magnitude() > 0) {
            //acceleration = new Point3D(acceleration.getX(), g, acceleration.getZ());
            speed = speed.add(acceleration.multiply(elapsedSeconds));
        } else {
            // deceleration if not trying to move anymore (~friction)
            speed = speed.add(-speed.getX() * acc * elapsedSeconds, -speed.getY() * acc * elapsedSeconds, -speed.getZ() * acc * elapsedSeconds);
            //speed = speed.add(-speed.getX() * acc * elapsedSeconds, g * elapsedSeconds, -speed.getZ() * acc * elapsedSeconds);
        }
        if (speed.magnitude() > max_speed) {
            // clamping
            speed = speed.normalize().multiply(max_speed);
        }
        Point3D deltaPos = speed.multiply(elapsedSeconds);

        /*
        if (!Utils.compare(deltaPos.getY(), 0)) {
            double avSpace = 0.5;
            int sign = deltaPos.getY() > 0 ? 1 : -1;

            for (int x = (int) (position.getX() - x2); x <= (int) (position.getX() + x2); x++) {
                for (int z = (int) (position.getZ() - z2); z <= (int) (position.getZ() + z2); z++) {
                    Block b = map.getBlock(x, (int) (position.getY()) + sign, z);
                    if (b != null) {
                        avSpace = ((int) (position.getY() + y2 * sign)) - (position.getY() + y2 * sign);
                    }
                }
            }
            if (Math.abs(deltaPos.getY()) > Math.abs(avSpace)) {
                speed = new Point3D(speed.getX(), 0, speed.getZ());
                deltaPos = new Point3D(deltaPos.getX(), avSpace, deltaPos.getZ());
            }
        }
        if (!Utils.compare(deltaPos.getZ(), 0)) {
            double avSpace = 0.5;
            int sign = deltaPos.getZ() > 0 ? 1 : -1;

            for (int y = (int) (position.getY() - y2); y <= (int) (position.getY() + y2); y++) {
                for (int x = (int) (position.getX() - x2); x <= (int) (position.getX() + x2); x++) {
                    Block b = map.getBlock(x, y, (int) (position.getZ()) + sign);
                    if (b != null) {
                        avSpace = ((int) (position.getZ() + z2 * sign)) - (position.getZ() + z2 * sign);
                    }
                }
            }
            if (Math.abs(deltaPos.getZ()) > Math.abs(avSpace)) {
                speed = new Point3D(speed.getX(), speed.getY(), 0);
                deltaPos = new Point3D(deltaPos.getX(), deltaPos.getY(), avSpace);
            }
        }
        if (!Utils.compare(deltaPos.getX(), 0)) {
            double avSpace = 0.5;
            int sign = deltaPos.getX() > 0 ? 1 : -1;

            for (int y = (int) (position.getY() - y2); y <= (int) (position.getY() + y2); y++) {
                for (int z = (int) (position.getZ() - z2); z <= (int) (position.getZ() + z2); z++) {
                    Block b = map.getBlock((int) (position.getX()) + sign, y, z);
                    if (b != null) {
                        avSpace = ((int) (position.getX() + x2 * sign)) - (position.getX() + x2 * sign);
                    }
                }
            }
            if (Math.abs(deltaPos.getX()) > Math.abs(avSpace)) {
                speed = new Point3D(0, speed.getY(), speed.getZ());
                deltaPos = new Point3D(avSpace, deltaPos.getY(), deltaPos.getZ());
            }
        }

         */


        position = position.add(deltaPos);

        for (int x = (int) (position.getX() - x2); x <= (int) (position.getX() + x2); x++) {
            for (int y = (int) (position.getY() - y2); y <= (int) (position.getY() + y2); y++) {
                for (int z = (int) (position.getZ() - z2); z <= (int) (position.getZ() + z2); z++) {
                    if (map.getBlock(x, y, z) != null) {
                        System.out.println(new Coord(x, y, z));
                        double sopra = (+y2) - (y);
                        if (!(sopra > 0 && sopra < 1)) {
                            sopra = 0;
                        }

                        double sotto = (position.getY() - y2) - (y + 1);
                        if (!(sotto < 0 && sotto > -1)) {
                            sotto = 0;
                        }

                        double nord = (position.getX() + x2) - (x);
                        if (!(nord > 0 && nord < 1)) {
                            nord = 0;
                        }

                        double sud = (position.getX() - x2) - (x + 1);
                        if (!(sud < 0 && sud > -1)) {
                            sud = 0;
                        }

                        double est = (position.getZ() + z2) - (z);
                        if (!(est > 0 && est < 1)) {
                            est = 0;
                        }

                        double ovest = (position.getZ() - z2) - (z + 1);
                        if (!(ovest < 0 && ovest > -1)) {
                            ovest = 0;
                        }
                        System.out.println(sopra + " " + sotto);
                        double minX = Math.min(nord, -sud);
                        double minY = Math.min(sopra, -sotto);
                        double minZ = Math.min(est, -ovest);
                        if (minX < minZ && minX < minY) {
                            System.out.println("minX");

                            position = new Point3D(position.getX() - (minX == nord ? nord : sud), position.getY(), position.getZ());
                            speed = new Point3D(0, speed.getY(), speed.getZ());
                        } else {
                            if (minZ < minY && minZ < minX) {
                                System.out.println("minZ");

                                position = new Point3D(position.getX(), position.getY(), position.getZ() - (minZ == est ? est : ovest));
                                speed = new Point3D(speed.getX(), speed.getY(), 0);
                            } else {
                                System.out.println("minY: " + (minY == sopra ? sopra : sotto));

                                position = new Point3D(position.getX(), position.getY() - (minY == sopra ? sopra : sotto), position.getZ());
                                speed = new Point3D(speed.getX(), 0, speed.getZ());
                            }
                        }

                    }
                }
            }
        }

    }

    public double getRotV() {
        return rotV;
    }

    public double getRotH() {
        return rotH;
    }

    public Point3D getPos() {
        return position;
    }


    @Override
    public String toString() {
        return (
                "V=" + String.format("%." + 1 + "f", rotV) +
                        ", H=" + String.format("%." + 1 + "f", rotH) +
                        ", X=" + String.format("%." + 1 + "f", position.getX()) +
                        ", Y=" + String.format("%." + 1 + "f", position.getY()) +
                        ", Z=" + String.format("%." + 1 + "f", position.getZ()) +
                        ", sb=" + selectedBlock + "\n" +
                        "Speed = " + String.format("%." + 1 + "f", Math.sqrt(speed.getX() * speed.getX() + speed.getY() * speed.getY() + speed.getZ() * speed.getZ())) +
                        " "
        );
    }
}
