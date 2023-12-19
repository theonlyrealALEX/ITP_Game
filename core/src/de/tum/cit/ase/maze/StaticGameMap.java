package de.tum.cit.ase.maze;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class StaticGameMap {
    private MapObject[][] mapObjects;

    public StaticGameMap() {
    }

    // Loads Game Map into the mapObjects
    public void loadStaticMap(String filePath){
        Properties prop = new Properties();
        int[][] intArray;
        int maxRow = 0, maxCol = 0;

        try (InputStream input = new FileInputStream(filePath)) {
            prop.load(input);

            // First pass to find the size of the array
            for (String key : prop.stringPropertyNames()) {
                String[] parts = key.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                maxRow = Math.max(maxRow, row);
                maxCol = Math.max(maxCol, col);
            }

            intArray = new int[maxRow + 1][maxCol + 1]; // +1 because arrays are 0-indexed

            // Second pass to fill the array
            for (String key : prop.stringPropertyNames()) {
                String[] parts = key.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                intArray[row][col] = Integer.parseInt(prop.getProperty(key));
            }
            // Example to print the array
            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < intArray[i].length; j++) {
                    System.out.print(intArray[i][j] + " ");
                }
                System.out.println();
            }

            mapObjects = new MapObject[maxRow + 1][maxCol + 1];

            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < intArray[i].length; j++) {
                    if (intArray[i][j] == 0) {
                        mapObjects[i][j] = new Wall();
                    } else {
                        mapObjects[i][j] = new Path();
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
