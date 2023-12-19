package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Player {
    private float currentWindowX, currentWindowY;
    private Animation<TextureRegion> characterDownAnimation;
    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterLeftAnimation;

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

    private TextureRegion characterStandingUpTexture;
    private TextureRegion characterStandingDownTexture;
    private TextureRegion characterStandingLeftTexture;
    private TextureRegion characterStandingRightTexture;
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Direction direction;

    public Player() {
        loadCharacterAnimations();
        direction = Direction.STANDINGUP;
    }

    public float getCurrentWindowX() {
        return currentWindowX;
    }

    public float getCurrentWindowY() {
        return currentWindowY;
    }

    public void setCurrentWindowX(float currentWindowX) {
        this.currentWindowX = currentWindowX;
    }

    public void setCurrentWindowY(float currentWindowY) {
        this.currentWindowY = currentWindowY;
    }

    private void loadCharacterAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));
        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;

        characterDownAnimation = createAnimation(walkSheet, 0, frameWidth, frameHeight, animationFrames);
        characterRightAnimation = createAnimation(walkSheet, 32, frameWidth, frameHeight, animationFrames);
        characterUpAnimation = createAnimation(walkSheet, 64, frameWidth, frameHeight, animationFrames);
        characterLeftAnimation = createAnimation(walkSheet, 96, frameWidth, frameHeight, animationFrames);

        characterStandingDownTexture = new TextureRegion(walkSheet, 0, 0, frameWidth, frameHeight);
        characterStandingRightTexture = new TextureRegion(walkSheet, 0, 32, frameWidth, frameHeight);
        characterStandingUpTexture = new TextureRegion(walkSheet, 0, 64, frameWidth, frameHeight);
        characterStandingLeftTexture = new TextureRegion(walkSheet, 0, 96, frameWidth, frameHeight);
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * frameWidth, startY, frameWidth, frameHeight));
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
                this.currentWindowY += speed ;
                break;
            case DOWN:
                this.currentWindowY -= speed ;
                break;
            case LEFT:
                this.currentWindowX -= speed ;
                break;
            case RIGHT:
                this.currentWindowX += speed;
                break;
            default:
                // No movement for standing directions
                break;
        }
    }


}
