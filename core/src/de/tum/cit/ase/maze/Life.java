package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Life extends MapObject {
    private static final Texture overlayTexture = new Texture(Gdx.files.internal("basictiles.png"));
    // Assuming the y-coordinate for the second row is 16, since each tile is 16 pixels in height
    private static final TextureRegion pathBackroundRegion = Path.getPathTextureRegion();
    private Animation<TextureRegion> animation;

    private float elapsedTime = 2f;
    private float x, y;
    private int mapX, mapY;

    public Life(int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        loadAnimations();
    }

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

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
}

