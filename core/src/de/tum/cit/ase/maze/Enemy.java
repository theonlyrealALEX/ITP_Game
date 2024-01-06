package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Enemy extends MapObject {
    public int tileX;
    public int tileY;
    private float currentWindowX, currentWindowY;
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterLeftAnimation;
    private TextureRegion characterStandingUpTexture;
    private TextureRegion characterStandingDownTexture;
    private TextureRegion characterStandingLeftTexture;
    private TextureRegion characterStandingRightTexture;
    private MapObject currentTile;
    private Direction direction;

    public Enemy(int tileX, int tileY) {
        loadCharacterAnimations();
        direction = Direction.DOWN;
        this.tileX = tileX;
        this.tileY = tileY;

    }

    public TextureRegion getCharacterStandingUpTexture() {
        return characterStandingUpTexture;
    }

    public TextureRegion getCharacterStandingDownTexture() {
        return characterStandingDownTexture;
    }

    public TextureRegion getCharacterStandingLeftTexture() {
        return characterStandingLeftTexture;
    }

    public TextureRegion getCharacterStandingRightTexture() {
        return characterStandingRightTexture;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public float getCurrentWindowX() {
        return currentWindowX;
    }

    public void setCurrentWindowX(float currentWindowX) {
        this.currentWindowX = currentWindowX;
    }

    public float getCurrentWindowY() {
        return currentWindowY;
    }

    public void setCurrentWindowY(float currentWindowY) {
        this.currentWindowY = currentWindowY;
    }

    private void loadCharacterAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 3;

        characterDownAnimation = createAnimation(walkSheet, 144, 64, frameWidth, frameHeight, animationFrames);
        characterRightAnimation = createAnimation(walkSheet, 144, 96, frameWidth, frameHeight, animationFrames);
        characterUpAnimation = createAnimation(walkSheet, 144, 112, frameWidth, frameHeight, animationFrames);
        characterLeftAnimation = createAnimation(walkSheet, 144, 80, frameWidth, frameHeight, animationFrames);

        characterStandingDownTexture = new TextureRegion(walkSheet, 160, 64, frameWidth, frameHeight);
        characterStandingRightTexture = new TextureRegion(walkSheet, 160, 96, frameWidth, frameHeight);
        characterStandingUpTexture = new TextureRegion(walkSheet, 160, 112, frameWidth, frameHeight);
        characterStandingLeftTexture = new TextureRegion(walkSheet, 160, 80, frameWidth, frameHeight);
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * frameWidth, startY, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames);
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int startX, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, (i * frameWidth) + startX, startY, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames);
    }

    // Getters for each animation
    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }

    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public void move(float speed) {
        switch (this.direction) {
            case UP:
                this.currentWindowY += speed;
                break;
            case DOWN:
                this.currentWindowY -= speed;
                break;
            case LEFT:
                this.currentWindowX -= speed;
                break;
            case RIGHT:
                this.currentWindowX += speed;
                break;
            default:
                // No movement for standing directions
                break;
        }
    }


    public MapObject getCurrentTile() {
        return currentTile;
    }


    public void setCurrentTileFromCoords(GameMap gameMap, float tileSize) {
        int i = (int) ((getCurrentWindowX() + 32) / tileSize);
        int j = (int) ((getCurrentWindowY() + 48) / tileSize);
        currentTile = gameMap.getStaticMapObjects()[j][i];
    }

    public void setWindowCordsFromTilet(float tileSize) {
        this.currentWindowX = this.tileX * tileSize;
        this.currentWindowY = this.tileY * tileSize;
    }

    @Override
    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
        //TODO
    }
}


