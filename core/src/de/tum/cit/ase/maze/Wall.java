package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends MapObject {
    private static final Texture tilesTexture = new Texture(Gdx.files.internal("basictiles.png"));
    private static final TextureRegion wallTextureRegion = new TextureRegion(tilesTexture, 96, 0, 16, 16);

    @Override
    public void render(SpriteBatch spriteBatch, float x, float y) {
        spriteBatch.draw(wallTextureRegion, x, y);
    }
}
