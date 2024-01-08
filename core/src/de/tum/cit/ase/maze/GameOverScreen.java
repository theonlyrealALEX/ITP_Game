package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The GameOverScreen class displays the game over screen in the middle of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the screen.
 */
public class GameOverScreen implements Screen {

    private final Stage stage;
    private float elapsedTime = 2f;

    private Music gameOverMusic =  Gdx.audio.newMusic(Gdx.files.internal("gameOverMusic.mp3"));

    /**
     * Constructor for GameOverScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameOverScreen(MazeRunnerGame game) {
        // Music
        game.getBackgroundMusic().dispose();
        gameOverMusic.setVolume(0.5f);
        gameOverMusic.setLooping(true);
        gameOverMusic.play();

        // Set up Screen
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1.5f;

        Viewport viewport = new ScreenViewport(camera);
        this.stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create a Label with the lost text
        Label lost = new Label("You Lost!", game.getSkin(), "title");
        lost.setColor(Color.BLUE);

        // Set up actions to make the text move, change color, and scale
        lost.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(0.5f, 0.5f, 1, 1), 2.0f),
                                Actions.color(Color.GRAY, 2.0f),
                                Actions.color(Color.BLUE, 2.0f)
                        )
                )
        );
        table.add(lost).padBottom(150).row();

        // Create an animation for the skull
        Image skullImage = createSkullAnimation();
        table.add(skullImage).padBottom(50).row();

        // Add a label as a title
        table.add(new Label("Nice try", game.getSkin(), "title")).padBottom(80).row();

        // Add Play Again button
        TextButton startNewGameButton = new TextButton("Play Again", game.getSkin());
        table.add(startNewGameButton).width(300).row();
        startNewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                gameOverMusic.dispose();
                buttonStartNewGameFadeAway(startNewGameButton, game);
            }
        });

        // Add Back to Menu button
        TextButton exitButton = new TextButton("Back to Menu", game.getSkin());
        table.add(exitButton).width(300).row();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                gameOverMusic.dispose();
                exitButton.addAction(Actions.sequence(
                        Actions.fadeOut(0.5f),
                        Actions.run(() -> {
                            game.dispose();
                            game.create();
                            game.goToSelect();
                        })
                ));
            }
        });
    }

    // Create an animation for the skull
    private Image createSkullAnimation() {
        Array<TextureRegion> skullFrames = new Array<>();
        Texture skullSheet = new Texture(Gdx.files.internal("ExplosiveSkullPixelArt.png"));
        int frameWidth = 32;
        int frameHeight = 32;
        int animationFramesRow = 4;

        for (int i = 0; i < animationFramesRow; i++) {
            for(int j =0; j<3;j++){
                skullFrames.add(new TextureRegion(skullSheet, j * frameWidth, i*frameHeight, frameWidth, frameHeight));
            }
        }

        Image skullImage = new Image(skullFrames.get(0));
        skullImage.setOrigin(skullImage.getWidth() / 2, skullImage.getHeight() / 2);
        skullImage.setSize(100, 100);
        skullImage.setScale(10f);

        var skullAnimation = Actions.forever(
                Actions.sequence(
                        Actions.run(() -> {
                            int frameIndex = (int) (elapsedTime / 0.03333f) % skullFrames.size;
                            skullImage.setDrawable(new TextureRegionDrawable(skullFrames.get(frameIndex)));
                            elapsedTime += Gdx.graphics.getDeltaTime();
                        }),
                        Actions.delay(0.1f)
                )
        );

        skullImage.addAction(skullAnimation);
        return skullImage;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void clickSound() {
        Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
        clickMusic.setVolume(2.5f);
        clickMusic.setLooping(false);
        clickMusic.play();
    }

    private void buttonStartNewGameFadeAway(TextButton textButton, MazeRunnerGame game) {
        textButton.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.run(() -> {
                    game.dispose();
                    game.create();
                    game.goToGame();
                })
        ));
    }

    @Override
    public void pause() {
    }

    public void resume() {
    }

    @Override
    public void hide() {
    }
}
