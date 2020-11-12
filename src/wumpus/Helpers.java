package wumpus;

import java.util.Hashtable;
import java.util.Random;
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
    public static String printTable(int[][] table) {
        String result = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                result += " [" + table[i][j] + "] ";
            }
            result += "\n";
        }
        return result;
    }

    /* Devuelve las posiciones de la matriz para el hedor y/o viento */
    public static Hashtable<String, Integer> aroundObjectPositions(int x, int y) {
        Hashtable<String, Integer> hash = new Hashtable();
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
        }
        return null;
    }
}
