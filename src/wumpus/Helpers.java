package wumpus;

import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;

import wumpus.Box.*;
/**
 * Clase de Helpers
 * */
public class Helpers {
    /* Este metodo devuelve un numero aleatorio entre 0 y 9 */
    public static int getRandomPosition() {
        Random rand = new Random();
        return rand.nextInt(10);
    }

    /* Este metodo sirve para visualizar la tabla en consola */
    public static String printTable(Box[][] table) {
        String result = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                result += " [" + table[i][j].getAttribute(BoxAttribute.VALUE) + "] ";
            }
            result += "\n";
        }
        return result;
    }

    /* Devuelve las posiciones de la matriz para el hedor y/o viento */
    public static Hashtable<String, Integer> aroundObjectPositions(int x, int y) {
        Hashtable hash = new Hashtable();
        if(x > 0 && x < 9 && y > 0 && y < 9) {
            /* Todo lo de en medio */
            hash.put("x0", x);
            hash.put("y0", y - 1);
            hash.put("x1", x - 1);
            hash.put("y1", y - 1);
            hash.put("x2", x + 1);
            hash.put("y2", y - 1);
            hash.put("x3", x - 1);
            hash.put("y3", y);
            hash.put("x4", x + 1);
            hash.put("y4", y);
            hash.put("x5", x - 1);
            hash.put("y5", y + 1);
            hash.put("x6", x);
            hash.put("y6", y + 1);
            hash.put("x7", x + 1);
            hash.put("y7", y + 1);
        } else if(x != 0 && x != 9){
            hash.put("x0", x + 1);
            hash.put("y0", y);
            hash.put("x1", x - 1);
            hash.put("y1", y);
            if(y == 0) {
                /* Mitad vertical lado izq */
                hash.put("x2", x - 1);
                hash.put("y2", y + 1);
                hash.put("x3", x);
                hash.put("y3", y + 1);
                hash.put("x4", x + 1);
                hash.put("y4", y + 1);
            } else if(y == 9) {
                /* Mitad vertical lado derecho */
                hash.put("x2", x + 1);
                hash.put("y2", y - 1);
                hash.put("x3", x);
                hash.put("y3", y - 1);
                hash.put("x4", x - 1);
                hash.put("y4", y - 1);
            }
        } else {
            /* Esquinas */
            if(x == 0) {
                hash.put("x0", x + 1);
                hash.put("y0", y);
                if(y == 0) {
                    hash.put("x1", x);
                    hash.put("y1", y + 1);
                    hash.put("x2", x + 1);
                    hash.put("y2", y + 1);
                } else {
                    hash.put("x1", x);
                    hash.put("y1", y - 1);
                    hash.put("x2", x + 1);
                    hash.put("y2", y - 1);
                    if(y < 9) {
                        hash.put("x3", x);
                        hash.put("y3", y + 1);
                        hash.put("x4", x + 1);
                        hash.put("y4", y + 1);
                    }
                }
            } else {
                hash.put("x0", x - 1);
                hash.put("y0", y);
                if(y == 0) {
                    hash.put("x1", x - 1);
                    hash.put("y1", y + 1);
                    hash.put("x2", x);
                    hash.put("y2", y + 1);
                } else {
                    hash.put("x1", x - 1);
                    hash.put("y1", y - 1);
                    hash.put("x2", x);
                    hash.put("y2", y - 1);
                    if(y < 9) {
                        hash.put("x3", x);
                        hash.put("y3", y + 1);
                        hash.put("x4", x - 1);
                        hash.put("y4", y + 1);
                    }
                }
            }
        }
        return hash;
    }

    /* Metodo para validar que no se encuentre encerrado al iniciar */

    public static boolean isLockedUp(Box[][] table, int x, int y) {
        return (y != 9 && isEnemy(table[x][y + 1])) && (y != 0 && isEnemy(table[x][y - 1])) && (x != 0 && isEnemy(table[x - 1][y])) && (x != 9 && isEnemy(table[x + 1][y]));
    }

    /* Verifica si el valor es de un enemigo */

    public static boolean isEnemy(Box box) {
        return box.getAttribute(BoxAttribute.VALUE) == 20 || box.getAttribute(BoxAttribute.VALUE) == 30;
    }

    public static int combineValues(int inputValue, int actualValue) {
        int realValue = inputValue;
        if(actualValue == 40 && inputValue == 60) realValue = 70;
        else if(actualValue == 50 && inputValue == 60) realValue = 80;
        else if(actualValue == 40 && inputValue == 10) realValue = 100;
        else if(actualValue == 50 && inputValue == 10) realValue = 110;

        return realValue;
    }

    /* Este metodo devuelve la ruta de la imagen del objeto */
    public static String getCharacterValue(int characterValue) {
        switch (characterValue) {
            case 1:
                return "src/assets/entrada.png";
            case 2:
                return "src/assets/salida.png";
            case 10:
                return "src/assets/naruto.png";
            case 20:
                return "src/assets/orochimaru.png";
            case 30:
                return "src/assets/hoyo.png";
            case 40:
                return "src/assets/viento.png";
            case 50:
                return "src/assets/serpiente.png";
            case 60:
                return "src/assets/sasuke.png";
            case 70:
                return "src/assets/viento_sasuke.png";
            case 80:
                return "src/assets/serpiente_sasuke.png";
            case 90:
                return "src/assets/viento_serpiente.png";
            case 100:
                return "src/assets/viento_naruto.png";
            case 110:
                return "src/assets/serpiente_naruto.png";
            case 120:
                return "src/assets/sasuke_naruto.png";
        }
        return null;
    }

    /* La casilla se encuentra disponible */
    public static boolean isAvailableBox(int value) {
        return value == 0 || value == 40 || value == 50;
    }
}
