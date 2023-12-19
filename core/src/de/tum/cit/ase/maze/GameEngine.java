package de.tum.cit.ase.maze;

public class GameEngine {
    private GameMap gameMap;

    public GameEngine() {
        System.out.println("Loading Game Engine");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        gameMap = new GameMap();
    loadMap();
    }

    // Needs to be changed later with Map-Selection Menu
    private void loadMap(){
        gameMap.loadMap("maps/level-1.properties");
    }

    public GameMap getStaticGameMap() {
return gameMap;
    }
}
