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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * The PauseMenuScreen class is responsible for displaying the pause menu in the middle of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class VictoryScreen implements Screen {

    private final Stage stage;

    private Music victoryMusic;
    private Music victoryMusic2;
    /**
     * Constructor for PauseMenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public VictoryScreen(MazeRunnerGame game) {

        this.victoryMusic = Gdx.audio.newMusic(Gdx.files.internal("victory2.wav"));
        victoryMusic.setVolume(2.0f);
        victoryMusic.setLooping(false);
        victoryMusic.play();




        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage


        // Create a Label with the victory text
        Label victory = new Label("VICTORY!", game.getSkin(), "title");
        victory.setColor(Color.YELLOW);
        // Set up actions to make the text move and change color
        victory.addAction(Actions.parallel(
                Actions.moveBy(200, 0, 1.0f), // Move right
                Actions.color(new Color(1, 0.5f, 0, 1), 1.0f) // Change to orange

        ));
        victory.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.parallel(
                                        Actions.moveBy(-400, 0, 2.0f), // Move left
                                        Actions.color(Color.YELLOW, 1.0f)// Change back to yellow

                                ),
                                Actions.parallel(
                                        Actions.moveBy(400, 0, 2.0f), // Move left
                                        Actions.color(new Color(1, 0.5f, 0, 1), 1.0f)// Change back to yellow
                                )
                        )
                )
        );

        table.add(victory).padBottom(100).row();
        stage.addActor(table); // Add the table to the stage


        /*table.add(victory).padBottom(100).row();

        // Set up actions to make the text move
        victory.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(200, 0, 2.0f) // Move right
                        ,Actions.moveBy(-400, 0, 2.0f) // Move left
        )));*/



// Add a label as a title
        table.add(new Label("You successfully exit the maze", game.getSkin(), "title")).padBottom(80).row();

        TextButton continueButton = new TextButton("Next Level", game.getSkin());
        table.add(continueButton).width(300).row();
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                //game.resumeGame();

            }
        });

        TextButton startNewGameButton = new TextButton("Play Again", game.getSkin());
        table.add(startNewGameButton).width(300).row();
        startNewGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
                buttonStartNewGameFadeAway(startNewGameButton, game);
                /*game.dispose();
                game.create();
                game.goToGame();*/
                // Change to the game screen when button is pressed
            }
        });

        TextButton exitButton = new TextButton("Back to Menu", game.getSkin());
        table.add(exitButton).width(300).row();
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound();
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
                /*game.dispose();
                game.create();
                game.goToMenu();*/
            }
        });





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

    /**
     * Sound effect when buttons clicked.
     */
    public void clickSound(){
        Music clickMusic = Gdx.audio.newMusic(Gdx.files.internal("click_sound.mp3"));
        clickMusic.setVolume(2.5f);
        clickMusic.setLooping(false);
        clickMusic.play();
    }

    /**
     * Fade away animation for Start New Game button.
     */

    public void buttonStartNewGameFadeAway(TextButton textButton,MazeRunnerGame game){
        textButton.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),// Duration of the fade-out effect
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        // Dispose of the current game and create a new one
                        game.dispose();
                        game.create();
                        // You can add more animation effects here if needed

                        // Switch to the game screen
                        game.goToGame();
                    }
                })
        ));
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


