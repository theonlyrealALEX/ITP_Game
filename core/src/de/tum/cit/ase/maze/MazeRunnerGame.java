package de.tum.cit.ase.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import games.spooky.gdx.nativefilechooser.NativeFileChooser;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.files.FileHandle;

/**
 * The MazeRunnerGame class represents the core of the Maze Runner game.
 * It manages the screens and global resources like SpriteBatch and Skin.
 */
public class MazeRunnerGame extends Game {
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
    public String selectedMap;

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public Music getBackGroundMusic() {
        return backGroundMusic;
    }

    public void setBackGroundMusic(Music backGroundMusic) {
        this.backGroundMusic = backGroundMusic;
    }

    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;

    // Getter methods
    public Skin getSkin() {
        return skin;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }


    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setPauseMenuScreen(PauseMenuScreen pauseMenuScreen) {
        this.pauseMenuScreen = pauseMenuScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public Music getBackgroundMusic() {
        return backGroundMusic;
    }

    public void setCharacterDownAnimation(Animation<TextureRegion> characterDownAnimation) {
        this.characterDownAnimation = characterDownAnimation;
    }

    public void setSelectScreen(SelectScreen selectScreen) {
        this.selectScreen = selectScreen;
    }


    /**
     * Constructor for MazeRunnerGame.
     *
     * @param fileChooser The file chooser for the game, typically used in desktop environment.
     */
    public MazeRunnerGame(NativeFileChooser fileChooser) {
        super();
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

    public void goToPauseMenu(){

        this.setScreen(new PauseMenuScreen(this)); // Set the current screen to MenuScreen
        this.pause();
    }


    /**
     * Switches to the Victory or Game Over screen when win or lose the game.
     */

    public void goToVictoryScreen(){
        //this.setScreen(new FireScreen());  // Testing
        this.setScreen(new VictoryScreen(this)); // Set the current screen to MenuScreen

        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }

    public void goToGameOverScreen(){
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
        this.gameEngine=new GameEngine(this);
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen

        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
    }


    public void continueGame(){
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
    public void closeGame(){
        Gdx.app.exit();
    }
}
