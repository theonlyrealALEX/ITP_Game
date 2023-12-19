package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Path extends MapObject {
    private static final Texture tilesTexture = new Texture(Gdx.files.internal("basictiles.png"));
    // Assuming the y-coordinate for the second row is 16, since each tile is 16 pixels in height
    private static final TextureRegion pathTextureRegion = new TextureRegion(tilesTexture, 64, 16, 16, 16);

    @Override
    public void render(SpriteBatch spriteBatch, float x, float y) {
        spriteBatch.draw(pathTextureRegion, x, y);
    }
}
