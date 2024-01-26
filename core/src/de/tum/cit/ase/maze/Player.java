package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


/**
 * The type Player.
 */
public class Player {
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

    /**
     * Instantiates a new Player.
     */
    public Player() {
        loadCharacterAnimations();
        direction = Direction.STANDINGUP;
    }

    /**
     * Gets character standing up texture.
     *
     * @return the character standing up texture
     */
    public TextureRegion getCharacterStandingUpTexture() {
        return characterStandingUpTexture;
    }

    /**
     * Gets character standing down texture.
     *
     * @return the character standing down texture
     */
    public TextureRegion getCharacterStandingDownTexture() {
        return characterStandingDownTexture;
    }

    /**
     * Gets character standing left texture.
     *
     * @return the character standing left texture
     */
    public TextureRegion getCharacterStandingLeftTexture() {
        return characterStandingLeftTexture;
    }

    /**
     * Gets character standing right texture.
     *
     * @return the character standing right texture
     */
    public TextureRegion getCharacterStandingRightTexture() {
        return characterStandingRightTexture;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets current window x.
     *
     * @return the current window x
     */
    public float getCurrentWindowX() {
        return currentWindowX;
    }

    /**
     * Sets current window x.
     *
     * @param currentWindowX the current window x
     */
    public void setCurrentWindowX(float currentWindowX) {
        this.currentWindowX = currentWindowX;
    }

    /**
     * Gets current window y.
     *
     * @return the current window y
     */
    public float getCurrentWindowY() {
        return currentWindowY;
    }

    /**
     * Sets current window y.
     *
     * @param currentWindowY the current window y
     */
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

    /**
     * Gets character down animation.
     *
     * @return the character down animation
     */
// Getters for each animation
    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }

    /**
     * Gets character up animation.
     *
     * @return the character up animation
     */
    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    /**
     * Gets character right animation.
     *
     * @return the character right animation
     */
    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    /**
     * Gets character left animation.
     *
     * @return the character left animation
     */
    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    /**
     * Move.
     *
     * @param speed the speed
     */
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


    /**
     * Gets current tile.
     *
     * @return the current tile
     */
    public MapObject getCurrentTile() {
        return currentTile;
    }


    /**
     * Sets current tile from coords.
     *
     * @param gameMap  the game map
     * @param tileSize the tile size
     */
    public void setCurrentTileFromCoords(GameMap gameMap, float tileSize) {
        int i = (int) ((getCurrentWindowX() + 32) / tileSize);
        int j = (int) ((getCurrentWindowY() + 48) / tileSize);
        currentTile = gameMap.getStaticMapObjects()[j][i];
    }

    /**
     * Gets offset window x.
     *
     * @param tileSize    the tile size
     * @param personality the personality
     * @return the offset window x
     */
    public float getOffsetWindowX(float tileSize, Personality personality) {
        switch (getDirection()) {
            case RIGHT:
            case STANDINGRIGHT:
                if (personality == Personality.INFRONT) {
                    return currentWindowX + 2 * tileSize;
                } else if (personality == Personality.FOLLOWER) {
                    return currentWindowX - 1 * tileSize;
                } else {
                    return getCurrentWindowX();
                }
            case STANDINGLEFT:
            case LEFT:
                if (personality == Personality.INFRONT) {
                    return currentWindowX - 2 * tileSize;
                } else if (personality == Personality.FOLLOWER) {
                    return currentWindowX + 1 * tileSize;
                } else {
                    return getCurrentWindowX();
                }
        }
        return getCurrentWindowX();
    }

    /**
     * Gets offset window y.
     *
     * @param tileSize    the tile size
     * @param personality the personality
     * @return the offset window y
     */
    public float getOffsetWindowY(float tileSize, Personality personality) {
        switch (getDirection()) {
            case UP:
            case STANDINGUP:
                if (personality == Personality.INFRONT) {
                    return currentWindowY + 2 * tileSize;
                } else if (personality == Personality.FOLLOWER) {
                    return currentWindowY - 1 * tileSize;
                } else {
                    return getCurrentWindowY();
                }
            case DOWN:
            case STANDINGDOWN:
                if (personality == Personality.INFRONT) {
                    return currentWindowY - 2 * tileSize;
                } else if (personality == Personality.FOLLOWER) {
                    return currentWindowY + 1 * tileSize;
                } else {
                    return getCurrentWindowY();
                }
        }
        return getCurrentWindowY();
    }
}
