package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The type Entry point.
 */
public class EntryPoint extends MapObject {

    private static final Texture tilesTexture = new Texture(Gdx.files.internal("basictiles.png"));
    private static final TextureRegion pathTextureRegion = new TextureRegion(tilesTexture, 0, 96, 16, 16);
    private int x;
    private int y;

    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
        spriteBatch.draw(pathTextureRegion, x, y, tileSize, tileSize);
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param j the j
     */
    public void setX(int j) {
        this.x = j;
    }
}
