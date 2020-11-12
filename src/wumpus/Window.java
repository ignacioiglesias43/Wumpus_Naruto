package wumpus;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = 600;
        int y1 = 600;

        int x = (screenSize.width - x1) / 2;
        int y = (screenSize.height - y1) / 2;

        setContentPane(new MainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(x1, y1);
        setLocation(x, y);
        setTitle("Wumpus-Naruto");
        setVisible(true);
        setResizable(false);
    }
}
