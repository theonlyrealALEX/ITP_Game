package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

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
    private Personality personality;
    private int movementSmoothing = 0;

    /**
     * @param tileX
     * @param tileY
     */
    public Enemy(int tileX, int tileY) {
        loadCharacterAnimations();
        direction = Direction.DOWN;
        this.tileX = tileX;
        this.tileY = tileY;
        this.personality = getRandomPersonality();
        //System.out.println("Created Enemy with Personality: " + getPersonality());

    }

    /**
     * Creates new Random Enemy Personality
     *
     * @return
     */
    public Personality getRandomPersonality() {
        Random random = new Random();
        Personality[] personalities = Personality.values();
        int randomIndex = random.nextInt(personalities.length);
        return personalities[randomIndex];
    }

    /**
     * Used for Rotating the Enemy when he is facing an Obstacle
     *
     * @return
     */
    public Rotation getRandomRotation() {
        Random random = new Random();
        Rotation[] rotations = Rotation.values();
        int randomIndex = random.nextInt(rotations.length);
        return rotations[randomIndex];
    }

    /**
     * @return
     */
    public TextureRegion getCharacterStandingUpTexture() {
        return characterStandingUpTexture;
    }

    /**
     * @return
     */
    public TextureRegion getCharacterStandingDownTexture() {
        return characterStandingDownTexture;
    }

    /**
     * @return
     */
    public TextureRegion getCharacterStandingLeftTexture() {
        return characterStandingLeftTexture;
    }

    /**
     * @return
     */
    public TextureRegion getCharacterStandingRightTexture() {
        return characterStandingRightTexture;
    }

    /**
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return
     */
    public int getTileX() {
        return tileX;
    }

    /**
     * @param tileX
     */
    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    /**
     * @return
     */
    public int getTileY() {
        return tileY;
    }

    /**
     * @param tileY
     */
    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    /**
     * @return
     */
    public float getCurrentWindowX() {
        return currentWindowX;
    }

    /**
     * @param currentWindowX
     */
    public void setCurrentWindowX(float currentWindowX) {
        this.currentWindowX = currentWindowX;
    }

    /**
     * @return
     */
    public float getCurrentWindowY() {
        return currentWindowY;
    }

    /**
     * @param currentWindowY
     */
    public void setCurrentWindowY(float currentWindowY) {
        this.currentWindowY = currentWindowY;
    }

    /**
     *
     */
    private void loadCharacterAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("mobs.png"));
        int frameWidth = 16;
        int frameHeight = 16;
        int animationFrames = 3;

        characterDownAnimation = createAnimation(walkSheet, 48, 64, frameWidth, frameHeight, animationFrames);
        characterRightAnimation = createAnimation(walkSheet, 48, 96, frameWidth, frameHeight, animationFrames);
        characterUpAnimation = createAnimation(walkSheet, 48, 112, frameWidth, frameHeight, animationFrames);
        characterLeftAnimation = createAnimation(walkSheet, 48, 80, frameWidth, frameHeight, animationFrames);

        characterStandingDownTexture = new TextureRegion(walkSheet, 64, 64, frameWidth, frameHeight);
        characterStandingRightTexture = new TextureRegion(walkSheet, 64, 96, frameWidth, frameHeight);
        characterStandingUpTexture = new TextureRegion(walkSheet, 64, 112, frameWidth, frameHeight);
        characterStandingLeftTexture = new TextureRegion(walkSheet, 64, 80, frameWidth, frameHeight);
    }

    /**
     * @param sheet
     * @param startY
     * @param frameWidth
     * @param frameHeight
     * @param frameCount
     * @return
     */
    private Animation<TextureRegion> createAnimation(Texture sheet, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, i * frameWidth, startY, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames);
    }

    /**
     * Putting together the animation from the Textures
     *
     * @param sheet
     * @param startX
     * @param startY
     * @param frameWidth
     * @param frameHeight
     * @param frameCount
     * @return
     */
    private Animation<TextureRegion> createAnimation(Texture sheet, int startX, int startY, int frameWidth, int frameHeight, int frameCount) {
        Array<TextureRegion> frames = new Array<>(TextureRegion.class);
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(sheet, (i * frameWidth) + startX, startY, frameWidth, frameHeight));
        }
        return new Animation<>(0.1f, frames);
    }

    /**
     * @return
     */
    public Animation<TextureRegion> getCharacterDownAnimation() {
        return characterDownAnimation;
    }

    /**
     * @return
     */
    public Animation<TextureRegion> getCharacterUpAnimation() {
        return characterUpAnimation;
    }

    /**
     * @return
     */
    public Animation<TextureRegion> getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    /**
     * @return
     */
    public Animation<TextureRegion> getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    /**
     * Moves the Enemy in the direction the he already has by the speed value. Speed is set in the GameScreen class.
     *
     * @param speed
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
     * @return
     */
    public MapObject getCurrentTile() {
        return currentTile;
    }

    /**
     * Set currentTile to the tile that is underneath the enemy
     *
     * @param gameMap
     * @param tileSize
     */
    public void setCurrentTileFromCoords(GameMap gameMap, float tileSize) {
        int i = (int) ((getCurrentWindowX() + 32) / tileSize);
        int j = (int) ((getCurrentWindowY() + 48) / tileSize);
        currentTile = gameMap.getStaticMapObjects()[j][i];
    }

    /**
     * @param tileSize
     */
    public void setWindowCordsFromTilet(float tileSize) {
        this.currentWindowX = this.tileX * tileSize;
        this.currentWindowY = this.tileY * tileSize;
    }

    /**
     * @param spriteBatch
     * @param x
     * @param y
     * @param tileSize
     */
    @Override
    public void render(SpriteBatch spriteBatch, float x, float y, float tileSize) {
    }

    /**
     * @return
     */
    public Personality getPersonality() {
        return personality;
    }

    /**
     * @param personality
     */
    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    /**
     * @return
     */
    public int getMovementSmoothing() {
        return movementSmoothing;
    }

    /**
     * Used for smoothing the movement at an obstacle
     */
    public void decrmenentMovementSmoothing() {
        if (movementSmoothing > 0) {
            movementSmoothing--;
        } else {
            movementSmoothing = 60;
        }

    }

    /**
     * change direction, used when at an obstacle
     */
    public void rotateDirection() {
        if (getRandomRotation() == Rotation.RIGHT) {
            switch (getDirection()) {
                case LEFT -> setDirection(Direction.UP);
                case RIGHT -> setDirection(Direction.DOWN);
                case UP -> setDirection(Direction.RIGHT);
                case DOWN -> setDirection(Direction.LEFT);
            }
        } else {
            switch (getDirection()) {
                case LEFT -> setDirection(Direction.DOWN);
                case RIGHT -> setDirection(Direction.UP);
                case UP -> setDirection(Direction.LEFT);
                case DOWN -> setDirection(Direction.RIGHT);
            }
        }
    }
}


