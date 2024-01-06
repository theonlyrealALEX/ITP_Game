package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Trap extends MapObject {
    private static final Texture backroundTexture = new Texture(Gdx.files.internal("basictiles.png"));
    private static final Texture overlayTexture = new Texture(Gdx.files.internal("basictiles.png"));
    // Assuming the y-coordinate for the second row is 16, since each tile is 16 pixels in height
    private static final TextureRegion pathBackroundRegion = new TextureRegion(backroundTexture, 16, 0, 16, 16);
    private static final TextureRegion pathOverlayTextureRegion = new TextureRegion(overlayTexture, 80, 48, 16, 16);

    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
        spriteBatch.draw(pathBackroundRegion, x, y, tileSize, tileSize);
        spriteBatch.draw(pathOverlayTextureRegion, x, y, tileSize, tileSize);
    }
}
