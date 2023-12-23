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
    private PauseMenuScreen pauseMenuScreen;
    private GameScreen gameScreen;

    // Sprite Batch for rendering
    private SpriteBatch spriteBatch;

    // UI Skin
    private Skin skin;



    // Character animation downwards
    private Animation<TextureRegion> characterDownAnimation;

    // Getter methods
    public Skin getSkin() {
        return skin;
    }

    public Animation<TextureRegion> getCharacterDownAnimation() {
        return gameEngine.getPlayer().getCharacterDownAnimation();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }


    public GameEngine getGameEngine() {
        return gameEngine;
    }


    private Music backgroundMusic;



    //JODIE TRYING OUT THINGS
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    //JODIE TRYING OUT THINGS



    // Engine
    private  GameEngine gameEngine;
    private static final String GAME_STATE_FILE_PATH = "game_state.json";



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

        // Play some background music
        // Background sound
        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("crystal_cave.mp3"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Load Engine and Map
        gameEngine = new GameEngine();

        goToMenu(); // Navigate to the menu screen
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
        backgroundMusic.dispose();
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
        if (gameScreen != null) {
            gameScreen.dispose(); // Dispose the game screen if it exists
            gameScreen = null;
        }
    }
    /* game resume function for continue game button WIP
    public void resumeGame() {

        this.getSkin();
        this.getSpriteBatch();
        this.getCharacterDownAnimation();
        this.setScreen(getGameScreen());
        this.render();
        // Set the current screen to GameScreen
        if ( pauseMenuScreen!= null) {
            pauseMenuScreen.dispose(); // Dispose the game screen if it exists
            pauseMenuScreen = null;
        }

    }*/


    /**
     * Switches to the game screen.
     */
    public void goToGame() {
        this.setScreen(new GameScreen(this)); // Set the current screen to GameScreen
        if (menuScreen != null) {
            menuScreen.dispose(); // Dispose the menu screen if it exists
            menuScreen = null;
        }
    }









}
