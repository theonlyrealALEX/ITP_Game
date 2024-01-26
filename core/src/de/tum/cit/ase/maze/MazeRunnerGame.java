package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {
    /**
     * The Selected map.
     */
    public String selectedMap;
    // Screens
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private PauseMenuScreen pauseMenuScreen;
    private SelectScreen selectScreen;
    private GameEngine gameEngine;
    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;
    // UI Skin
    private Skin skin;
    // Music
    private Music backGroundMusic;
    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;

    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
    }

    /**
     * Gets menu screen.
     *
     * @return the menu screen
     */
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    /**
     * Gets back ground music.
     *
     * @return the back ground music
     */
    public Music getBackGroundMusic() {
        return backGroundMusic;
    }

    /**
     * Sets back ground music.
     *
     * @param backGroundMusic the back ground music
     */
    public void setBackGroundMusic(Music backGroundMusic) {
        this.backGroundMusic = backGroundMusic;
    }

    /**
     * Gets skin.
     *
     * @return the skin
     */
// Getter methods
    public Skin getSkin() {
        return skin;
    }

    /**
     * Gets sprite batch.
     *
     * @return the sprite batch
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    /**
     * Gets game engine.
     *
     * @return the game engine
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Sets pause menu screen.
     *
     * @param pauseMenuScreen the pause menu screen
     */
    public void setPauseMenuScreen(PauseMenuScreen pauseMenuScreen) {
        this.pauseMenuScreen = pauseMenuScreen;
    }

    /**
     * Gets game screen.
     *
     * @return the game screen
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Sets game screen.
     *
     * @param gameScreen the game screen
     */
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Gets background music.
     *
     * @return the background music
     */
    public Music getBackgroundMusic() {
        return backGroundMusic;
    }

    /**
     * Sets character down animation.
     *
     * @param characterDownAnimation the character down animation
     */
    public void setCharacterDownAnimation(Animation<TextureRegion> characterDownAnimation) {
        this.characterDownAnimation = characterDownAnimation;
    }

    /**
     * Sets select screen.
     *
     * @param selectScreen the select screen
     */
    public void setSelectScreen(SelectScreen selectScreen) {
        this.selectScreen = selectScreen;
    }

    /**
     * Called when the game is created. Initializes the SpriteBatch and Skin.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch(); // Create SpriteBatch
        skin = new Skin(Gdx.files.internal("craft/craftacular-ui.json")); // Load UI skin

        setBackGroundMusic(Gdx.audio.newMusic(Gdx.files.internal("MenuSound.mp3")));
        // Play some background music
        // Background sound

        // Load Engine and Map
        //gameEngine = new GameEngine();

        goToSelect(); // Navigate to the menu screen

    }


    /**
     * Switches to the select screen.
     */
    public void goToSelect() {
        this.setScreen(new SelectScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the menu screen.
     */
    public void goToMenu() {
        this.setScreen(new MenuScreen(this)); // Set the current screen to MenuScreen
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Switches to the pause menu screen when press ESC.
     */
    public void goToPauseMenu() {

        this.setScreen(new PauseMenuScreen(this)); // Set the current screen to MenuScreen
        this.pause();
    }


    /**
     * Switches to the Victory or Game Over screen when win or lose the game.
     */
    public void goToVictoryScreen() {
        //this.setScreen(new FireScreen());  // Testing
        this.setScreen(new VictoryScreen(this)); // Set the current screen to MenuScreen

        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    /**
     * Go to game over screen.
     */
    public void goToGameOverScreen() {
        //this.setScreen(new FireScreen());  // Testing

        this.setScreen(new GameOverScreen(this)); // Set the current screen to MenuScreen

        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }


    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.gameEngine = new GameEngine(this);
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen

        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
    }


    /**
     * Continue game.
     */
    public void continueGame() {
        // this.setScreen(getGameScreen());// Set the current screen to GameScreen
        this.setScreen(gameScreen);
        if (pauseMenuScreen != null) {
            pauseMenuScreen.dispose(); // Dispose the menu screen if it exists
            pauseMenuScreen = null;
        }
    }

    /**
     * Cleans up resources when the game is disposed.
     */
    @Override
    public void dispose() {
        getScreen().hide(); // Hide the current screen
        getScreen().dispose(); // Dispose the current screen
        spriteBatch.dispose(); // Dispose the spriteBatch
        skin.dispose(); // Dispose the skin
        backGroundMusic.dispose();

    }

    /**
     * Close game.
     */
    public void closeGame() {
        Gdx.app.exit();
    }
}
