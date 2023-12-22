package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import static de.tum.cit.ase.maze.Direction.*;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic and rendering of the game elements.
 */
public class GameScreen implements Screen {

    private final MazeRunnerGame game;
    private final OrthographicCamera camera;
    private final BitmapFont font;

    private float sinusInput = 0f;

    private final float tileSize = 80;

    private float playerSpeed = 3;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(MazeRunnerGame game) {
        this.game = game;

        // Create and configure the camera for the game view
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 0.75f;

        // Get the font from the game's skin
        font = game.getSkin().getFont("font");

        //Create Player
        game.getGameEngine().getPlayer().setCurrentWindowX(camera.viewportWidth / 2);
        game.getGameEngine().getPlayer().setCurrentWindowY(camera.viewportHeight / 2);

    }


    // Screen interface methods with necessary functionality
    @Override
    public void render(float delta) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }

        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen

        renderMap();
        Player player = game.getGameEngine().getPlayer();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setDirection(UP);
            renderPlayer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setDirection(DOWN);
            renderPlayer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setDirection(LEFT);
            renderPlayer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setDirection(RIGHT);
            renderPlayer();
        } else {
            // Do not change the direction here, just render the standing player
            renderStandingPlayer();
        }


        if(isPlayerAtEdge()){
            System.out.println("Player at Edge; Updating Camera");
            updateCameraPosition();
        }

        //camera.update(); // Update the camera

        // Move text in a circular path to have an example of a moving object
        sinusInput += delta;
        float textX = (float) (camera.position.x + Math.sin(sinusInput) * 100);
        float textY = (float) (camera.position.y + Math.cos(sinusInput) * 100);

        // Set up and begin drawing with the sprite batch
        game.getSpriteBatch().setProjectionMatrix(camera.combined);


        game.getSpriteBatch().begin(); // Important to call this before drawing anything

        game.getSpriteBatch().end(); // Important to call this after drawing everything
    }

    // Render Map
    public void renderMap() {
        MapObject[][] mapObjects = game.getGameEngine().getStaticGameMap().getStaticMapObjects();

        // Calculate the size of each tile on the screen, for example:
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

        game.getSpriteBatch().begin();
        // Draw the standing frame scaled to tileSize
        game.getSpriteBatch().draw(
                currentFrame,
                x, y,
                64, 128 // Adjust the size as needed
        );
        game.getSpriteBatch().end();
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
        }

        TextureRegion currentFrame = anim.getKeyFrame(sinusInput, true);

        game.getSpriteBatch().begin();
        game.getSpriteBatch().draw(currentFrame, x, y, 64, 128);
        game.getSpriteBatch().end();

        player.setDirection(getStandingDirection(player.getDirection()));
    }

    private Direction getStandingDirection(Direction currentDirection) {
        return switch (currentDirection) {
            case UP -> STANDINGUP;
            case DOWN -> STANDINGDOWN;
            case LEFT -> STANDINGLEFT;
            case RIGHT -> STANDINGRIGHT;
            // For standing directions, keep the same
            default -> currentDirection;
        };
    }

    private boolean isPlayerAtEdge() {
        Player player = game.getGameEngine().getPlayer();
        float playerX = player.getCurrentWindowX();
        float playerY = player.getCurrentWindowY();

        float edge = 0.2f;
        float playerWidth = 16;  // Replace with actual player width
        float playerHeight = 32; // Replace with actual player height

        // Calculate the bounds of the camera's viewport
        float leftBound = camera.position.x - camera.viewportWidth / 2;
        float rightBound = camera.position.x + camera.viewportWidth / 2;
        float bottomBound = camera.position.y - camera.viewportHeight / 2;
        float topBound = camera.position.y + camera.viewportHeight / 2;

        System.out.println("LeftBound:"+leftBound);

        System.out.println("Camera Coords:"+camera.position.x+ " "+ camera.position.y);
        System.out.println("Camera Viewport"+camera.viewportWidth);
        System.out.println("PlayerCoords"+playerX + " "+playerY);


        // Check left and right bounds
        if (playerX < 0 + camera.position.x * edge/2 && playerX > 0) {
            return true;
        } else if (playerX > camera.position.x * 2 - camera.position.x * edge/2) {
            return true;
        }

        System.out.println("top edge: " + camera.position.y * edge);
        float val = camera.position.y * 2 -camera.position.y * edge;
        System.out.println("bottom edge: " +val );
        // Check top and bottom bounds
        if (playerY < 0 + camera.position.y * edge) {
            return true;
        } else if (playerY  > camera.position.y * 2 -camera.position.y * edge) {
            System.out.println("bottom edge: " +val );
            return true;
        }

        return false;
    }

    // Reason for the "snapping"-bug is because I work with percentages.
    private void updateCameraPosition() {
        Player player = game.getGameEngine().getPlayer();
        float screenWidth = camera.viewportWidth;
        float screenHeight = camera.viewportHeight;

        // Define how close to the edge the player must be to move the camera

        System.out.println("Screen Height: "+screenHeight);
        System.out.println("Snap");
        // Check and update camera position based on player's direction
        switch (player.getDirection()) {
            case UP:
            case STANDINGUP:
                camera.position.y += screenHeight*0.3; // Move camera up by one screen height
                //player.setCurrentWindowY(camera.position.y); // Adjust player's Y position
                break;
            case DOWN:
            case STANDINGDOWN:
                    camera.position.y -= screenHeight*0.3; // Move camera down by one screen height
                    //player.setCurrentWindowY(camera.position.y); // Adjust player's Y position
                break;
            case LEFT:
            case STANDINGLEFT:
                    camera.position.x -= screenWidth*0.3; // Move camera left by one screen width
                    //player.setCurrentWindowX(camera.position.x); // Adjust player's X position
                break;
            case RIGHT:
            case STANDINGRIGHT:
                    camera.position.x += screenWidth*0.3; // Move camera right by one screen width
                    //player.setCurrentWindowX(camera.position.x); // Adjust player's X position
                break;
            default:
                // Do nothing for standing directions
                break;
        }
        camera.update(); // Update the camera after repositioning
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

    // Additional methods and logic can be added as needed for the game screen

       /*
        // Render the text
        font.draw(game.getSpriteBatch(), "Press ESC to go to menu", textX, textY);

        // Draw the character next to the text :) / We can reuse sinusInput here
        game.getSpriteBatch().draw(
                game.getCharacterDownAnimation().getKeyFrame(sinusInput, true),
                textX - 96,
                textY - 64,
                64,
                128
        );
*/
}
