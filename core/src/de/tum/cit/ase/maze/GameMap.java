package de.tum.cit.ase.maze;
import java.util.Arrays;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class GameMap {
    private MapObject[][] staticMapObjects;

    public GameMap() {
    }

    // Loads Game Map into the staticMapObjects
    public void loadMap(String filePath){
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

            // Initialize the intArray with -1
            intArray = new int[maxRow + 1][maxCol + 1]; // +1 because arrays are 0-indexed
            for (int[] row : intArray) {
                Arrays.fill(row, -1);
            }

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

            staticMapObjects = new MapObject[maxRow + 1][maxCol + 1];

            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < intArray[i].length; j++) {
                    if (intArray[i][j] == 0) {
                        staticMapObjects[i][j] = new Wall();
                    } else {
                        staticMapObjects[i][j] = new Path();
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public MapObject[][] getStaticMapObjects() {
        return staticMapObjects;
    }

    public float getMapWidth(){
        return staticMapObjects[0].length;
    }
    public float getMapHeight(){
        return staticMapObjects.length;
    }

    public MapObject getTile(float x, float y, float tileSize){
        int i = (int) (x / tileSize);
        int j = (int) (y / tileSize);

        if (i >= 0 && i < staticMapObjects.length && j >= 0 && j < staticMapObjects[0].length) {
            System.out.println("Map Coordinates at tile x/y: "+i+" "+j);
            return staticMapObjects[i][j];
        } else {
            return null; // Or handle the out-of-bounds scenario differently
        }
    }

}
