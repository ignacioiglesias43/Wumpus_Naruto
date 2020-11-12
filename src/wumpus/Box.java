package wumpus;

public class Box {

    public enum BoxAttribute {X, Y, VALUE}

    private int x, y, value;

    public Box(int x, int y) {
        this.value = 0;
        this.x = x;
        this.y = y;
    }

    public int getAttribute(BoxAttribute attribute) {
        switch (attribute) {
            case X:
                return this.x;
            case Y:
                return this.y;
            case VALUE:
                return this.value;
            default:
                return -1;
        }
    }

    public void setAttribute(BoxAttribute attribute, int value) {
        switch (attribute) {
            case X:
                this.x = value;
                break;
            case Y:
                this.y = value;
                break;
            case VALUE:
                this.value = value;
                break;
            default:
                break;
        }
    }
}
