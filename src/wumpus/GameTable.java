package wumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static wumpus.Helpers.*;

public class GameTable extends JPanel {
    public JButton[][] boxes;

    public GameTable(int max) {
        boxes = new JButton[max][max];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[i].length; j++) {
                boxes[i][j] = new JButton();
                boxes[i][j].setEnabled(true);
                boxes[i][j].setBorder(null);
                boxes[i][j].setBorderPainted(false);
                boxes[i][j].setContentAreaFilled(false);
                boxes[i][j].setHorizontalTextPosition(JButton.CENTER);
                boxes[i][j].setVerticalTextPosition(JButton.CENTER);
                boxes[i][j].setPreferredSize(new Dimension(70, 70));
                add(boxes[i][j]);
            }
            setLayout(new GridLayout(max, max));
        }
    }

    public void restart() {
        for (JButton[] box : boxes) {
            for (JButton jButton : box) {
                ImageIcon icon = new ImageIcon(getCharacterValue(0));
                Image img = getScaledImage(icon.getImage(), 70, 70);
                jButton.setIcon(new ImageIcon(img));
            }
        }
        repaint();
    }

    public Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public void createImage(int characterValue, int x, int y) {
        ImageIcon icon = new ImageIcon(getCharacterValue(characterValue));
        Image img = getScaledImage(icon.getImage(), 70, 70);

        boxes[x][y].setIcon(new ImageIcon(img));
    }
}
/*
 * Valores de cada objeto dentro de la lista:
 * ENTRADA = 1
 * SALIDA = 2
 * HEROE = 10
 * WUMPUS = 20
 * POZO = 30
 * VIENTO = 40
 * HEDOR = 50
 * TESORO = 60
 * */