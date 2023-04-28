package data.userinterface;

public class HotBar extends UIelement {
    private int selected = -1;

    public HotBar() {
        super(9, 1);
    }

    public void update(int selectedSlot) {
        if (selectedSlot != selected) {
            selected = selectedSlot;
            cells[(selectedSlot - 1 + 9) % 9][0].unSelectCell();
            cells[selectedSlot][0].selectCell();
        }
    }

    public int getSelectedBlock() {
        return cells[selected][0].getBlockCode();
    }
}
