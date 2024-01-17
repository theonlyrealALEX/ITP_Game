package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * The PauseMenuScreen class is responsible for displaying the pause menu in the middle of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class PauseMenuScreen implements Screen {

    private final Stage stage;





    /**
     * Constructor for PauseMenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public PauseMenuScreen(MazeRunnerGame game) {

        game.setPauseMenuScreen(this);
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements


        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage



        // Add a label as a title
        table.add(new Label("Welcome to Pause Menu!", game.getSkin(), "title")).padBottom(80).row();

        TextButton continueButton = new TextButton("Continue Game", game.getSkin());
        //continueButton.setColor(Color.BLUE);
        table.add(continueButton).width(300).row();
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound(); // Sound effect
                game.continueGame();


            }
        });


        TextButton exitButton = new TextButton("Start Again", game.getSkin());
        table.add(exitButton).width(300).row();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();  // Sound effect
                exitButton.addAction(Actions.sequence(
                        Actions.fadeOut(0.5f),// Duration of the fade-out effect
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                // Dispose of the current game and create a new one
                                game.dispose();
                                game.create();
                                // You can add more animation effects here if needed

                                // Switch to the game menu
                                game.goToMenu();
                            }
                        })
                ));
            }
        });
        TextButton selectAgainButton = new TextButton("Select New Map", game.getSkin());
        table.add(selectAgainButton).width(300).row();
        selectAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();  // Sound effect
                selectAgainButton.addAction(Actions.sequence(
                        Actions.fadeOut(0.5f),// Duration of the fade-out effect
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                // Dispose of the current game and create a new one
                                game.dispose();
                                game.create();
                                // You can add more animation effects here if needed

                                // Switch to the game menu
                                game.goToSelect();
                            }
                        })
                ));
            }
        });


        TextButton quitGameButton = new TextButton("Quit Game", game.getSkin());
        table.add(quitGameButton).width(300).row();
        quitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound(); // Sound effect
                game.closeGame();

            }
        });



    }

    /**
     * Fade away animation for Start New Game button.
     */
/*
    private void buttonStartNewGameFadeAway(TextButton textButton,MazeRunnerGame game){
        textButton.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),// Duration of the fade-out effect
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        // Dispose of the current game and create a new one
                        game.dispose();
                        game.goToSelect();
                        game.create();
                        // You can add more animation effects here if needed

                        // Switch to the game screen
                        game.goToGame();
                    }
                })
        ));

    }*/


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

    /**
     * Sound effect when buttons clicked.
     */
    private void clickSound(){
        Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
        clickMusic.setVolume(2.5f);
        clickMusic.setLooping(false);
        clickMusic.play();
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    public void resume(){

    }


    @Override
    public void hide() {
    }
}


