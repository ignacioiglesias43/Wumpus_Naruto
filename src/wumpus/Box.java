package wumpus;

import java.util.Stack;

public class Box {

    public enum BoxAttribute {X, Y, VALUE, G, F, H}

    private int x, y, value, g, h, f;
    private Stack<Box> around;
    private Box fatherBox;

    public Box(int x, int y) {
        this.value = 0;
        this.x = x;
        this.y = y;
        this.g = 0;
        this.f = 0;
        this.h = 0;
        this.around = new Stack<>();
        this.fatherBox = null;
    }

    public int getAttribute(BoxAttribute attribute) {
        switch (attribute) {
            case X:
                return this.x;
            case Y:
                return this.y;
            case VALUE:
                return this.value;
            case G:
                return this.g;
            case F:
                return this.f;
            case H:
                return this.h;
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
            case G:
                this.g = value;
                break;
            case F:
                this.f = value;
                break;
            case H:
                this.h = value;
            default:
                break;
        }
    }

    public void setAround(Stack<Box> around) {
        this.around = around;
    }

    public Stack<Box> getAround() {
        return around;
    }

    public Box getFatherBox() {
        return fatherBox;
    }

    public void setFatherBox(Box fatherBox) {
        this.fatherBox = fatherBox;
    }

    public void setBoxesAround(Box[][] table) {
        int x = getAttribute(BoxAttribute.X);
        int y = getAttribute(BoxAttribute.Y);
        Stack<Box> aroundBoxes = new Stack<>();

        if(x > 0)
            aroundBoxes.push(table[x-1][y]);
        if(x < 9)
            aroundBoxes.push(table[x+1][y]);
        if(y > 0)
            aroundBoxes.push(table[x][y-1]);
        if(y < 9)
            aroundBoxes.push(table[x][y+1]);

        setAround(aroundBoxes);
    }

    public void heuristic(int g, int endX, int endY) {
        int x = getAttribute(BoxAttribute.X);
        int y = getAttribute(BoxAttribute.Y);
        int h = Math.abs(endX - x) + Math.abs(endY - y);
        setAttribute(BoxAttribute.G, g);
        setAttribute(BoxAttribute.H, h);
        setAttribute(BoxAttribute.F, h + g);
    }

    public void heuristic(int endX, int endY) {
        int x = getAttribute(BoxAttribute.X);
        int y = getAttribute(BoxAttribute.Y);
        int h = Math.abs(endX - x) + Math.abs(endY - y);
        int g = getAttribute(BoxAttribute.G);
        setAttribute(BoxAttribute.G, g);
        setAttribute(BoxAttribute.H, h);
        setAttribute(BoxAttribute.F, h + g);
    }
}
