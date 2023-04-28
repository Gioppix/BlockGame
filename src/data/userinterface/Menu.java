package data.userinterface;

public class Menu extends UIelement {
    public Menu() {
        super(9, 3);
    }

    public void setContent(int col, int row, int blockcode) {
        this.cells[col][row].setBlockCode(blockcode);
    }
}
