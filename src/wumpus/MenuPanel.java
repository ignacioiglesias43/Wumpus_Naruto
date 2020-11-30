package wumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * En esta clase va el menu
 */
public class MenuPanel extends JPanel {
    private JPanel btnPanel = new JPanel();
    public JLabel init, about, exit;
    private JLabel title = new JLabel("Wumpus Naruto");
    private Image background;
    private Font narutoFont;

    public MenuPanel() {
        Font();
        init = new JLabel();
        about = new JLabel();
        exit = new JLabel();
        init.setIcon(new ImageIcon("src/buttons/init.png"));
        about.setIcon(new ImageIcon("src/buttons/about.png"));
        exit.setIcon(new ImageIcon("src/buttons/exit.png"));

        init.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        about.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        exit.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
        title.setFont(new Font(narutoFont.getName(), Font.PLAIN, 30));

        setBackground("src/assets/fondo.jpg");
        setLayout(new GridLayout(10, 1, 0, 0));
        setBorder(BorderFactory.createEmptyBorder(150, 250, -350, 100));
        add(title);
        add(init);
        add(about);
        add(exit);
    }

    public void Font() {
        try {
            narutoFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/njnaruto.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/njnaruto.ttf")));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        int width = this.getSize().width;
        int height = this.getSize().height;

        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    public void setBackground(String imagePath) {
        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }
}
