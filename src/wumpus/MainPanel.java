package wumpus;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

import static wumpus.Helpers.*;

/*
 * En esta clase se realizan las validaciones del juego
 * */
public class MainPanel extends JPanel {
    private int[][] table = new int[10][10];
    private final GameTable gameTable = new GameTable(10);

    public MainPanel() {
        JPanel panelBtns = new JPanel(new GridLayout(1, 2, 20, 0));
        JButton btnInit = new JButton("Iniciar");
        JButton btnRestart = new JButton("Reiniciar");

        panelBtns.add(btnInit);
        panelBtns.add(btnRestart);

        setLayout(new BorderLayout());
        add(gameTable, BorderLayout.NORTH);
        add(panelBtns, BorderLayout.CENTER);

        btnInit.addActionListener(e -> {
            initTable();
            setRandomPositions();
            System.out.println(printTable(table));
            btnInit.setEnabled(false);
        });

        btnRestart.addActionListener(e -> {
            initTable();
            setRandomPositions();
        });
    }

    public void setRandomPositions() {
        gameTable.restart();
        /* Salida */
        insertObject(2);
        /* Sasuke (Tesoro) */
        insertObject(60);
        /* Pozos */
        for (int i = 0; i < 3; i++) {
            insertObject(30);
        }
        /* Orochimaru (wumpus) */
        insertObject(20);
        /* Naruto (heroe) */
        insertObject(10);
    }

    public void initTable() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                table[i][j] = 0;
            }
        }
    }

    /* Insertar un objeto dentro de la matriz (heroe, wumpus, pozo, tesoro) */
    public void insertObject(int value) {
        int x = getRandomPosition();
        int y = getRandomPosition();
		/* Acomodar solo si es vacio, viento o hedor */
        if (table[x][y] == 0 || table[x][y] == 40 || table[x][y] == 50) {
            table[x][y] = value;
            gameTable.createImage(value, x, y);
            if (value == 30 || value == 20) insertAroundObject(value, x, y);
        } else insertObject(value);
    }

    /* Inserta hedor o viento alrededor del objeto */
    public void insertAroundObject(int objectValue, int x, int y) {
        int aroundValue = objectValue == 20 ? 50 : 40;
        Hashtable<String, Integer> positions = aroundObjectPositions(x, y);
        for (int i = 0; i < positions.size() / 2; i++) {
            int newX = positions.get("x" + i);
            int newY = positions.get("y" + i);
            if (table[newX][newY] == 0 || table[newX][newY] == 40 || table[newX][newY] == 50) {
                table[newX][newY] = aroundValue;
                gameTable.createImage(aroundValue, newX, newY);
            }
        }
    }
}