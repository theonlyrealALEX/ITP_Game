package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The type Path.
 */
public class Path extends MapObject {
    private static final Texture tilesTexture = new Texture(Gdx.files.internal("basictiles.png"));

    private static final TextureRegion pathTextureRegion = new TextureRegion(tilesTexture, 16, 0, 16, 16);

    /**
     * Gets path texture region.
     *
     * @return the path texture region
     */
    public static TextureRegion getPathTextureRegion() {
        return pathTextureRegion;
    }

    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
        spriteBatch.draw(pathTextureRegion, x, y, tileSize, tileSize);
    }
}
