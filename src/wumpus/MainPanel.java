package wumpus;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

import static wumpus.Helpers.*;
import static wumpus.Box.*;

/*
 * En esta clase se realizan las validaciones del juego
 * */
public class MainPanel extends JPanel implements Runnable {
    private Box[][] table = new Box[10][10];
    private int inputX, inputY;
    private Thread mainThread;
    private final GameTable gameTable = new GameTable(10);

    public MainPanel() {
        JPanel panelBtns = new JPanel(new GridLayout(1, 2, 20, 0));
        JButton btnInit = new JButton("Iniciar");
        JButton btnRestart = new JButton("Reiniciar");
        mainThread = new Thread(this);
        panelBtns.add(btnInit);
        panelBtns.add(btnRestart);

        setLayout(new BorderLayout());
        add(gameTable, BorderLayout.NORTH);
        add(panelBtns, BorderLayout.CENTER);
        initTable();
        setRandomPositions();
        System.out.println(printTable(table));

        btnInit.addActionListener(e -> {
            /* Iniciar algoritmo a* */
            btnInit.setEnabled(false);
            mainThread.start();
        });

        btnRestart.addActionListener(e -> {
            initTable();
            setRandomPositions();
        });
    }

    public void setRandomPositions() {
        gameTable.restart();
        /* Pozos */
        insertObject(30);
        insertObject(30);
        insertObject(30);
        insertObject(30);
        insertObject(30);
        insertObject(30);
        /* Orochimaru (wumpus) */
        insertObject(20);
        /* Naruto (heroe) */
        insertObject(10);
        /* Sasuke (Tesoro) */
        insertObject(60);
    }

    public void initTable() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                table[i][j] = new Box(i, j);
            }
        }
    }

    /* Insertar un objeto dentro de la matriz (heroe, wumpus, pozo, tesoro) */
    public void insertObject(int value) {
        int x = getRandomPosition();
        int y = getRandomPosition();
        /* Acomodar solo si es vacio, viento o hedor */
        if (isAvailableBox(table[x][y].getAttribute(BoxAttribute.VALUE))) {
            if (value == 60 || value == 10) {
                if (!isLockedUp(table, x, y)) {
                    /* Combinacion de valores dependiendo si es el tesoro o el heroe */
                    int realValue = combineValues(value, table[x][y].getAttribute(BoxAttribute.VALUE));
                    table[x][y].setAttribute(BoxAttribute.VALUE, realValue);
                    gameTable.createImage(realValue, x, y);
                    if (value == 10) {
                        inputX = x;
                        inputY = y;
                    }
                } else insertObject(value);
            } else if (value == 30 || value == 20) {
                table[x][y].setAttribute(BoxAttribute.VALUE, value);
                gameTable.createImage(value, x, y);
                insertAroundObject(value, x, y);
            }

        } else insertObject(value);
    }

    /* Inserta hedor o viento alrededor del objeto */
    public void insertAroundObject(int objectValue, int x, int y) {
        int aroundValue = objectValue == 20 ? 50 : 40;
        Hashtable<String, Integer> positions = aroundObjectPositions(x, y);
        for (int i = 0; i < positions.size() / 2; i++) {
            int newX = positions.get("x" + i);
            int newY = positions.get("y" + i);
            if (isAvailableBox(table[newX][newY].getAttribute(BoxAttribute.VALUE))) {
                if (table[newX][newY].getAttribute(BoxAttribute.VALUE) != 0 && aroundValue != table[newX][newY].getAttribute(BoxAttribute.VALUE)) {
                    table[newX][newY].setAttribute(BoxAttribute.VALUE, 90);
                    gameTable.createImage(90, newX, newY);
                } else {
                    table[newX][newY].setAttribute(BoxAttribute.VALUE, aroundValue);
                    gameTable.createImage(aroundValue, newX, newY);
                }
            }
        }
    }

    @Override
    public void run() {
        /* Juego */
        while(true) {
            try {
                Thread.sleep(2000);
//                table[inputX][inputY].setAttribute(BoxAttribute.VALUE,0);
                /*gameTable.boxes[inputX][inputY].setIcon(null);
                inputY++;
                gameTable.createImage(10, inputX, inputY);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}