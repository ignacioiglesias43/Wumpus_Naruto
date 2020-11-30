package wumpus;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;
import java.util.Stack;

import static wumpus.Helpers.*;
import static wumpus.Box.*;

/*
 * En esta clase se realizan las validaciones del juego
 * */
public class MainPanel extends JPanel implements Runnable {
    private final Box[][] table = new Box[10][10];
    private Stack<Box> openSet, closedSet, path;
    private volatile boolean done = false;
    private int inputX = 0, inputY = 0, endX = 0, endY = 0;

    private Thread mainThread;
    private final GameTable gameTable = new GameTable(10);

    public MainPanel() {
        mainThread = new Thread(this);
        mainThread.start();

        setLayout(new BorderLayout());
        add(gameTable, BorderLayout.CENTER);

        openSet = new Stack<>();
        closedSet = new Stack<>();
        path = new Stack<>();
        initTable();
        setRandomPositions();
    }

    public void initGame() {
        /* Iniciar algoritmo a* */
        openSet.push(table[inputX][inputY]);

        analyze(table[inputX][inputY]);
    }

    public void restartGame() {
        openSet.clear();
        closedSet.clear();
        path.clear();
        initTable();
        setRandomPositions();
    }

    public void setRandomPositions() {
        gameTable.restart();
        inputY = 0;
        inputX = 0;
        endY = 0;
        endX = 0;
        /* Pozos */
        for (int i = 0; i < 30; i++) {
            insertObject(30);
        }
        /* Orochimaru (wumpus) */
        insertObject(20);
        //* Naruto (heroe) */
        insertObject(10);
        //* Sasuke (Tesoro) */
        insertObject(60);
        table[inputX][inputY].heuristic(endX, endY);
    }

    public void initTable() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                table[i][j] = new Box(i, j);
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                table[i][j].setBoxesAround(table);
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
                    if (value == 60) {
                        endX = x;
                        endY = y;
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

    public void analyze(Box inputBox) {
        /* Se declaran los stacks de around (vecinos de la casilla) */
        Stack<Box> around, auxStack;
        /* Se obtienen las coordenadas iniciales */
        int x = inputBox.getAttribute(BoxAttribute.X);
        int y = inputBox.getAttribute(BoxAttribute.Y);
        int g = 0;
        /* Comienza un ciclo hasta que se encuentre la ultima casilla */
        while ((x != endX || y != endY) && !openSet.isEmpty()) {
            g++;
            /* Se instancia la casilla actual */
            Box naruto = table[x][y];
            /*gameTable.boxes[x][y].setBackground(Color.GREEN);*/
            naruto.setAttribute(BoxAttribute.F, 1000);
            /* Se elimina de el stack de abiertos y se agrega a cerrados */
            closedSet.push(naruto);
            openSet.remove(naruto);
            /* Para cada casilla en abiertos se actualiza la heuristica y se valida si tiene una menor F que el actual */
            for (Box box : openSet) {
                box.heuristic(g, endX, endY);
                if (box.getAttribute(BoxAttribute.F) < naruto.getAttribute(BoxAttribute.F)) {
                    /* En caso de tener una menor F, se actualiza la instancia de la casilla actual */
                    naruto = box;
                    naruto.setAttribute(BoxAttribute.F, 0);
                }
            }
            /* Se actualizan los valores de X y Y */
            x = naruto.getAttribute(BoxAttribute.X);
            y = naruto.getAttribute(BoxAttribute.Y);

            /* Se obtienen las casillas vecinas a la actual */
            around = naruto.getAround();
            /* Para cada vecino se valida que no sea un enemigo y no se haya revisado anteriormente */
            for (Box box : around) {
                if (!isEnemy(box) && !closedSet.contains(box)) {
                    /* Se crea una varibale temporal para controlar el peso */
                    int tempG = naruto.getAttribute(BoxAttribute.G) + 1;
                    if (openSet.contains(box)) {
                        /* Si el vecino se encuentra en abiertos se valida que el G temporal sea menor al G del vecino*/
                        if (tempG < box.getAttribute(BoxAttribute.G)) {
                            System.out.println(tempG);
                            box.setAttribute(BoxAttribute.G, tempG);
                        }
                    } else {
                        /* En caso de no estar en abiertos se añade la instancia a su padre y se agrega al stack de abiertos */
                        box.setFatherBox(naruto);
                        openSet.push(box);
                    }
                }
            }
        }
        /* Llenar el stack auxiliar */
        auxStack = new Stack<>();
        Box last = table[endX][endY];
        while (last.getFatherBox() != null) {
            last = last.getFatherBox();
            auxStack.push(last);
        }
        /* Invertir el stack auxiliar para llenar el de path */
        for (int i = auxStack.size() - 1; i >= 0; i--) {
            Box box = auxStack.get(i);
            path.push(box);
        }
        path.push(table[endX][endY]);
        /* Levantar bandera para pintar en el metodo runnable */
        done = true;
    }

    public int getNewCharacterValue(int value) {
        switch (value) {
            case 40:
                return 100;
            case 50:
                return 110;
            case 60:
            case 70:
            case 80:
                return 120;
        }
        return 10;
    }

    @Override
    public void run() {
        /* Juego */
        while (true) {
            try {
                if(done) {
                    done = false;
                    if(path.size() - 1 > 0) {
                        Box lastBox = null;
                        for(Box box : path) {
                            int x = box.getAttribute(BoxAttribute.X);
                            int y = box.getAttribute(BoxAttribute.Y);
                            int value = box.getAttribute(BoxAttribute.VALUE);
                            /*gameTable.boxes[x][y].setBackground(Color.BLUE);*/
                            gameTable.createImage(getNewCharacterValue(value), x, y);
                            if(lastBox != null) {
                                x = lastBox.getAttribute(BoxAttribute.X);
                                y = lastBox.getAttribute(BoxAttribute.Y);
                                value = lastBox.getAttribute(BoxAttribute.VALUE);
                                if(value != 0 && value != 10) {
                                    if(value == 100) value = 40;
                                    else if(value == 110) value = 50;

                                    gameTable.createImage(value, x, y);
                                } else gameTable.createImage(0, x, y);
                            }
                            Thread.sleep(500);
                            lastBox = box;
                        }
                        JOptionPane.showMessageDialog(this, "Juego terminado en " + (path.size() - 1) + " pasos",
                                "Terminado", JOptionPane.DEFAULT_OPTION, null);
                    } else {
                        JOptionPane.showMessageDialog(this, "El escenario no tiene solución",
                                "Terminado", JOptionPane.DEFAULT_OPTION, null);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}