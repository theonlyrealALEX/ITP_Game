# Maze Runner Game

## Table of Contents


- [Start](#start)
- [Additional Game Mechanics](#additionalgamemechanics)
- [Classes](#classes)
- [Classes/Structure](#classes/structure)


## Start!
### How to run the game?

1. Pull it from the GitHUB.
2. Edit the configurations, make sure to use java 17 SDK with desktop.test.
3. Run the code.

### Start the Game
1. After running the code, the "select screen" with selections of the available maps appear, select the level you want to play.
2. It brings you to the "menu screen", where you can choose to start the game with the selected map or quit game which close the entire game window.

### Play the game
1. The main character can move up, down, left, right, you can control it with the direction keys on the key board.You can't walk through walls.
2. The game starts with 3 lives. To get out of the maze you need to collect one or more keys (the information is provided at HUD which is on the top center of the game screen).
3. You win the game if you are able to collect the sufficient amount of keys and get out of the maze from the exit; you loose the game if you lost all the lives during the game.
4. There are 2 kinds of obstacles that reduces your life, one is the moving animies which detects your location and goes after you, another one is the blue traps which you need to avoid.
5. To increase your life you can walk to the red spinning heart, and your life will increase by 1. The status of your lives and collection of keys is showed at the game HUD.
6. You can pause the game by pressing "ESC" key on your keyboard

### Pause game
1. Click "continue game" to continue the existing game.
2. Click "start again" to start a new game with the selected map.
3. Click "select new map" to end the game and go back to the select screen.
4. Click "quit game" to close the game window.

### Victory
1. If you managed to collect the sufficient amount of keys and get out of the maze, you win the game.
2. Click "choose new map" to select another map.
3. Click "play again" to play the same game again.

### Loose
1. If you loose all the lives inside the maze, you loose the game.
2. Click "play again" to start the same game again.
3. Click "back to menu" to select a new map.


## Additional Game Mechanics
1. Enemies move intelligently towards the main character: All the moving enemies in this game will be moving towards the main character, so it is harder for the player to avoid them.
2. Collectable lives: The lives are collectable by walking throught the spinning red hearts in the maze.



## Classes

Please check out the UML diagram: ([https://img.shields.io/github/license/:user/:repo.svg](https://drive.google.com/file/d/1M7xw0M8C7JkSlSbCO7nkR9EaGFN3kLQ4/view?usp=sharing))

The main Logic of the Game is in the GameScreen class. It handles most of the events and triggers the correct responses. It can be compared to a Controller in the MVC-Model. It also handles the Camera Logic for changing the view when the Player approaches the end of the visible screen.
The GameEngine with its subclasses (GameMap, Player, Enemy, MapObject) is the Model of the game. It is responsible for Modeling the behavior of the Characters and Map. There are static map objects that are not movable (MapObject (Abstract): Trap, Path, Wall, EntryPoint, ExitPoint, …) and movable Objects such as the Player and Enemy. The static objects and Enemies are loaded from the Map-File, or randomly distributed on the Paths. The Player’s coordinates are set to the Location of the EntryPoint.
The Screen classes are responsible for the visualization of the different screens and the HUD (‘View’ in MVC).

## Classes

### Screens
#### GameHUD:

The GameHUD class extends the LibGDX Stage and implements the Screen interface. It utilizes a SpriteBatch for rendering and manages various UI elements, including animations, labels, and camera settings. The HUD displays the player's remaining lives and key status.

Constructor
- GameHUD(MazeRunnerGame game)
Initializes the HUD with default values.
Sets up the layout using a LibGDX Table.
Creates heart and key animations.
Associates the HUD with the main game.
- GameHUD(MazeRunnerGame game, boolean key, int lives)
Overloaded constructor allowing customization of key and lives.
Similar to the default constructor with parameterized key and lives.


Public Methods

- isKey(): boolean
Returns the current key status.
- setKey(boolean key)
Sets the key status.
- getLives(): Integer
Returns the current number of lives.
- decrementsLives()
Decreases the lives count if greater than 0.
- incrementLifes()
Increases the lives count.


Animations

The class includes methods for creating heart and key animations. These animations continuously cycle through frames, providing a dynamic visual effect.



User Interface Elements

The HUD consists of heart and key images, as well as corresponding labels for key status and lives. These elements are organized in a LibGDX Table for proper layout.


Input Processing

- show()
Sets the input processor to the HUD.


Lifecycle Methods

- render(float delta)
Updates and draws the HUD during the game loop.
- resize(int width, int height)
Placeholder for handling screen resizing.
- dispose()
Disposes of resources, including the sprite batch.


#### SelectScreen:
The SelectScreen class provides functionality for players to select a map before entering the game. It displays a list of available maps, and players can choose a specific map to play.


Constructor

- SelectScreen(MazeRunnerGame game)
Initializes the map selection screen.
Sets up the camera, viewport, and stage for UI elements.
Creates UI components, including labels, buttons, and a map selection table.
Populates the table with buttons corresponding to available maps.


UI Components

The class includes UI components such as labels, buttons, and a table, organized in a LibGDX Table for proper layout. Buttons represent individual maps, allowing players to choose the desired map.



Sound Effects

Background Music: Plays background music while the player is on the map selection screen.
Button Click Sound: Plays a sound effect when map buttons are clicked, providing auditory feedback to the player.


#### MenuScreen:
The MenuScreen class in the Maze Runner Game project is responsible for displaying the main menu of the game. It extends the LibGDX Screen class and sets up the UI components for the menu, including animations and buttons. Below is an overview of the class.


Constructor

- MenuScreen(MazeRunnerGame game)
Initializes the main menu screen.
Sets up the camera, viewport, and stage for UI elements.
Creates UI components, including a title label, animated fire, and buttons to go to the game or quit.


Animations

- createFireAnimation()
Creates an animated fire image using a sequence of frames.
Utilizes LibGDX Actions to define the animation sequence.


Input Processing

- show()
Sets the input processor to the stage, enabling the reception of input events.


Lifecycle Methods

- render(float delta)
Renders the main menu screen.
Clears the screen and updates/draws the stage.
- resize(int width, int height)
Updates the stage viewport on screen resize.
- dispose()
Disposes of resources, including the stage, when the screen is no longer in use.


#### PauseMenuScreen:
The PauseMenuScreen class serves as an essential component for managing the pause menu within the game. It allows players to make choices like continuing the game, starting a new game, selecting a new map, or quitting the game.


Constructor

- PauseMenuScreen(MazeRunnerGame game)
Initializes the pause menu screen.
Sets up the camera, viewport, and stage for UI elements.
Creates UI components, including labels and buttons, for various menu options.


UI Components

The class includes UI components such as labels and buttons, organized in a LibGDX Table for proper layout. Buttons trigger actions to continue the game, start a new game, select a new map, or quit the game.


Animations

The class features a fade-out animation for the "Start Again" and "Select New Map" buttons. These buttons smoothly fade out before executing specific actions.


Input Processing

- show()
Sets the input processor to the stage, enabling the reception of input events.
Lifecycle Methods
- render(float delta)
Renders the pause menu screen.
Clears the screen and updates/draws the stage.
resize(int width, int height)
Updates the stage viewport on screen resize.
- dispose()
Disposes of resources, including the stage, when the screen is no longer in use.


Sound Effects


The class includes a method, clickSound(), which plays a sound effect when buttons are clicked. This enhances the user experience with auditory feedback.

#### GameOverScreen:
The GameOverScreen class provides functionality for displaying a game over message, animations, and options for the player to start a new game or return to the main menu.


Constructor

- GameOverScreen(MazeRunnerGame game)
Initializes the game over screen.
Sets up the camera, viewport, and stage for UI elements.
Creates UI components, including labels, buttons, and an animation for the game over screen.


UI Components

The class includes UI components such as labels, buttons, and an animation of an explosive skull. These components are organized in a LibGDX Table for proper layout.


Animations

The class features an animation of an explosive skull created from frames of a texture sheet. The skull animation adds a visually appealing element to the game over screen.


Sound Effects

Game Over Music: Plays game over music when the player loses the game.
Button Click Sound: Plays a sound effect when buttons are clicked, providing auditory feedback to the player.


Button Actions

Play Again Button: Initiates a fade-out animation for the "Play Again" button and starts a new game.
Back to Menu Button: Initiates a fade-out animation for the "Back to Menu" button, disposes of resources, creates a new game, and returns to the map selection screen.

#### VictoryScreen:
The VictoryScreen class provides functionality for displaying a victory message, animations, and options for the player to choose a new map or play the game again.


Constructor

- VictoryScreen(MazeRunnerGame game)
Initializes the victory screen.
Sets up the camera, viewport, and stage for UI elements.
Creates UI components, including labels, buttons, and an animation for the victory screen.
Plays victory music when the player successfully exits the maze.


UI Components

The class includes UI components such as labels, buttons, and an animation of a character celebrating the victory. These components are organized in a LibGDX Table for proper layout.


Animations

The class features an animation of a character celebrating the victory created from frames of a texture sheet. The victory animation adds a visually appealing element to the victory screen.


Button Actions

Choose New Map Button: Initiates a fade-out animation for the "Choose New Map" button and navigates to the map selection screen.
Play Again Button: Initiates a fade-out animation for the "Play Again" button and starts a new game.


Sound Effects

Victory Music: Plays victory music when the player successfully exits the maze.
Button Click Sound: Plays a sound effect when buttons are clicked, providing auditory feedback to the player.


### Model and Controller Classess
MazeRunnerGame
The provided Skeleton Class for the Game
Overview
MazeRunnerGame is the core class of the Maze Runner game. It orchestrates game screens, manages global resources like SpriteBatch and Skin, and controls game flow.
Key Features
* Manages various screens (Menu, Game, PauseMenu, SelectScreen).
* Handles the game engine and rendering resources.
* Controls game music and animations.
Main Functionalities
* Screen Management: Switches between different game screens.
* Resource Handling: Manages SpriteBatch, Skin, and Music.
* Game Flow Control: Directs game flow, from menu to gameplay, pause, and end screens.
Methods
* create(): Initializes the game resources and navigates to the select screen.
* goToSelect(), goToMenu(), goToGame(), goToPauseMenu(), goToVictoryScreen(), goToGameOverScreen(), continueGame(): Manage screen transitions.
* dispose(): Cleans up resources upon game closure.
* Getters and setters for music, screens, and game engine.
Usage
Create an instance of MazeRunnerGame to start the game. Utilize its methods to navigate between screens and manage game states.


#### GameEngine
Implements the Model of the Game. Has a GameMap for storing the Map, and also the Player and also Enemies.
Overview
The GameEngine class, part of the de.tum.cit.ase.maze package, serves as the central controller for the Maze Runner game. It initializes the game environment, including the map and the player.
Key Features
* Initializes game components upon creation.
* Manages the player and the game map.
Main Functionalities
* Initialization: Configures the game with a selected map and a new player instance.
* Player Management: Offers methods to get and set the player's details.
* Map Handling: Handles the game map, allowing retrieval of the current map.
Methods
* GameEngine(MazeRunnerGame): Constructor that sets up the game engine.
* getPlayer(): Returns the current player.
* setPlayer(Player): Updates the player instance.
* getStaticGameMap(): Retrieves the static game map.
Usage
Create an instance of GameEngine with a MazeRunnerGame object to initialize the game. Use provided methods to interact with the player and the map.
 
#### GameMap
Models the static and dynamic game map.
Overview
GameMap is a central class in the Maze Runner game, responsible for loading and managing the game map's layout, including elements like enemies, lives, and keys.
Features
* Dynamic map loading from files.
* Manages enemies, lives, keys, and entry points.
* Interactive map functions for gameplay mechanics.
Key Methods
* loadMap(String): Loads and sets up the game map.
* getNewRandomLife(int, int): Generates random life positions.
* getTile(float, float, float): Retrieves map tile content.
* removeKey(float, float, float): Removes a key from the map.
Usage
Instantiate and use loadMap to load a map, then interact using class methods for game dynamics.

#### Player
Models the Player
Overview
The Player class in the Maze Runner game manages the player's animations, movement, and position within the game world.
Key Features
* Character animations for different directions.
* Methods for player movement and position tracking.
* Management of the player's current tile and direction.
Main Functionalities
* Animation Handling: Loads and manages animations for player movement.
* Movement: Implements player movement based on speed and direction.
* Position Tracking: Manages the player's position in the game window and on the game map.
Methods
* Player(): Constructor that initializes player animations and sets initial direction.
* move(float speed): Moves the player based on the current direction and speed.
* setCurrentTileFromCoords(GameMap, float): Updates the player's current tile based on their coordinates.
* getOffsetWindowX(float, Personality), getOffsetWindowY(float, Personality): Calculate the player's offset position.
Usage
Instantiate a Player object to represent the player in the game. Use its methods to control the player's animations, movements, and interactions with the game environment.

 
#### Enemy
Models the Enemy.
Overview
The Enemy class in the Maze Runner game extends MapObject to represent enemies on the game map. It handles animations, movements, and interactions of enemies within the game.
Key Features
* Character animations for different directions.
* Random personality and rotation for dynamic behavior.
* Movement mechanics and position tracking.
Main Functionalities
* Animation Handling: Loads and manages animations for enemy movement.
* Movement: Implements enemy movement based on direction, speed, and personality.
* Position and Rotation: Manages the enemy's position and rotation behavior when encountering obstacles.
Methods
* Enemy(int tileX, int tileY): Constructor initializing enemy with tile coordinates.
* move(float speed): Moves the enemy based on the current direction and speed.
* setCurrentTileFromCoords(GameMap, float): Updates the enemy's current tile based on their coordinates.
* rotateDirection(): Changes the enemy's direction when encountering an obstacle.
* getRandomPersonality(), getRandomRotation(): Randomizes enemy's personality and rotation.
Usage
Create Enemy objects to populate the game map with adversaries. Utilize the class methods to control their animations, movements, and interactions with the game environment.

#### MapObject
Superclass for all static map objects.
ChatGPT
README: MapObject Class Summary
Overview
MapObject is an abstract class in the Maze Runner game, serving as a base for various elements within the game map. It provides a fundamental structure for rendering objects in the game world.
Key Feature
* Establishes a common interface for all map objects to be rendered.
Main Functionality
* Rendering Interface: Defines an abstract method for rendering map objects.
Method
* render(SpriteBatch spriteBatch, float x, float y, float tileSize): Abstract method that must be implemented by subclasses to render the object on the game map.
Usage
Extend MapObject to create different entities in the game (like walls, paths, enemies) that can be rendered on the map. Each subclass should provide its specific implementation of the render method.

