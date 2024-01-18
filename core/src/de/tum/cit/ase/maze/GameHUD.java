package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.Screen;
public class GameHUD extends Stage implements Screen{

    private SpriteBatch batch;
    private float elapsedTime = 2f;  // Initialize here
    private boolean key;
    private Integer lives;

    private Label keyLabel;
    private Label livesLabel;

    private OrthographicCamera HUDcam = new OrthographicCamera();


    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }

    public GameHUD(MazeRunnerGame game) {
        batch = new SpriteBatch();
        lives = 3;
        key = false;

        Table table = new Table();
        table.top().setFillParent(true);

        //Create labels
        keyLabel = new Label(key ? "Key Collected!" : "Key Not Collected", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label(String.format("%01d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        // Create animation
        Image heartImage = createHeartAnimation();
        Image keyImage = createKeyAnimation();


        table.add(heartImage).center().padTop(10);
        // Add livesLabel and expand it
        table.add(livesLabel).padTop(13).row();
        // Add keyLabel and expand it
        table.add(keyImage).center().padTop(10);
        table.add(keyLabel).padTop(13).padLeft(10);



        addActor(table);

        HUDcam.setToOrtho(false);
        HUDcam.update();
    }
    private Image createHeartAnimation() {
        Array<TextureRegion> heartFrames = new Array<>();
        Texture heartSheet = new Texture(Gdx.files.internal("objects.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFramesRow = 4;

        for (int i = 0; i < animationFramesRow; i++) {
            heartFrames.add(new TextureRegion(heartSheet, i * frameWidth, 48, frameWidth, frameHeight));

        }

        Image heartImage = new Image(heartFrames.get(0));
        heartImage.setOrigin(heartImage.getWidth() / 2, heartImage.getHeight() / 2);
        //heartImage.setSize(100, 100);
        heartImage.setScale(2f);

        var heartAnimation = Actions.forever(
                Actions.sequence(
                        Actions.run(() -> {
                            int frameIndex = (int) (elapsedTime / 0.03333f) % heartFrames.size;
                            heartImage.setDrawable(new TextureRegionDrawable(heartFrames.get(frameIndex)));
                            elapsedTime += Gdx.graphics.getDeltaTime();
                        }),
                        Actions.delay(0.1f)
                )
        );

        heartImage.addAction(heartAnimation);
        return heartImage;
    }

    private Image createKeyAnimation() {
        Array<TextureRegion> keyFrames = new Array<>();
        Texture keySheet = new Texture(Gdx.files.internal("things.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFramesRow = 3;

        for (int i = 0; i < animationFramesRow; i++) {
            keyFrames.add(new TextureRegion(keySheet, i * frameWidth, 96, frameWidth, frameHeight));

        }

        Image keyImage = new Image(keyFrames.get(0));
        keyImage.setOrigin(keyImage.getWidth() / 2, keyImage.getHeight() / 2);
        //heartImage.setSize(100, 100);
        keyImage.setScale(2f);

        var keyAnimation = Actions.forever(
                Actions.sequence(
                        Actions.run(() -> {
                            int frameIndex = (int) (elapsedTime / 0.03333f) % keyFrames.size;
                            keyImage.setDrawable(new TextureRegionDrawable(keyFrames.get(frameIndex)));
                            elapsedTime += Gdx.graphics.getDeltaTime();
                        }),
                        Actions.delay(0.1f)
                )
        );

        keyImage.addAction(keyAnimation);
        return keyImage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        //act(Gdx.graphics.getDeltaTime());
        draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }
}
