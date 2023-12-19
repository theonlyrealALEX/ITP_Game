package de.tum.cit.ase.maze;

public class GameEngine {
    private GameMap staticGameMap;

    public Player getPlayer() {
        return player;
    }

    private Player player;


    public GameEngine() {
        System.out.println("Loading Game Engine");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        player = new Player();
        staticGameMap = new GameMap();
        loadMap();
    }

    // Needs to be changed later with Map-Selection Menu
    private void loadMap(){
        staticGameMap.loadMap("maps/level-1.properties");
    }

    public GameMap getStaticGameMap() {
        return staticGameMap;
    }
}
