package de.tum.cit.ase.maze;

/**
 * The type Game engine.
 */
public class GameEngine {
    private GameMap staticGameMap;
    private Player player;


    /**
     * Instantiates a new Game engine.
     *
     * @param game the game
     */
    public GameEngine(MazeRunnerGame game) {
        System.out.println("Loading Game Engine");
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        System.out.println("Game engine select:" + game.selectedMap);
        this.player = new Player();
        this.staticGameMap = new GameMap();
        this.staticGameMap.loadMap(game.selectedMap);
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets static game map.
     *
     * @return the static game map
     */
    public GameMap getStaticGameMap() {
        return staticGameMap;
    }
}
