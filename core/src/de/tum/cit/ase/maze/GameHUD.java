package de.tum.cit.ase.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;



public class GameHUD extends Stage {

    private final Label livesLabel;
    public GameHUD(Viewport viewport) {
        super(viewport);
        // Initialize your HUB components here
        // Example: Add labels, images, or other actors
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        livesLabel = new Label("Lives: 5", labelStyle);
        livesLabel.setPosition(10, viewport.getWorldHeight() - 20); // Adjust position as needed
        addActor(livesLabel);
    }

    // Add methods to update the HUB components based on game data
    public void updateScore(int newScore) {
        // Update score label or other score-related components
    }
}