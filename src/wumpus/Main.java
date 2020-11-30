package wumpus;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Image icon = Toolkit.getDefaultToolkit().getImage("src/assets/sasuke_naruto.png");
        Window window = new Window();
        window.setVisible(true);
        window.setIconImage(icon);
    }
}
