package de.tum.cit.ase.maze;

public class GameEngine {
    private GameMap staticGameMap;
    private Player player;

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }



    public GameEngine(MazeRunnerGame game) {
        System.out.println("Loading Game Engine");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println("Game engine select:" + game.selectedMap);
        this.player = new Player();
        this.staticGameMap = new GameMap();
        this.staticGameMap.loadMap(game.selectedMap);
    }

    public void loadMap() {
    }

    public GameMap getStaticGameMap() {
        return this.staticGameMap;
    }
}
