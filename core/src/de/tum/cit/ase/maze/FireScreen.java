package de.tum.cit.ase.maze;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;



public class FireScreen implements Screen{
    private static final float FRAME_TIME = 1/15f;
    private float elapse_time;
    private Animation<TextureRegion> fire;
    private SpriteBatch batch;



    @Override
    public void show() {
        Texture fireSheet = new Texture(Gdx.files.internal("things.png"));
        int frameWidth = 16; // 3 frames per row
        int frameHeight = 16; // 3 rows
        int animationFrames = 3;

        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < animationFrames; i++) {
            frames.add(new TextureRegion(fireSheet, 144+i * frameWidth, 64, frameWidth, frameHeight));
        }
        fire = new Animation<>(FRAME_TIME,frames);
        fire.setFrameDuration(FRAME_TIME);

        batch=new SpriteBatch();


    }


    @Override
    public void render(float delta) {
        elapse_time+=delta;
        TextureRegion currentFrame= fire.getKeyFrame(elapse_time,true);

        //Gdx.gl.glClearColor(0.0f,0,0.0f,1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(currentFrame,500,500,100,100); //change the size and location of the fire
        batch.end();

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

    }
}
