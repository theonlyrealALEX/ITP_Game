package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;



public class GameHUD extends Stage {

    public Stage stage;
    private GameScreen gameScreen;
    private Viewport viewport;
    private boolean key;
    private Integer lives;

    Label keyLabel;
    Label livesLabel;

    private OrthographicCamera HUDcam = new OrthographicCamera();

    @Override
    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public GameHUD(SpriteBatch spriteBatch, MazeRunnerGame game) {
        lives =3;
        key=false;
        viewport =new FitViewport(getCamera().position.x,getCamera().position.x, HUDcam);

        stage = new Stage(viewport,spriteBatch);
        Table table = new Table();
        table.top(); //table is at the top of stage
        table.setFillParent(true); //table is the size of stage

        keyLabel = new Label(key ? "Collected" : "Not Collected", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label(String.format("%01d",lives),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        table.add(keyLabel).expandX().padTop(10);
        table.row();
        table.add(livesLabel).expandX().padTop(10);
        stage.addActor(table);

    }

}