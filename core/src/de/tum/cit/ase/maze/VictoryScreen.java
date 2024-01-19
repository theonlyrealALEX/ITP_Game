package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
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
 * The VictoryScreen class is responsible for displaying the victory screen in the game.
 * It extends the LibGDX Screen class and sets up the UI components for the screen.
 */
public class VictoryScreen implements Screen {

    private final Stage stage;
    private Music victoryMusic;
    private float elapsedTime = 2f;


    /**
     * Constructor for VictoryScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public VictoryScreen(MazeRunnerGame game) {
        game.getGameScreen().getGameMusic().dispose();
        this.victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("victory2.wav"));
        victoryMusic.setVolume(2.0f);
        victoryMusic.setLooping(false);
        victoryMusic.play();

        var camera = new OrthographicCamera();
        camera.zoom = 1.5f;

        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.setFillParent(true);

        Label victory = new Label("VICTORY!", game.getSkin(), "title");
        victory.setColor(Color.YELLOW);
        victory.addAction(Actions.parallel(
                Actions.moveBy(200, 0, 1.0f),
                Actions.color(new Color(1, 0.5f, 0, 1), 1.0f)
        ));
        victory.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.parallel(
                                        Actions.moveBy(-400, 0, 2.0f),
                                        Actions.color(Color.YELLOW, 1.0f)
                                ),
                                Actions.parallel(
                                        Actions.moveBy(400, 0, 2.0f),
                                        Actions.color(new Color(1, 0.5f, 0, 1), 1.0f)
                                )
                        )
                )
        );

        table.add(victory).padBottom(100).row();
        stage.addActor(table);

        // Create an animation
        Image winImage = createWinAnimation();
        table.add(winImage).padBottom(100).row();

        table.add(new Label("You successfully exit the maze", game.getSkin(), "title")).padBottom(80).row();

        TextButton continueButton = new TextButton("Choose New Map", game.getSkin());
        table.add(continueButton).width(300).row();
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                game.goToSelect();
            }
        });

        TextButton startNewGameButton = new TextButton("Play Again", game.getSkin());
        table.add(startNewGameButton).width(300).row();
        startNewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                clickSound();
                buttonStartNewGameFadeAway(startNewGameButton, game);
            }
        });

        /*TextButton exitButton = new TextButton("Back to Menu", game.getSkin());
        table.add(exitButton).width(300).row();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                exitButtonFadeAway(exitButton, game);
            }
        });*/
    }

    private Image createWinAnimation() {
        Array<TextureRegion> winFrames = new Array<>();
        Texture winSheet = new Texture(Gdx.files.internal("Character.png"));
        int frameWidth = 32;
        int frameHeight = 32;
        int animationFramesRow = 4;

        for (int i = 0; i < animationFramesRow; i++) {
            winFrames.add(new TextureRegion(winSheet, i * frameWidth, 128, frameWidth, frameHeight));

        }

        Image winImage = new Image(winFrames.get(0));
        winImage.setOrigin(winImage.getWidth() / 2, winImage.getHeight() / 2);
        winImage.setSize(100, 100);
        winImage.setScale(8f);

        var skullAnimation = Actions.forever(
                Actions.sequence(
                        Actions.run(() -> {
                            int frameIndex = (int) (elapsedTime / 0.03333f) % winFrames.size;
                            winImage.setDrawable(new TextureRegionDrawable(winFrames.get(frameIndex)));
                            elapsedTime += Gdx.graphics.getDeltaTime();
                        }),
                        Actions.delay(0.1f)
                )
        );

        winImage.addAction(skullAnimation);
        return winImage;
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

    private void exitButtonFadeAway(TextButton exitButton, MazeRunnerGame game) {
        exitButton.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.run(() -> {
                    game.dispose();
                    game.create();
                    game.goToMenu();
                })
        ));
    }
    private void clickSound() {
        Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
        clickMusic.setVolume(2.5f);
        clickMusic.setLooping(false);
        clickMusic.play();
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
        victoryMusic.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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

