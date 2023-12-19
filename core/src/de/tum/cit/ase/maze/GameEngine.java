package de.tum.cit.ase.maze;

public class GameEngine {
    private StaticGameMap staticGameMap;

    public GameEngine() {
        System.out.println("Loading Game Engine");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        staticGameMap = new StaticGameMap();
    loadMap();
    }

    // Needs to be changed later with Map-Selection Menu
    private void loadMap(){
        staticGameMap.loadStaticMap("maps/level-1.properties");
    }
}
