package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Player {

    private Animation<TextureRegion> characterUpAnimation;
    private Animation<TextureRegion> characterRightAnimation;
    private Animation<TextureRegion> characterLeftAnimation;

    public Player() {
        loadCharacterAnimations();
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

    private float currentWindowX, currentWindowY;
    private Animation<TextureRegion> characterDownAnimation;



    private void loadCharacterAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("character.png"));
        int frameWidth = 16;
        int frameHeight = 32;
        int animationFrames = 4;

        characterDownAnimation = createAnimation(walkSheet, 0, frameWidth, frameHeight, animationFrames);
        characterRightAnimation = createAnimation(walkSheet, 32, frameWidth, frameHeight, animationFrames);
        characterUpAnimation = createAnimation(walkSheet, 64, frameWidth, frameHeight, animationFrames);
        characterLeftAnimation = createAnimation(walkSheet, 96, frameWidth, frameHeight, animationFrames);
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

}
