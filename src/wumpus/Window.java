package wumpus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Window extends JFrame {
    private MainPanel mainPanel = new MainPanel();
    private MenuPanel menuPanel = new MenuPanel();
    private JMenuBar menu = new JMenuBar();
    private JMenu opt = new JMenu("Opciones");
    private JMenuItem play = new JMenuItem("Iniciar");
    private JMenuItem restart = new JMenuItem("Reiniciar");
    private JMenuItem exit = new JMenuItem("Salir");
    private JLabel init, about, exitL;

    public Window() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int top = getInsets().top;
        int bottom = getInsets().bottom;
        int left = getInsets().left;
        int right = getInsets().right;

        int x1 = 700 + left + right;
        int y1 = 700 + top + bottom;

        int x = (screenSize.width - x1) / 2;
        int y = (screenSize.height - y1) / 2;
        init = menuPanel.init;
        about = menuPanel.about;
        exitL = menuPanel.exit;

        init.addMouseListener(MouseListener(1));
        about.addMouseListener(MouseListener(2));
        exitL.addMouseListener(MouseListener(3));

        Menu();
        setContentPane(menuPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(x1, y1);
        setLocation(x, y);
        setTitle("Wumpus-Naruto");
        setVisible(true);
        setResizable(false);
    }

    public void Menu() {
        opt.add(play);
        opt.add(restart);
        opt.add(exit);
        menu.add(opt);
        menu.setPreferredSize(new Dimension(menu.getPreferredSize().width, 25));
        menu.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 200), 3));
        play.addActionListener(e -> mainPanel.initGame());
        restart.addActionListener(e -> mainPanel.restartGame());
        exit.addActionListener(e -> System.exit(0));
    }

    public MouseListener MouseListener(int type) {
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (type) {
                    case 1:
                        setJMenuBar(menu);
                        getContentPane().remove(menuPanel);
                        setContentPane(mainPanel);
                        pack();
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Ignacio Iglesias Campoy :)", "Desarrollado por: ", JOptionPane.DEFAULT_OPTION);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                switch (type) {
                    case 1:
                        init.setIcon(new ImageIcon("src/buttons/init_hover.png"));
                        init.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        break;
                    case 2:
                        about.setIcon(new ImageIcon("src/buttons/about_hover.png"));
                        about.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        break;
                    case 3:
                        exitL.setIcon(new ImageIcon("src/buttons/exit_hover.png"));
                        exitL.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        break;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switch (type) {
                    case 1:
                        init.setIcon(new ImageIcon("src/buttons/init.png"));
                        break;
                    case 2:
                        about.setIcon(new ImageIcon("src/buttons/about.png"));
                        break;
                    case 3:
                        exitL.setIcon(new ImageIcon("src/buttons/exit.png"));
                        break;
                }
            }
        };
        return mouseListener;
    }
}
