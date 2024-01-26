package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * The type Map object.
 */
public abstract class MapObject {
    /**
     * Render.
     *
     * @param spriteBatch the sprite batch
     * @param x           the x
     * @param y           the y
     * @param tileSize    the tile size
     */
    public abstract void render(SpriteBatch spriteBatch, float x, float y, float tileSize);
}
