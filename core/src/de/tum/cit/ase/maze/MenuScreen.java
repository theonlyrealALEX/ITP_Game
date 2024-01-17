package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private float elapsedTime = 2f;

    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(MazeRunnerGame game) {
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        Image fireImage = createFireAnimation();
        table.add(fireImage).padBottom(100).row();

        // Add a label as a title
        table.add(new Label("Welcome to Maze Runner Game!", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        TextButton goToGameButton = new TextButton("Go To Game", game.getSkin());
        table.add(goToGameButton).width(300).row();
        // Add sound effect to the button
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
                clickMusic.setVolume(1.5f);
                clickMusic.setLooping(false);
                clickMusic.play();
                game.goToGame();
            }
        });
        TextButton quitGameButton = new TextButton("Quit Game", game.getSkin());
        table.add(quitGameButton).width(300).row();
        quitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
                clickMusic.setVolume(1.5f);
                clickMusic.setLooping(false);
                clickMusic.play();
                game.closeGame();

            }
        });
    }

    // Create an animation for the skull
    private Image createFireAnimation() {
        Array<TextureRegion> fireFrames = new Array<>();
        Texture skullSheet = new Texture(Gdx.files.internal("things.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFramesRow = 3;

        for (int i = 0; i < animationFramesRow; i++) {
            fireFrames.add(new TextureRegion(skullSheet, 144 + i * frameWidth, 64, frameWidth, frameHeight));
        }

        Image fireImage = new Image(fireFrames.get(0));
        fireImage.setOrigin(fireImage.getWidth() / 2, fireImage.getHeight() / 2);
        fireImage.setSize(100, 100);
        fireImage.setScale(10f);

        var skullAnimation = Actions.forever(
                Actions.sequence(
                        Actions.run(() -> {
                            int frameIndex = (int) (elapsedTime / 0.03333f) % fireFrames.size;
                            fireImage.setDrawable(new TextureRegionDrawable(fireFrames.get(frameIndex)));
                            elapsedTime += Gdx.graphics.getDeltaTime();
                        }),
                        Actions.delay(0.1f)
                )
        );

        fireImage.addAction(skullAnimation);
        return fireImage;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }


}
