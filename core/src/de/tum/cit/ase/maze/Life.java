package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * The type Life.
 */
public class Life extends MapObject {
    private static final Texture overlayTexture = new Texture(Gdx.files.internal("basictiles.png"));
    // Assuming the y-coordinate for the second row is 16, since each tile is 16 pixels in height
    private static final TextureRegion pathBackroundRegion = Path.getPathTextureRegion();
    private Animation<TextureRegion> animation;

    private float elapsedTime = 2f;
    private float x, y;
    private int mapX, mapY;

    /**
     * Instantiates a new Life.
     *
     * @param mapX the map x
     * @param mapY the map y
     */
    public Life(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        loadAnimations();
    }

    /**
     * Convert map to window.
     *
     * @param tileSize the tile size
     */
    public void convertMapToWindow(float tileSize) {
        this.x = mapX * tileSize;
        this.y = mapY * tileSize;
    }

    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
        spriteBatch.draw(pathBackroundRegion, x, y, tileSize, tileSize);

    }

    private void loadAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("objects.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 4;

        animation = createAnimation(walkSheet, 48, frameWidth, frameHeight, animationFrames);
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * frameWidth, startY, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames);
    }

    /**
     * Gets animation.
     *
     * @return the animation
     */
    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    /**
     * Sets animation.
     *
     * @param animation the animation
     */
    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets map x.
     *
     * @return the map x
     */
    public int getMapX() {
        return mapX;
    }

    /**
     * Sets map x.
     *
     * @param mapX the map x
     */
    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    /**
     * Gets map y.
     *
     * @return the map y
     */
    public int getMapY() {
        return mapY;
    }

    /**
     * Sets map y.
     *
     * @param mapY the map y
     */
    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
}

