package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.Serializable;

import static de.tum.cit.ase.maze.Direction.*;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen extends ScreenAdapter implements Screen, Serializable {
    //JODIE TRY
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    static final float tileSize = 80;
    static final float enemySpeed = 1;
    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;
    int gameState;
    float centerPlayerXOffset = 32;
    float centerPlayerYOffset = 48;
    private float sinusInput = 0f;
    private float playerSpeed = 3;
    private float mapMaxX, mapMaxY;
    private GameHUD hud;


    public OrthographicCamera getCamera() {
        return camera;
    }


    private boolean gameStart, gamePaused;
    private float beforePauseCameraX, beforePauseCameraY;


    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        // Set Miscellaneous values
        this.game = game;

        //this.hud=new GameHUD(game.getSpriteBatch(),game);

        gameState = GAME_RUNNING;
        gameStart = true;
        gamePaused = false;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        //Create Player on Entry Point and set Camera to Player
        float x = this.game.getGameEngine().getStaticGameMap().getEntryPoints().get(0).getX();
        float y = this.game.getGameEngine().getStaticGameMap().getEntryPoints().get(0).getY();
        game.getGameEngine().getPlayer().setCurrentWindowX(x * tileSize + 16);
        game.getGameEngine().getPlayer().setCurrentWindowY(y * tileSize + 16);
        game.getGameEngine().getPlayer().setDirection(DOWN);
        setCameraPositonToPlayer();

        game.setGameScreen(this);
    }

    // Sets camera position on the player
    private void setCameraPositonToPlayer() {
        if (gamePaused) {
            camera.position.x = beforePauseCameraX;
            camera.position.y = beforePauseCameraY;
        } else {
            camera.position.x = game.getGameEngine().getPlayer().getCurrentWindowX();
            camera.position.y = game.getGameEngine().getPlayer().getCurrentWindowY();
        }
        camera.update();
        System.out.println("Set initial Camera to: " + camera.position.x + " " + camera.position.y);
        System.out.println("Player at " + game.getGameEngine().getPlayer().getCurrentWindowX() + " " + game.getGameEngine().getPlayer().getCurrentWindowY());
    }


    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    // Main Method for keeping the game running
    @Override
    public void render(float delta) {
        if (gameStart) {
            setCameraPositonToPlayer();
            gameStart = false;
        }

        if (gamePaused) {
            setCameraPositonToPlayer();
            gamePaused = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Music escMusic = Gdx.audio.newMusic(Gdx.files.internal("ESC_sound.mp3"));
            escMusic.setVolume(2.5f);
            escMusic.setLooping(false);
            escMusic.play();
            gamePaused = true;
            beforePauseCameraX = camera.position.x;
            beforePauseCameraY = camera.position.y;
            game.goToPauseMenu();

        }
        if (gameState == GAME_LEVEL_END) {
            Music escMusic = Gdx.audio.newMusic(Gdx.files.internal("ESC_sound.mp3"));
            escMusic.setVolume(2.5f);
            escMusic.setLooping(false);
            escMusic.play();
            game.goToVictoryScreen();

        }
        if (gameState == GAME_OVER) {
            Music escMusic = Gdx.audio.newMusic(Gdx.files.internal("ESC_sound.mp3"));
            escMusic.setVolume(2.5f);
            escMusic.setLooping(false);
            escMusic.play();
            game.goToGameOverScreen();

        }

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen

        if (gameState == GAME_RUNNING) {
            renderMap();
            Player player = game.getGameEngine().getPlayer();

            // Player Movement
            if (Gdx.input.isKeyPressed(Input.Keys.W) | Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (isPlayerAtBarrier()) {
                    player.setDirection(STANDINGUP);
                    renderStandingPlayer();
                } else {
                    player.setDirection(UP);
                    renderPlayer();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) | Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (isPlayerAtBarrier()) {
                    player.setDirection(STANDINGDOWN);
                    renderStandingPlayer();
                } else {
                    player.setDirection(DOWN);
                    renderPlayer();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) | Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (isPlayerAtBarrier()) {
                    player.setDirection(STANDINGLEFT);
                    renderStandingPlayer();
                } else {
                    player.setDirection(LEFT);
                    renderPlayer();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) | Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (isPlayerAtBarrier()) {
                    player.setDirection(STANDINGRIGHT);
                    renderStandingPlayer();
                } else {
                    player.setDirection(RIGHT);
                    renderPlayer();
                }
            } else {
                renderStandingPlayer();
            }

            // Update the camera
            if (isPlayerAtEdgeOfCamera() && !gameStart) {
                System.out.println("Player at Edge; Updating Camera");
                updateCameraPosition();
            }

            // Set up and begin drawing with the sprite batch
            game.getSpriteBatch().setProjectionMatrix(camera.combined);
            game.getSpriteBatch().begin();
            game.getSpriteBatch().end();

            camera.update(); // Update the camera

            // DO NOT DELETE, KILLS ANIMATIONS
            sinusInput += delta;

            //float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
            //float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);

            // Set up and begin drawing with the sprite batch
            game.getSpriteBatch().setProjectionMatrix(camera.combined);

            //hud.stage.draw();

            game.getSpriteBatch().begin(); // Important to call this before drawing anything

            game.getSpriteBatch().end(); // Important to call this after drawing everything

            player.setCurrentTileFromCoords(game.getGameEngine().getStaticGameMap(), tileSize);

            // Render all Enemies
            for (Enemy enemy : game.getGameEngine().getStaticGameMap().getEnemies()) {
                renderEnemy(enemy);
                if (isPlayerTouchingEnemy(enemy)) {
                    gameState = GAME_OVER;
                }
            }

            // Tile touching logic
            player.setCurrentTileFromCoords(game.getGameEngine().getStaticGameMap(), tileSize);
            if (player.getCurrentTile() instanceof Trap) {
                gameState = GAME_OVER;
            }
            if (player.getCurrentTile() instanceof Exit && game.getGameEngine().getStaticGameMap().getKeysLeft() == 0) {
                gameState = GAME_LEVEL_END;
            }

            if (player.getCurrentTile() instanceof Key) {
                game.getGameEngine().getStaticGameMap().removeKey(player.getCurrentWindowX() + centerPlayerXOffset, player.getCurrentWindowY() + centerPlayerYOffset, tileSize);
            }
        }
    }

    private boolean isPlayerTouchingEnemy(Enemy enemy) {
        // Yes that's a lot of float's, but it's easier to understand that way
        Player player = game.getGameEngine().getPlayer();
        float offsetVerticalTop = 5;
        float offsetVerticalBottom = 33;
        float offsetHorizontal = 30;
        float centerEnemyXOffset = centerPlayerXOffset;
        float centerEnemyYOffset = centerPlayerYOffset;

        float playerCenterX = player.getCurrentWindowX() + centerPlayerXOffset;
        float playerCenterY = player.getCurrentWindowY() + centerPlayerYOffset;

        float enemyCenterX = enemy.getCurrentWindowX() + centerEnemyXOffset;
        float enemyCenterY = enemy.getCurrentWindowY() + centerEnemyYOffset;

        float deltaX = playerCenterX - enemyCenterX;
        float deltaY = playerCenterY - enemyCenterY;

        if (Math.abs(deltaX) < offsetHorizontal && Math.abs(deltaY) < offsetVerticalBottom) {
            return true;
        }
        return false;
    }

    public void renderMap() {
        MapObject[][] mapObjects = game.getGameEngine().getStaticGameMap().getStaticMapObjects();
        mapMaxX = mapObjects.length * tileSize;
        mapMaxY = mapObjects[0].length * tileSize;

        game.getSpriteBatch().begin();
        for (int row = 0; row < mapObjects.length; row++) {
            for (int col = 0; col < mapObjects[row].length; col++) {
                if (mapObjects[row][col] != null) {
                    float x = col * tileSize;
                    float y = row * tileSize;
                    mapObjects[row][col].render(game.getSpriteBatch(), x, y, tileSize);
                }
            }
        }
        game.getSpriteBatch().end();
    }

    private void renderStandingEnemy(Enemy enemy) {
        float x = enemy.getCurrentWindowX();
        float y = enemy.getCurrentWindowY();

        TextureRegion currentFrame = null;

        switch (enemy.getDirection()) {
            case STANDINGUP:
                currentFrame = enemy.getCharacterStandingUpTexture();
                break;
            case STANDINGDOWN:
                currentFrame = enemy.getCharacterStandingDownTexture();
                break;
            case STANDINGLEFT:
                currentFrame = enemy.getCharacterStandingLeftTexture();
                break;
            case STANDINGRIGHT:
                currentFrame = enemy.getCharacterStandingRightTexture();
                break;
            default:
                return;
        }

        draw(currentFrame, x, y, 64, 128);
    }

    private void renderStandingPlayer() {
        Player player = game.getGameEngine().getPlayer();

        float x = player.getCurrentWindowX();
        float y = player.getCurrentWindowY();

        TextureRegion currentFrame = null;

        switch (player.getDirection()) {
            case STANDINGUP:
                currentFrame = player.getCharacterStandingUpTexture();
                break;
            case STANDINGDOWN:
                currentFrame = player.getCharacterStandingDownTexture();
                break;
            case STANDINGLEFT:
                currentFrame = player.getCharacterStandingLeftTexture();
                break;
            case STANDINGRIGHT:
                currentFrame = player.getCharacterStandingRightTexture();
                break;
            default:
                renderPlayer();
                return;
        }

        draw(currentFrame, x, y, 64, 128);
    }

    private void draw(TextureRegion currentFrame, float x, float y, int width, int height) {
        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(currentFrame, x, y, width, height);
        game.getSpriteBatch().end();
    }

    private void renderEnemy(Enemy enemy) {
        Animation<TextureRegion> anim = null;
        Player player = game.getGameEngine().getPlayer();
        float x = player.getOffsetWindowX(tileSize, enemy.getPersonality());
        float y = player.getOffsetWindowY(tileSize, enemy.getPersonality());

        x = x - enemy.getCurrentWindowX();
        y = y - enemy.getCurrentWindowY();

        //Path Finding Algorithm
        Direction direction;

        if (x > 0) {
            if (y > 0) {
                direction = (x > y) ? RIGHT : UP;
            } else {
                direction = (Math.abs(x) > Math.abs(y)) ? RIGHT : DOWN;
            }
        } else {
            if (y > 0) {
                direction = (Math.abs(x) > y) ? LEFT : UP;
            } else {
                direction = (Math.abs(x) > Math.abs(y)) ? LEFT : DOWN;
            }
        }
        if (enemy.getMovementSmoothing() == 0) {
            enemy.setDirection(direction);
        } else if (isEnemyAtBarrier(enemy)) {
            enemy.rotateDirection();
        }

        enemy.decrmenentMovementSmoothing();
        //enemy.setDirection(direction);
        enemy.move(enemySpeed);
        switch (enemy.getDirection()) {
            case UP -> anim = enemy.getCharacterUpAnimation();
            case DOWN -> anim = enemy.getCharacterDownAnimation();
            case LEFT -> anim = enemy.getCharacterLeftAnimation();
            case RIGHT -> anim = enemy.getCharacterRightAnimation();
            default -> {
                renderStandingEnemy(enemy);
                return;
            }
        }

        draw(anim.getKeyFrame(sinusInput, true), enemy.getCurrentWindowX(), enemy.getCurrentWindowY(), 64, 128);
    }

    private void renderPlayer() {
        Player player = game.getGameEngine().getPlayer();
        player.move(playerSpeed);
        float x = player.getCurrentWindowX();
        float y = player.getCurrentWindowY();

        Animation<TextureRegion> anim = null;

        switch (player.getDirection()) {
            case UP -> anim = player.getCharacterUpAnimation();
            case DOWN -> anim = player.getCharacterDownAnimation();
            case LEFT -> anim = player.getCharacterLeftAnimation();
            case RIGHT -> anim = player.getCharacterRightAnimation();
            default -> {
                renderStandingPlayer();
                return;
            }
        }

        draw(anim.getKeyFrame(sinusInput, true), x, y, 64, 128);

        player.setDirection(getStandingDirection(player.getDirection()));
    }

    private boolean isPlayerAtBarrier() {
        Player player = game.getGameEngine().getPlayer();

        float offsetVerticalTop = 5;
        float offsetVerticalBottom = 33;
        float offsetHorizontal = 30;

        float playerCenterX = player.getCurrentWindowX() + centerPlayerXOffset;
        float playerCenterY = player.getCurrentWindowY() + centerPlayerYOffset;

        switch (player.getDirection()) {
            case STANDINGLEFT:
            case LEFT:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX - offsetHorizontal, playerCenterY, tileSize) instanceof Wall) {
                    System.out.println("Player On Wall");
                    return true;
                }
                break;
            case STANDINGRIGHT:
            case RIGHT:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX + offsetHorizontal, playerCenterY, tileSize) instanceof Wall) {
                    System.out.println("Player On Wall");
                    return true;
                }
                break;
            case STANDINGUP:
            case UP:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX, playerCenterY + offsetVerticalTop, tileSize) instanceof Wall) {
                    System.out.println("Player On Wall");
                    return true;
                }
                break;

            case STANDINGDOWN:
            case DOWN:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX, playerCenterY - offsetVerticalBottom, tileSize) instanceof Wall) {
                    System.out.println("Player On Wall");
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean isEnemyAtBarrier(Enemy enemy) {
        float offsetVerticalTop = 5;
        float offsetVerticalBottom = 33;
        float offsetHorizontal = 30;

        float playerCenterX = enemy.getCurrentWindowX() + centerPlayerXOffset;
        float playerCenterY = enemy.getCurrentWindowY() + centerPlayerYOffset;

        switch (enemy.getDirection()) {
            case STANDINGLEFT:
            case LEFT:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX - offsetHorizontal, playerCenterY, tileSize) instanceof Wall) {
                    return true;
                }
                break;
            case STANDINGRIGHT:
            case RIGHT:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX + offsetHorizontal, playerCenterY, tileSize) instanceof Wall) {
                    return true;
                }
                break;
            case STANDINGUP:
            case UP:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX, playerCenterY + offsetVerticalTop, tileSize) instanceof Wall) {
                    return true;
                }
                break;

            case STANDINGDOWN:
            case DOWN:
                if (game.getGameEngine().getStaticGameMap().getTile(playerCenterX, playerCenterY - offsetVerticalBottom, tileSize) instanceof Wall) {
                    return true;
                }
                break;
        }
        return false;
    }

    private Direction getStandingDirection(Direction currentDirection) {
        return switch (currentDirection) {
            case UP -> STANDINGUP;
            case DOWN -> STANDINGDOWN;
            case LEFT -> STANDINGLEFT;
            case RIGHT -> STANDINGRIGHT;
            default -> currentDirection;
        };
    }

    private boolean isPlayerAtEdgeOfCamera() {
        Player player = game.getGameEngine().getPlayer();
        float playerX = player.getCurrentWindowX();
        float playerY = player.getCurrentWindowY();

        // Check left and right bounds
        if (playerX < camera.position.x - 0.3 * camera.viewportWidth) {
            return true;
        } else if (playerX > camera.position.x + 0.3 * camera.viewportWidth) {
            return true;
        }

        // Check top and bottom bounds
        if (playerY < camera.position.y - 0.25 * camera.viewportHeight) {
            return true;
        } else if (playerY > camera.position.y + 0.25 * camera.viewportHeight) {
            return true;
        }

        return false;
    }

    private void updateCameraPosition() {
        Player player = game.getGameEngine().getPlayer();
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        // Check and update camera position based on player's direction
        switch (player.getDirection()) {
            case UP:
            case STANDINGUP:
                camera.position.y += screenHeight * 0.5;
                System.out.println("Set camera-y to: " + camera.position.y);
                break;
            case DOWN:
            case STANDINGDOWN:
                camera.position.y -= screenHeight * 0.5;
                System.out.println("Set camera-y to: " + camera.position.y);
                break;
            case LEFT:
            case STANDINGLEFT:
                camera.position.x -= screenWidth * 0.5;
                System.out.println("Set camera-x to: " + camera.position.x);
                break;
            case RIGHT:
            case STANDINGRIGHT:
                camera.position.x += screenWidth * 0.5;
                System.out.println("Set camera-x to: " + camera.position.x);
                break;
        }

        float projectionPlaneHeight = game.getGameEngine().getStaticGameMap().getMapHeight() * tileSize;
        float projectionPlaneWidth = game.getGameEngine().getStaticGameMap().getMapWidth() * tileSize;

        if (camera.position.y > projectionPlaneHeight) {
            camera.position.y = projectionPlaneHeight;
            System.out.println("Changed Y-position due to camera out of map-bounds" + camera.position.y);
        }
        if (camera.position.y < 0) {
            camera.position.y = 0;
            System.out.println("Changed Y-position due to camera out of map-bounds" + camera.position.y);
        }
        if (camera.position.x < 0) {
            camera.position.x = 0;
            System.out.println("Changed X-position due to camera out of map-bounds" + camera.position.x);
        }
        if (camera.position.x > projectionPlaneWidth) {
            camera.position.x = projectionPlaneWidth;
            System.out.println("Changed X-position due to camera out of map-bounds" + camera.position.x);
        }
        camera.update();
    }


    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
