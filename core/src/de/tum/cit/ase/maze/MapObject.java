package de.tum.cit.ase.maze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class MapObject {

    public abstract void render(SpriteBatch spriteBatch, float x, float y, float tileSize);
}
