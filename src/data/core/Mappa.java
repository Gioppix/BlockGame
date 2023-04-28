package data.core;

import data.blocks.*;
import data.blocks.elements.*;
import data.core.Controls;
import data.utils.Coord;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;

import java.util.Random;

public class Mappa {
    private Block[][][] blocks;
    private int x, y, z;

    private Group root;

    //private Controls controls;

    public void handleClickk(javafx.scene.input.MouseEvent event, int selectedBlock) {

        Block b = (Block) event.getTarget();
        System.out.println(b.getCoords());
        if (event.getButton() == MouseButton.PRIMARY) {
            if (b.subHp(1000)) {
                this.removeBlock(b);
            }


        }
        if (event.getButton() == MouseButton.SECONDARY) {

            //System.out.println(event.getX() + " " + event.getY() + " " + event.getZ());
            //root.getChildren().remove(event.getTarget());
            if (b instanceof Interactable i) {
                i.onclick(this);
                //System.out.println("int!!");
            } else {
                if (selectedBlock == -1) {
                    return;
                }
                Block temp = Factory.createBlock(selectedBlock);


                Coord old = b.getCoords();
                Point3D point = event.getPickResult().getIntersectedPoint();

                temp.move(new Coord(
                        old.x + (Utils.compare(point.getX(), 0.5) ? 1 : 0) + (Utils.compare(point.getX(), -0.5) ? -1 : 0),
                        old.y + (Utils.compare(point.getY(), 0.5) ? 1 : 0) + (Utils.compare(point.getY(), -0.5) ? -1 : 0),
                        old.z + (Utils.compare(point.getZ(), 0.5) ? 1 : 0) + (Utils.compare(point.getZ(), -0.5) ? -1 : 0)
                ));

                //root.getChildren().add(temp);
                //System.out.println(temp);

                this.addBlock(temp);
            }

        }
    }


    public Mappa(int x, int y, int z, Group root) {
        //this.controls = c;

        this.root = root;
        this.x = x;
        this.y = y;
        this.z = z;
        //Random r = new Random();
        this.blocks = new Block[x][y][z];

        for (int x1 = 0; x1 < x; x1++) {
            for (int y1 = 0; y1 < y / 2; y1++) {
                for (int z1 = 0; z1 < z; z1++) {
                    DirtBlock temp = new DirtBlock();
                    temp.move(new Coord(x1, y1, z1));
                    this.addBlock(temp);
                }
            }
        }
    }

    private void checkGravity() {
        boolean moved = false;
        for (int x1 = 0; x1 < x; x1++) {
            for (int y1 = 0; y1 < y; y1++) {
                for (int z1 = z - 1; z1 >= 0; z1--) {
                    if (this.blocks[x1][y1][z1] != null && this.blocks[x1][y1][z1].falls_with_gravity() && y1 != 0 && this.blocks[x1][y1 - 1][z1] == null) {
                        this.swap(new Coord(x1, y1, z1), new Coord(x1, y1 - 1, z1));
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            //CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS).execute(this::checkGravity);
            this.checkGravity();
        }
    }

    private void swap(Coord c1, Coord c2) {
        Block a = blocks[c1.x][c1.y][c1.z];

        blocks[c1.x][c1.y][c1.z] = blocks[c2.x][c2.y][c2.z];
        a.move(c2);
        if (blocks[c2.x][c2.y][c2.z] != null) {
            blocks[c2.x][c2.y][c2.z].move(c1);
        }
        blocks[c2.x][c2.y][c2.z] = a;
    }


    public void addBlock(Block b) {
        Coord point = b.getCoords();
        //System.out.println(xb + " " + yb + " " + zb);

        if (!(point.x >= 0 && point.x < this.x)) {
            return;
        }
        if (!(point.y >= 0 && point.y < this.y)) {
            return;
        }
        if (!(point.z >= 0 && point.z < this.z)) {
            return;
        }
        //System.out.println(this.blocks[xb][yb][zb]);

        if (this.blocks[point.x][point.y][point.z] != null) {
            System.out.println("Block not placed!");

            return;
        }

        this.root.getChildren().add(b.getDisplay());
        this.blocks[point.x][point.y][point.z] = b;
        this.checkGravity();
        //this.checkVisibility(b.getCoords());
    }

    private void checkVisibilityy(Coord c) {
        /*
        for (int x1 = 0; x1 < x; x1++) {
            for (int y1 = 0; y1 < y; y1++) {
                for (int z1 = 0; z1 < z; z1++) {
                    if (blocks[x1][y1][z1] != null) {
                        this.root.getChildren().remove(blocks[x1][y1][z1].getDisplay());
                        boolean circondato = true;
                        for (int dx1 = x1 - 1; dx1 <= x1 + 1; dx1++) {
                            for (int dy1 = y1 - 1; dy1 <= y1 + 1; dy1++) {
                                for (int dz1 = z1 - 1; dz1 <= z1 + 1; dz1++) {
                                    if (checkCoords(new Coord(dx1, dy1, dz1))) {
                                        if (blocks[dx1][dy1][dz1] == null) {
                                            circondato = false;
                                        }
                                    } else {
                                        circondato = false;
                                    }
                                }
                            }
                        }
                        if (!circondato) {
                            this.root.getChildren().add(blocks[x1][y1][z1].getDisplay());
                        }
                    }
                }
            }
        }

         */
        //if (blocks[x1][y1][z1] != null) {
        int x1 = c.x;
        int y1 = c.y;
        int z1 = c.z;
        this.root.getChildren().remove(blocks[x1][y1][z1].getDisplay());
        boolean circondato = true;
        for (int dx1 = x1 - 1; dx1 <= x1 + 1; dx1++) {
            for (int dy1 = y1 - 1; dy1 <= y1 + 1; dy1++) {
                for (int dz1 = z1 - 1; dz1 <= z1 + 1; dz1++) {
                    if (checkCoords(new Coord(dx1, dy1, dz1))) {
                        if (blocks[dx1][dy1][dz1] == null) {
                            circondato = false;
                        }
                    } else {
                        circondato = false;
                    }
                }
            }
        }
        if (!circondato) {
            this.root.getChildren().add(blocks[x1][y1][z1].getDisplay());
        }
        //}
    }

    public boolean checkCoords(Coord p) {
        if (p.x >= 0 && p.y >= 0 && p.z >= 0 && p.x < x && p.y < y && p.z < z) {
            return true;
        }
        return false;
    }

    public Block getBlock(Coord p) {
        if (!checkCoords(p)) {
            return null;
        }
        return blocks[p.x][p.y][p.z];
    }

    public Block getBlock(int x, int y, int z) {
        return this.getBlock(new Coord(x, y, z));
    }

    public void removeBlock(Block b) {
        Coord point = b.getCoords();
        this.blocks[point.x][point.y][point.z] = null;
        this.root.getChildren().remove(b);
        this.checkGravity();
        //this.checkVisibility(b.getCoords());

    }
}
